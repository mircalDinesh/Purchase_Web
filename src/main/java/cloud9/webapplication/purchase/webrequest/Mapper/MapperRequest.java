package cloud9.webapplication.purchase.webrequest.Mapper;

import cloud9.webapplication.purchase.webrequest.dto.RequestVoucher;
import cloud9.webapplication.purchase.webrequest.module.VoucherInventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MapperRequest {

    @Mapping(source = "requestInventory", target = "inventoryEntries")
    RequestVoucher toEntity(RequestVoucher request);


    @Mapping(source = "ItemDetails", target = "inventoryEntries")
    VoucherInventory toInvEntity(RequestVoucher request);

}
