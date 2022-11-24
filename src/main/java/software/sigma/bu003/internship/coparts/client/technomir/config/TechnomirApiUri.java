package software.sigma.bu003.internship.coparts.client.technomir.config;

public enum TechnomirApiUri {

    PRICE_POSITION_SEARCH("/price/search"),
    PRICE_GET_STOCK_PRICE("/price/getStockPrice");

    private final String str;

    TechnomirApiUri(String label) {
        this.str = label;
    }

    public String getStr() {
        return str;
    }
}
