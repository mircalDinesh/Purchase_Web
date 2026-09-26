package cloud9.webapplication.purchase.webrequest.controller;
import cloud9.webapplication.purchase.webrequest.dto.RequestVoucher;
import cloud9.webapplication.purchase.webrequest.service.RequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("api/auth/webrequest")
@RequiredArgsConstructor
public class ReqPurchase {

    private final RequestService serviceRequest;
    private final ObjectMapper objectMapper;




    @PostMapping(value = "/purchase", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> webRequestPurchase(
            @RequestParam("voucherData") String voucherDataJson,
            @RequestParam(value = "attachment", required = false) MultipartFile attachment) throws JsonProcessingException {

        RequestVoucher requestVoucher = objectMapper.readValue(voucherDataJson, RequestVoucher.class);

       // String result = serviceRequest.PurchaseRequest(requestVoucher, attachment);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(serviceRequest.PurchaseRequest(requestVoucher, attachment));
    }
}
