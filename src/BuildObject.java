public enum BuildObject {

    CUBOID('C'), DOME('D');

    private final char symbol;

    BuildObject(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    /**
     * Finds a BuildObject based on a given symbol.
     *
     * @param symbol The symbol used to identify the BuildObject.
     * @return The corresponding BuildObject, or null if no matching symbol is found.
     */
    public static BuildObject findBuildObject(char symbol) {
        for (BuildObject buildObject : BuildObject.values()) {
            if (buildObject.getSymbol() == symbol) {
                return buildObject;
            }
        }
        return null;
    }
}
