package edu.kit;

/**
 * Represents a dome object.
 *
 * @author uyjam
 * @version 1.0
 */
public class Dome extends Gameobject {

    /**
     * Represents a build object type.
     *
     *
     * @see BuildObject
     */
    private static final BuildObject TYPE = BuildObject.DOME;

    /**
     * Constructs a new edu.kit.Dome object with the specified coordinates.
     *
     * @param x the x-coordinate of the edu.kit.Dome object
     * @param y the y-coordinate of the edu.kit.Dome object
     */
    public Dome(int x, int y) {
        super(x, y);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return the string representation of the object
     */
    @Override
    public String toString() {
        var string = "";
        return string + TYPE.getSymbol();
    }
}
