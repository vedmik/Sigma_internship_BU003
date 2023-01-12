package software.sigma.bu003.internship.coparts.client.technomir.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import software.sigma.bu003.internship.coparts.client.technomir.dto.responce.StockTechnomirPart;
import software.sigma.bu003.internship.coparts.client.technomir.dto.responce.SupplierTechnomirPart;
import software.sigma.bu003.internship.coparts.client.technomir.dto.responce.TechnomirPart;
import software.sigma.bu003.internship.coparts.part.entity.Part;
import software.sigma.bu003.internship.coparts.part.entity.SupplierPart;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TechnomirMapper {

    @Mapping(target = "supplierList",
            expression = "java(fromStockTechnomirPartToPartSupplier(stockTechnomirPart))")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description", source = "descriptionRus")
    Part fromStockTechnomirPartToPart(StockTechnomirPart stockTechnomirPart);

    default List<SupplierPart> fromStockTechnomirPartToPartSupplier(StockTechnomirPart stockTechnomirPart){
        SupplierPart supplier = SupplierPart.builder()
                .priceLogo("stock")
                .price(stockTechnomirPart.getPrice())
                .currency(stockTechnomirPart.getCurrency())
                .build();

        return List.of(supplier);
    }

    @Mapping(target = "supplierList",
            expression = "java(fromTehnomirPartSupplierToPartSupplier(technomirPart.getRests()))")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description", source = "descriptionRus")
    Part fromTehnomirPartToPart(TechnomirPart technomirPart);

    List<SupplierPart> fromTehnomirPartSupplierToPartSupplier(
            List<SupplierTechnomirPart> supplierTehnomirPartList);
}
