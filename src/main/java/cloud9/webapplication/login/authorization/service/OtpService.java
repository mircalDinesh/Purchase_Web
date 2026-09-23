package cloud9.webapplication.login.authorization.service;
import cloud9.webapplication.login.authentication.module.LoginModule;
import cloud9.webapplication.login.authentication.repository.LoginRepo;
import cloud9.webapplication.login.mailconnector.TrigerMailSender;
import cloud9.webapplication.login.otpresponse.OTPResponse;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Service
public class OtpService {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private final TrigerMailSender trigerMailSender;
    private final LoginRepo loginRepo;

    public OtpService(TrigerMailSender trigerMailSender, LoginRepo loginRepo) {
        this.trigerMailSender = trigerMailSender;
        this.loginRepo = loginRepo;

    }

    private final Cache<String, String> otpCache = CacheBuilder.newBuilder()
            .expireAfterWrite(2, TimeUnit.MINUTES)
            .build();

    public void generateOtp(String email) {
        String otp = String.format("%06d", SECURE_RANDOM.nextInt(1_000_000));
        otpCache.put(email, otp);
        trigerMailSender.sendEmail(email, otp);
    }

    public ResponseEntity<OTPResponse> verifyOtp(String email, String code) {
        String storedOtp = otpCache.getIfPresent(email);
        if (storedOtp == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(null);
        }
        boolean isValid = storedOtp.equals(code);
        if (!isValid) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }
        // User Details For Reference For Login
        LoginModule loginModule = Optional
                .ofNullable(loginRepo.findByEmail(email))
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        // Why Used Record , Only Share Respective Details of User
        OTPResponse responseOtp = new OTPResponse(
                loginModule.getEmail(),
                loginModule.getLedgerName(),
                loginModule.getRole()
        );

        return ResponseEntity.ok(responseOtp);
    }
}
