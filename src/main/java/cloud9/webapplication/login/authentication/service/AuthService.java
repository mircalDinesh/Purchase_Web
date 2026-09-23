package cloud9.webapplication.login.authentication.service;

import cloud9.webapplication.login.authentication.dto.LoginDto;
import cloud9.webapplication.login.authentication.module.LoginModule;
import cloud9.webapplication.login.authentication.repository.LoginRepo;
import cloud9.webapplication.login.authorization.service.OtpService;
import cloud9.webapplication.security.JavaWebToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class AuthService {

    private final LoginRepo loginRepo;
    private final OtpService otpService;
    private final JavaWebToken javaWebToken;

    public AuthService(LoginRepo loginRepo, OtpService otpService, JavaWebToken javaWebToken) {
        this.loginRepo = loginRepo;
        this.otpService = otpService;
        this.javaWebToken = javaWebToken;
    }
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        LoginModule user = Optional.ofNullable(loginRepo.findByEmail(loginDto.getEmail()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if(user.getPassword().equals(loginDto.getPassword())) {
            HttpHeaders headers = new HttpHeaders();
            String token = javaWebToken.generateToken(loginDto.getEmail());
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            otpService.generateOtp(loginDto.getEmail());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(headers)
                    .body("Login Successful");
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Wrong Credentials or Invalid UserName");

    }


}
