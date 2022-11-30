package software.sigma.bu003.internship.coparts.entity;

public enum Currency {
    UAH("UAH"),
    USD("USD"),
    EUR("EUR");

    private final String text;

    Currency(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
