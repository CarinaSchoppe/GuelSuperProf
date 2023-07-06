package edu.kit.informatik;

/**
 * Represents a build object type.
 *
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
    DOME('D');
    /**
     * The symbol used to identify the edu.kit.informatik.BuildObject.
     */
    private final char symbol;

    /**
     * Constructs a new edu.kit.informatik.BuildObject with the specified symbol.
     *
     * @param symbol The symbol used to identify the edu.kit.informatik.BuildObject.
     */

    BuildObject(char symbol) {
        this.symbol = symbol;
    }

    /**
     * Finds a edu.kit.informatik.BuildObject based on a given symbol.
     *
     * @param symbol The symbol used to identify the edu.kit.informatik.BuildObject.
     * @return The corresponding edu.kit.informatik.BuildObject, or null if no matching symbol is found.
     */
    public static BuildObject findBuildObject(char symbol) {
        for (BuildObject buildObject : BuildObject.values()) {
            if (buildObject.getSymbol() == symbol) {
                return buildObject;
            }
        }
        return null;
    }

    /**
     * Returns the symbol used to identify the edu.kit.informatik.BuildObject.
     *
     * @return The symbol used to identify the edu.kit.informatik.BuildObject.
     */
    public char getSymbol() {
        return symbol;
    }
}
