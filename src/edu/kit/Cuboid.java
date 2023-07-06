package edu.kit;

/**
 * Represents a cuboid object.
 *
 * @author uyjam
 * @version 1.0
 */
public class Cuboid extends Gameobject {

    /**
     * Represents the type of a build object.
     * The possible types that can be assigned are:
     */
    private static final BuildObject TYPE = BuildObject.CUBOID;

    /**
     * Constructs a edu.kit.Cuboid object with the given dimensions.
     *
     * @param x the length of the cuboid
     * @param y the width of the cuboid
     */
    public Cuboid(int x, int y) {
        super(x, y);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        var string = "";
        return string + TYPE.getSymbol();
    }
}
