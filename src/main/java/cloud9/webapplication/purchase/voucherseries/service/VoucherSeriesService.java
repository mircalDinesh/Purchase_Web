package cloud9.webapplication.purchase.voucherseries.service;

import cloud9.webapplication.exception.InValidSequence;
import cloud9.webapplication.purchase.voucherseries.module.VoucherSeries;
import cloud9.webapplication.purchase.voucherseries.repository.VoucherSeriesRepo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j //log information use of this Annotations
@Service
@RequiredArgsConstructor
public class VoucherSeriesService {

    private final VoucherSeriesRepo sequenceRepository;

    // Preview VoucherNumber
    @Transactional(readOnly = true)
    public String getLastNumber(@NonNull String prefix) {
        String financialYear = getFinancialYear();
        VoucherSeries sequence = sequenceRepository
          .findByPrefixAndFinancialYear(prefix, financialYear)
                .orElse(null);
        long nextNumber = (sequence != null) ? sequence.getLastNumber() + 1 : 1L;
        log.info("getPreviewNumber: nextNumber={}", nextNumber);
        return String.format("%s/%04d/%s", prefix, nextNumber, financialYear);
    }

    @Transactional
    public String SaveLastSequence(@NonNull String prefix) {
        String financialYear = getFinancialYear();


        VoucherSeries seq = sequenceRepository
                .findByPrefixAndFinancialYearWithLock(prefix, financialYear)
                .orElseGet(() ->
                        {
                            VoucherSeries sequence = new VoucherSeries();
                            sequence.setPrefix(prefix);
                            sequence.setFinancialYear(financialYear);
                            sequence.setLastNumber(0L);
                            return sequenceRepository.save(sequence);
                        }
                );

        long nextNumber = seq.getLastNumber() + 1;
        System.out.println(nextNumber);
        seq.setLastNumber(nextNumber);

        sequenceRepository.saveAndFlush(seq);
        log.info("saveVoucherNumber: prefix={}, fy={}, nextNumber={}", prefix, financialYear, nextNumber);
        return String.format("%s/%04d/%s", prefix, nextNumber, financialYear);
    }

    private String getFinancialYear() {
        LocalDate currentDay = LocalDate.now();
        int year = currentDay.getYear();
        if (currentDay.getMonthValue() < 4) {
            return String.format("%02d-%02d", (year - 1) % 100, year % 100);
        }
        return String.format("%02d-%02d", year % 100, (year + 1) % 100);
    }
}
