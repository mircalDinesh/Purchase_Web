package cloud9.webapplication.purchase.voucherseries.repository;

import cloud9.webapplication.purchase.voucherseries.module.VoucherSeries;
 import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VoucherSeriesRepo extends JpaRepository<VoucherSeries, Long> {

    Optional<VoucherSeries> findByPrefixAndFinancialYear(String prefix, String financialYear);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM VoucherSeries s WHERE s.prefix = :prefix AND s.financialYear = :financialYear")
    Optional<VoucherSeries> findByPrefixAndFinancialYearWithLock(String prefix, String financialYear);

    // NEW — atomic get-or-create, closes the race window entirely
    @Modifying
    @Query(value = """
        INSERT INTO voucher_series (prefix, financial_year, last_number)
        VALUES (:prefix, :financialYear, 0)
        ON CONFLICT (prefix, financial_year) DO NOTHING
        """, nativeQuery = true)
    void ensureSeriesExists(@Param("prefix") String prefix, @Param("financialYear") String financialYear);
}
