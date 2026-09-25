package cloud9.webapplication.purchase.webrequest.controller;
import cloud9.webapplication.purchase.webrequest.dto.RequestVoucher;
import cloud9.webapplication.purchase.webrequest.service.RequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth/webrequest")
public class ReqPurchase {

    private final RequestService serviceRequest;

    public ReqPurchase(RequestService serviceRequest) {
        this.serviceRequest = serviceRequest;
    }


    @PostMapping("/purchase")
    public ResponseEntity<String> WebRequestPurchase(@RequestBody RequestVoucher requestVoucher) {
       return serviceRequest.PurchaseRequest(requestVoucher);
    }
}
