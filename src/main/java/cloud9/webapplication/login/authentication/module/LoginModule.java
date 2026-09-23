package cloud9.webapplication.login.authentication.module;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "login_details")
@Entity
public class LoginModule {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String ledgerName;
        private String role;
        private String email;
        private String password;
        private String status;
}

