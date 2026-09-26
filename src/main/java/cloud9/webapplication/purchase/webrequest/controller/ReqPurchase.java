package cloud9.webapplication.purchase.webrequest.controller;
import cloud9.webapplication.purchase.webrequest.dto.RequestVoucher;
import cloud9.webapplication.purchase.webrequest.service.RequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

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
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(serviceRequest.PurchaseRequest(requestVoucher, attachment));
    }


    @GetMapping(value = "purchase/{id}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MultiValueMap<String, Object>> webRequestPurchase(@PathVariable Long id) throws IOException {
        return ResponseEntity.ok(serviceRequest.fetchExisting(id));
    }
}
