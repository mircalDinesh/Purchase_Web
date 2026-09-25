package cloud9.webapplication.purchase.webrequest.service;
import cloud9.webapplication.purchase.webrequest.dto.RequestVoucher;
import cloud9.webapplication.purchase.webrequest.mapper.MappingVoucher;
import cloud9.webapplication.purchase.webrequest.module.VoucherDetails;
import cloud9.webapplication.purchase.webrequest.repo.WebRequestRep;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final WebRequestRep webRequestRep;
    private final MappingVoucher mappedVoucher;


    @Transactional
    public ResponseEntity<String> PurchaseRequest(@RequestBody RequestVoucher requestVoucher){
        Optional<VoucherDetails> Existing = webRequestRep.findByvchNo(requestVoucher.vchNo());
        Long Id = null;
        VoucherDetails previous=null;
        if(Existing.isPresent()){
            previous=Existing.get();
            Id=previous.getId();
            previous.getInventoryEntries().clear();
            previous.getLedgerEntries().clear();
            webRequestRep.saveAndFlush(previous);
        }
        VoucherDetails voucherDetails = mappedVoucher.toEntity(requestVoucher);
        voucherDetails.setId(Id);
        voucherDetails.setTallyStatus("Pending");
        webRequestRep.saveAndFlush(voucherDetails);
     //   System.out.println("RequestVoucher:"+requestVoucher);
        return ResponseEntity.ok().body("Success");
    }
}
