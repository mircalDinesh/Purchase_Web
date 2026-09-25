package cloud9.webapplication.purchase.webrequest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@JsonRootName("RequestVoucher")
public record RequestVoucher(
        String vchNo,
        String supplierName,
        String poNo,
        LocalDate poDate,
        LocalDate vchDate,
        @JsonProperty("requestInventory")
        List<ItemDetails> requestInventory,
        @JsonProperty("requestLedgerEntry")
        List<LedgerDetails> requestLedgerEntry,
        BigDecimal totalQty,
        String units,
        BigDecimal voucherAmount,
        String createdBy,
        LocalDate createdDate,
        String voucherStatus
) {
    public record ItemDetails(
            String itemLine,
            String itemName,
            String qty,
            String uom,
            Long rate,
            Integer disc,
            Integer gst,
            BigDecimal itemAmount
    ){}
    public record LedgerDetails(
            String ledLine,
            String ledgerName,
            String percentage,
            BigDecimal ledgerAmount
    ){}
}
