package software.sigma.bu003.internship.vedmid_andrii.client.tehnomir.config;

public enum ApiUri {

    // Basket - Корзина клієнта
    BASKET_GET_ALL_POSITIONS("/basket/getPositions"),
    BASKET_ADD_POSITION("/basket/add"),
    BASKET_CLEAR("/basket/clear"),
    BASKET_DELETE_ONE("/basket/delete"),

    // Info - Довідкова інформація
    INFO_GET_CURRENCY("/info/getCurrency"),
    INFO_GET_ALL_BRANDS("/info/getBrands"),
    INFO_GET_ALL_SUPPLIERS("/info/getSupplier"),
    INFO_GET_PRODUCT_INFO("/info/getProductInfo"),
    INFO_GET_STATUSES("/info/getPositionStatuses"),

    // Order - Замовлення кліента
    ORDER_CREATE("/order/create"),
    ORDER_GET_ACTIVE("/order/getActive"),
    ORDER_SEARCH("/order/search"),
    ORDER_GET_POSITIONS("/order/getOrderPositions"),
    ORDER_GET_POSITION_INFO("/order/getPositionInfo"),

    // Price
    PRICE_POSITION_SEARCH("/price/search"),
    PRICE_GET_STOCK_PRICE("/price/getStockPrice"),

    // Test
    TEST_CONNECTION("/test/connect"),

    // Unload
    UNLOAD_LIST("/unload/search"),
    UNLOAD_GET_ONE("/unload/getData"),
    UNLOAD_GET_BOXES("/unload/getBoxesReadyToSend");

    public final String str;

    ApiUri(String label) {
        this.str = label;
    }
}
