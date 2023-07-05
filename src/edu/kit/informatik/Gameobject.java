package edu.kit.informatik;

public abstract class Gameobject {

    /**
     * Represents the value of variable x.
     *
     * <p>
     * This private integer variable is used to store a value. The value can be accessed and modified
     * through getter and setter methods.
     * </p>
     *
     * @since 1.0
     */
    private int x;
    /**
     * Represents the value of a private integer variable.
     * The value of the variable can only be accessed within the scope of the class.
     */
    private int y;

    /**
     * Protected constructor for creating a edu.kit.informatik.Gameobject object.
     *
     * @param x the x-coordinate of the edu.kit.informatik.Gameobject
     * @param y the y-coordinate of the edu.kit.informatik.Gameobject
     */
    protected Gameobject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the value of x.
     *
     * @return The value of x.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the value of x.
     *
     * @param x The new value for x.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the value of the y coordinate.
     *
     * @return the y coordinate value
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the value of Y.
     *
     * @param y the new value of Y
     */
    public void setY(int y) {
        this.y = y;
    }
}
