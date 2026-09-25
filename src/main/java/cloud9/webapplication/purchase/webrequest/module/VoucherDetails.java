package cloud9.webapplication.purchase.webrequest.module;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private String createdBy;
    private LocalDate createdDate;
    private String voucherStatus;
    private String tallyStatus;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "superkey")
    private List<VoucherInventory> inventoryEntries;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "superkey")
    private List<VoucherLedger> ledgerEntries;
}
