package cloud9.webapplication.login.authentication.repository;

import cloud9.webapplication.login.authentication.module.LoginModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoginRepo extends JpaRepository<LoginModule,Integer> {
    LoginModule findByEmail (String email);
}
