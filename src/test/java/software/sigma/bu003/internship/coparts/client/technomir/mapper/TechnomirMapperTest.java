package software.sigma.bu003.internship.coparts.client.technomir.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import software.sigma.bu003.internship.coparts.client.technomir.dto.responce.StockTechnomirPart;
import software.sigma.bu003.internship.coparts.client.technomir.dto.responce.TechnomirPart;
import software.sigma.bu003.internship.coparts.entity.Currency;
import software.sigma.bu003.internship.coparts.entity.Part;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { TechnomirMapperImpl.class })
class TechnomirMapperTest {

    @Autowired
    private TechnomirMapper mapperUnderTest;

    private final String BRAND = "Brand";
    private final String CODE = "Code";

    private final String PRICE = "100";
    private final Currency CURRENCY = Currency.USD;
    private final String PRICE_LOGO_STOCK = "stock";

    private final StockTechnomirPart testStockTechnomirPart = new StockTechnomirPart();
    private final TechnomirPart testTechnomirPart = new TechnomirPart();
    private final TechnomirPart.SupplierTechnomirPart testSupplierTechnomirPart =
            testTechnomirPart.new SupplierTechnomirPart();
    private final Part.Supplier expectedSupplierPart = new Part.Supplier();

    private final Part expectedPart = new Part(BRAND, CODE);

    @BeforeEach
    public void init() {
        String PRICE_LOGO = "supplier";
        expectedSupplierPart.setPriceLogo(PRICE_LOGO);
        expectedSupplierPart.setPrice(PRICE);
        expectedSupplierPart.setCurrency(CURRENCY);

        String DESCRIPTION = "Desc";
        expectedPart.setDescription(DESCRIPTION);
        expectedPart.setSupplierList(List.of(expectedSupplierPart));

        testStockTechnomirPart.setBrand(BRAND);
        testStockTechnomirPart.setCode(CODE);
        testStockTechnomirPart.setDescriptionRus(DESCRIPTION);
        testStockTechnomirPart.setPrice(PRICE);
        testStockTechnomirPart.setCurrency(CURRENCY);

        testSupplierTechnomirPart.setCurrency(CURRENCY);
        testSupplierTechnomirPart.setPriceLogo(PRICE_LOGO);
        testSupplierTechnomirPart.setPrice(PRICE);

        testTechnomirPart.setBrand(BRAND);
        testTechnomirPart.setCode(CODE);
        testTechnomirPart.setDescriptionRus(DESCRIPTION);
        testTechnomirPart.setRests(List.of(testSupplierTechnomirPart));
    }

    @Test
    void shouldReturnPartFromStockTechnomirPart() {
        expectedPart.setSupplierList(
                expectedPart.getSupplierList().stream()
                        .peek(supplier -> supplier.setPriceLogo(PRICE_LOGO_STOCK))
                        .toList()
        );

        Part actual = mapperUnderTest.fromStockTechnomirPartToPart(testStockTechnomirPart);

        assertEquals(expectedPart, actual);
    }

    @Test
    void shouldReturnPartSupplierFromStockTechnomirPart() {
        Part.Supplier expectedPartSupplier = new Part.Supplier();

        expectedPartSupplier.setPriceLogo(PRICE_LOGO_STOCK);
        expectedPartSupplier.setPrice(PRICE);
        expectedPartSupplier.setCurrency(CURRENCY);

        List<Part.Supplier> actual = mapperUnderTest.fromStockTechnomirPartToPartSupplier(testStockTechnomirPart);

        assertEquals(List.of(expectedPartSupplier), actual);
    }

    @Test
    void shouldReturnPartFromTehnomirPart() {
        Part actual = mapperUnderTest.fromTehnomirPartToPart(testTechnomirPart);

        assertEquals(expectedPart, actual);
    }

    @Test
    void shouldReturnPartSupplierFromTehnomirPartSupplier() {
        List<Part.Supplier> actual = mapperUnderTest.fromTehnomirPartSupplierToPartSupplier(List.of(testSupplierTechnomirPart));

        assertEquals(List.of(expectedSupplierPart), actual);
    }
}