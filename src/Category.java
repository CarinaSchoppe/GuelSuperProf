public enum Category {

    SMALL(2.9),
    MEDIUM(4.3),
    LARGE(6.6),
    TRANSPORT(9.9);


    private final double price;

    Category(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
