package software.sigma.bu003.internship.coparts.client.technomir.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import software.sigma.bu003.internship.coparts.client.technomir.dto.responce.StockTechnomirPart;
import software.sigma.bu003.internship.coparts.client.technomir.dto.responce.SupplierTechnomirPart;
import software.sigma.bu003.internship.coparts.client.technomir.dto.responce.TechnomirPart;
import software.sigma.bu003.internship.coparts.part.entity.Currency;
import software.sigma.bu003.internship.coparts.part.entity.Part;
import software.sigma.bu003.internship.coparts.part.entity.SupplierPart;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { TechnomirMapperImpl.class })
class TechnomirMapperTest {

    @Autowired
    private TechnomirMapper sut;

    private final String BRAND = "Brand";
    private final String CODE = "Code";

    private final String PRICE = "100";
    private final Currency CURRENCY = Currency.USD;
    private final String PRICE_LOGO_STOCK = "stock";

    private StockTechnomirPart testStockTechnomirPart;
    private TechnomirPart testTechnomirPart;
    private SupplierTechnomirPart testSupplierTechnomirPart;

    private SupplierPart expectedSupplierPart;

    private final Part expectedPart = new Part(BRAND, CODE);

    @BeforeEach
    public void init() {
        String PRICE_LOGO = "supplier";
        String DESCRIPTION = "Desc";

        expectedSupplierPart = SupplierPart.builder()
                .priceLogo(PRICE_LOGO)
                .price(PRICE)
                .currency(CURRENCY)
                .build();


        expectedPart.setDescription(DESCRIPTION);
        expectedPart.setSupplierList(List.of(expectedSupplierPart));

        testStockTechnomirPart = StockTechnomirPart.builder()
                .brand(BRAND)
                .code(CODE)
                .descriptionRus(DESCRIPTION)
                .price(PRICE)
                .currency(CURRENCY)
                .build();

        testSupplierTechnomirPart = SupplierTechnomirPart.builder()
                .currency(CURRENCY)
                .priceLogo(PRICE_LOGO)
                .price(PRICE)
                .build();

        testTechnomirPart = TechnomirPart.builder()
                .brand(BRAND)
                .code(CODE)
                .descriptionRus(DESCRIPTION)
                .rests(List.of(testSupplierTechnomirPart))
                .build();
    }

    @Test
    void shouldReturnPartFromStockTechnomirPart() {
        expectedPart.setSupplierList(
                expectedPart.getSupplierList().stream()
                        .peek(supplier -> supplier.setPriceLogo(PRICE_LOGO_STOCK))
                        .toList()
        );

        Part actual = sut.fromStockTechnomirPartToPart(testStockTechnomirPart);

        assertEquals(expectedPart, actual);
    }

    @Test
    void shouldReturnPartSupplierFromStockTechnomirPart() {
        SupplierPart expectedPartSupplier = SupplierPart.builder()
                .priceLogo(PRICE_LOGO_STOCK)
                .price(PRICE)
                .currency(CURRENCY)
                .build();

        List<SupplierPart> actual = sut.fromStockTechnomirPartToPartSupplier(testStockTechnomirPart);

        assertEquals(List.of(expectedPartSupplier), actual);
    }

    @Test
    void shouldReturnPartFromTehnomirPart() {
        Part actual = sut.fromTehnomirPartToPart(testTechnomirPart);

        assertEquals(expectedPart, actual);
    }

    @Test
    void shouldReturnPartSupplierFromTehnomirPartSupplier() {
        List<SupplierPart> actual = sut.fromTehnomirPartSupplierToPartSupplier(List.of(testSupplierTechnomirPart));

        assertEquals(List.of(expectedSupplierPart), actual);
    }
}