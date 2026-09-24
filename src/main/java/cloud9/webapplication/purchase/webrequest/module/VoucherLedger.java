package cloud9.webapplication.purchase.webrequest.module;


import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="ledger_entries")
public class VoucherLedger {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ledLine;
    private String ledgerName;
    private String percentage;
    private BigDecimal ledgerAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher_id")
    private VoucherDetails voucherDetails;
}
