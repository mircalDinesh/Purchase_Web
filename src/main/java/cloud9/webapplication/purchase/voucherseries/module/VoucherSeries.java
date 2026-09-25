package cloud9.webapplication.purchase.voucherseries.module;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(
        name = "voucherseries",
        uniqueConstraints = @UniqueConstraint(columnNames = {"prefix",
                "financial_year"})
)
public class VoucherSeries {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "voucher_no_id_generator"
    )
    @SequenceGenerator(
            name = "voucher_no_id_generator",
            sequenceName = "voucher_no_id_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false)
    private String prefix;

    @Column(name = "financial_year", nullable = false)
    private String financialYear;

    @Column(name = "last_number", nullable = false)
    private Long lastNumber = 0L;

}
