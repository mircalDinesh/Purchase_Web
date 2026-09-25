package cloud9.webapplication.purchase.webrequest.module;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;


@Entity
@Table(name="inventory_entries")
@Data
public class VoucherInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemLine;
    private String itemName;
    private String qty;
    private String uom;
    private Long rate;
    private Integer gst;
    private Integer disc;
    private BigDecimal itemAmount;

  //  @ManyToOne(fetch = FetchType.LAZY)
  //  @JoinColumn(name = "voucher_id")
  //  private VoucherDetails voucherDetails;
}
