package cloud9.webapplication.purchase.webrequest.repo;

import cloud9.webapplication.purchase.webrequest.module.VoucherDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebRequestRep extends JpaRepository<VoucherDetails, Long> {

}
