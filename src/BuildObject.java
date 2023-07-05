public enum BuildObject {

    CUBOID('C'), DOME('D');

    private final char symbol;

    BuildObject(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public static BuildObject findBuildObject(char symbol) {
        for (BuildObject buildObject : BuildObject.values()) {
            if (buildObject.getSymbol() == symbol) {
                return buildObject;
            }
        }
        return null;
    }
}
