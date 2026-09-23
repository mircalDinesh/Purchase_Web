package cloud9.webapplication.purchase.webrequest.module;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "voucher_details")
@Data
public class VoucherDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vch_no", nullable = false, unique = true)
    private String vchNo;

    private String supplierName;
    private String poNo;
    private LocalDate poDate;
    private LocalDate vchDate;
    private BigDecimal totalQty;
    private String units;
    private BigDecimal voucherAmount;

    @OneToMany(mappedBy = "voucherDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoucherInventory> inventoryEntries;

    @OneToMany(mappedBy = "voucherDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoucherLedger> ledgerEntries;
}
