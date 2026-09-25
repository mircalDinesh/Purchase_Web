package cloud9.webapplication.login.authorization.controller;

import cloud9.webapplication.login.authorization.service.OtpService;
import cloud9.webapplication.login.otpresponse.OTPResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth/authorization")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/otpverify")
    public ResponseEntity<OTPResponse> verify(@RequestParam String email, @RequestParam String code) {
      //  System.out.println(code);
        return  otpService.verifyOtp(email, code);
    }

}
