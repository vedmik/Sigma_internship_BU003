package software.sigma.bu003.internship.coparts.client.technomir.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import software.sigma.bu003.internship.coparts.client.technomir.dto.responce.StockTechnomirPart;
import software.sigma.bu003.internship.coparts.client.technomir.dto.responce.TechnomirPart;
import software.sigma.bu003.internship.coparts.entity.Part;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TechnomirMapper {

    @Mapping(target = "supplierList",
            expression = "java(fromStockTechnomirPartToPartSupplier(stockTechnomirPart))")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description", source = "descriptionRus")
    Part fromStockTechnomirPartToPart(StockTechnomirPart stockTechnomirPart);

    default List<Part.Supplier> fromStockTechnomirPartToPartSupplier(StockTechnomirPart stockTechnomirPart){
        Part.Supplier supplier = new Part.Supplier();

        supplier.setPriceLogo("stock");
        supplier.setPrice(stockTechnomirPart.getPrice());
        supplier.setCurrency(stockTechnomirPart.getCurrency());

        return List.of(supplier);
    }

    @Mapping(target = "supplierList",
            expression = "java(fromTehnomirPartSupplierToPartSupplier(technomirPart.getRests()))")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description", source = "descriptionRus")
    Part fromTehnomirPartToPart(TechnomirPart technomirPart);

    List<Part.Supplier> fromTehnomirPartSupplierToPartSupplier(
            List<TechnomirPart.SupplierTechnomirPart> supplierTehnomirPartList);
}
