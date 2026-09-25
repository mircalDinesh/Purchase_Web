package cloud9.webapplication.purchase.webrequest.module;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name="ledger_entries")
@Data
public class VoucherLedger {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ledLine;
    private String ledgerName;
    private String percentage;
    private BigDecimal ledgerAmount;

   // private VoucherDetails voucherDetails;
}
