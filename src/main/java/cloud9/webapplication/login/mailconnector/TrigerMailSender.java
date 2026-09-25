package cloud9.webapplication.login.mailconnector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TrigerMailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String recipient, String OTP) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreplycloud9softtechnologies@gmail.com"); // REQUIRED
            message.setTo(recipient);
            message.setSubject("Your OTP Code");
            message.setText("Your OTP is: " + OTP + "\n Valid for 2 minute.");
            javaMailSender.send(message);
            ResponseEntity.status(HttpStatus.OK).body(
                    Map.of("OTP", OTP).toString()
            );
        } catch (Exception e) {
          //  System.out.println(e.getMessage());
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send OTP: " + e.getMessage());
        }
    }
}
