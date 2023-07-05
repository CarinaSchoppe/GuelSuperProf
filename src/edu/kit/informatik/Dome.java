package edu.kit.informatik;

public class Dome extends Gameobject {

    /**
     * Represents a build object type.
     *
     * <p>
     * This variable is used to specify the type of a build object. It is of type {@link BuildObject} and is marked as
     * <code>private</code>, <code>static</code>, and <code>final</code>.
     * </p>
     *
     * <p>
     * The value assigned to this variable is {@link BuildObject#DOME}, indicating that the build object type is a "dome".
     * </p>
     *
     * @see BuildObject
     */
    private static final BuildObject TYPE = BuildObject.DOME;

    /**
     * Constructs a new edu.kit.informatik.Dome object with the specified coordinates.
     *
     * @param x the x-coordinate of the edu.kit.informatik.Dome object
     * @param y the y-coordinate of the edu.kit.informatik.Dome object
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
