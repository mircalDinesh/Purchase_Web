package cloud9.webapplication.purchase.webrequest.service;

import cloud9.webapplication.purchase.webrequest.dto.RequestVoucher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class RequestService {

    public ResponseEntity<String> RequestService(@RequestBody RequestVoucher requestVoucher){
        System.out.println("RequestVoucher:"+requestVoucher);
        return ResponseEntity.ok().body("Success");
    }
}
