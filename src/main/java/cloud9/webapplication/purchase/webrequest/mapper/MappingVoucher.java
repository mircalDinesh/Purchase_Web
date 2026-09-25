package cloud9.webapplication.purchase.webrequest.mapper;

import cloud9.webapplication.purchase.webrequest.dto.RequestVoucher;
import cloud9.webapplication.purchase.webrequest.module.VoucherDetails;
import cloud9.webapplication.purchase.webrequest.module.VoucherInventory;
import cloud9.webapplication.purchase.webrequest.module.VoucherLedger;
import org.mapstruct.*;

import javax.xml.transform.Source;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MappingVoucher {

    @Mapping(source = "requestInventory", target = "inventoryEntries")
    @Mapping(source = "requestLedgerEntry", target = "ledgerEntries")
        // adjust to your actual target field name
    VoucherDetails toEntity(RequestVoucher request);

    VoucherInventory toInvEntity(RequestVoucher.ItemDetails itemDetails);

    // Same pattern if you also need ledger entries mapped to their own entity
    VoucherLedger toLedgerEntity(RequestVoucher.LedgerDetails ledgerDetails);


    @AfterMapping
    default void validateEntries(@MappingTarget VoucherDetails voucherDetails) {
        boolean noInventory = voucherDetails.getInventoryEntries() == null
                || voucherDetails.getInventoryEntries().isEmpty();
      //  boolean noLedger = voucherDetails.getLedgerEntries() == null
           //     || voucherDetails.getLedgerEntries().isEmpty();

        if (noInventory) {
            throw new IllegalArgumentException(
                    "Voucher Contains Must be Contain atLeast one Inventory Entry");
        }
    }
}