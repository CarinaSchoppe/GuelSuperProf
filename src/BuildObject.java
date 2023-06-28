public enum BuildObject {

    CUBOID('C'), DOME('D');

    private final char symbol;

    BuildObject(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}
