package edu.kit.informatik;

/**
 * @author uyjam
 * @version 1.0
 */
public enum BuildObject {

    /**
     * Cuboid as object for playing game.
     */
    CUBOID('C'),
    /**
     * Dome as object for playing game.
     */
    DOME('D'); //Dome

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
