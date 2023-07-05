public class Cuboid extends Gameobject {

    /**
     * Represents the type of a build object.
     * <p>
     * The possible types that can be assigned are:
     * - BuildObject.CUBOID
     * - BuildObject.SPHERE
     * - BuildObject.CYLINDER
     * <p>
     * This variable is declared as a private static final, ensuring that its value
     * cannot be modified after initialization.
     * <p>
     * Example usage:
     * ```
     * if (TYPE == BuildObject.CUBOID) {
     * // Perform actions specific to cuboid objects
     * } else if (TYPE == BuildObject.SPHERE) {
     * // Perform actions specific to sphere objects
     * } else if (TYPE == BuildObject.CYLINDER) {
     * // Perform actions specific to cylinder objects
     * }
     * ```
     */
    private static final BuildObject TYPE = BuildObject.CUBOID;

    /**
     * Constructs a Cuboid object with the given dimensions.
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
