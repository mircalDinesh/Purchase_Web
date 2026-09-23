package cloud9.webapplication.login.authentication.controller;


import cloud9.webapplication.login.authentication.dto.LoginDto;
import cloud9.webapplication.login.authentication.repository.LoginRepo;
import cloud9.webapplication.login.authentication.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth/authentication")
public class LoginController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> ControllerLogin(@RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }
}
