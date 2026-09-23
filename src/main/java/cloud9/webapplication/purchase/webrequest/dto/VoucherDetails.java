package cloud9.webapplication.purchase.webrequest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public record VoucherDetails(
        String vchNo,
        String supplierName,
        String poNo,
        LocalDate poDate,
        LocalDate vchDate,
        List<ItemDetails> InventoryEntries,
        List<LedgerDetails> LedgerEntries,
        BigDecimal voucherAmount
) {
    public record ItemDetails(
            String itemLine,
            String itemName,
            String qty,
            String uom,
            Long rate,
            BigDecimal itemAmount
    ){}
    public record LedgerDetails(
            String ledLine,
            String ledgerName,
            String percentage,
            BigDecimal ledgerAmount
    ){}
}
