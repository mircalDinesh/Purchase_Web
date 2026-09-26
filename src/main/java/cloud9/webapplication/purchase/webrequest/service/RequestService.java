package cloud9.webapplication.purchase.webrequest.service;
import cloud9.webapplication.purchase.voucherseries.service.VoucherSeriesService;
import cloud9.webapplication.purchase.webrequest.dto.RequestVoucher;
import cloud9.webapplication.purchase.webrequest.mapper.MappingVoucher;
import cloud9.webapplication.purchase.webrequest.module.VoucherDetails;
import cloud9.webapplication.purchase.webrequest.repo.WebRequestRep;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final WebRequestRep webRequestRep;
    private final MappingVoucher mappedVoucher;
    private final VoucherSeriesService voucherSeriesService;
    private static final String UPLOAD_DIR = "uploads/purchase-attachments/";

    @Transactional
    public String PurchaseRequest(RequestVoucher requestVoucher, MultipartFile attachment){
        String voucherNumber = voucherSeriesService.SaveLastSequence("Web-Pur");
        Optional<VoucherDetails> Existing = webRequestRep.findByvchNo(requestVoucher.vchNo());
        Long Id = null;
        VoucherDetails previous=null;
        if(Existing.isPresent()){
            previous=Existing.get();
            Id=previous.getId();
            previous.getInventoryEntries().clear();
            previous.getLedgerEntries().clear();
            voucherNumber=previous.getVchNo();
            webRequestRep.saveAndFlush(previous);
        }
        VoucherDetails voucherDetails = mappedVoucher.toEntity(requestVoucher);
        voucherDetails.setVchNo(voucherNumber);
        voucherDetails.setId(Id);
        voucherDetails.setTallyStatus("Pending");
        // Handle optional PDF attachment
        if (attachment != null && !attachment.isEmpty()) {
            storeAttachment(voucherDetails, attachment, voucherDetails.getVchNo());
        }
        webRequestRep.saveAndFlush(voucherDetails);
        return "Purchase voucher " + voucherNumber + " saved successfully with ID "  ;
    }


    private void storeAttachment(VoucherDetails voucher, MultipartFile file, String vchNo) {
        try {
            String StoringPath=UPLOAD_DIR+"/"+voucher.getSupplierName();
            Path uploadPath = Paths.get(StoringPath).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);
            String safeVchNo =vchNo.replaceAll("[\\\\/:*?\"<>|]", "_").trim();
            String fileName = safeVchNo  + ".pdf";
            Path filePath = uploadPath.resolve(fileName);
            System.out.println("Target file path: " + filePath);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            voucher.setAttachedFile(filePath.toString());
            System.out.println("File saved successfully at: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to store PDF attachment: " + e.getMessage(), e);
        }
    }
}
