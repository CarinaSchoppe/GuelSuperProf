package edu.kit.informatik;

/**
 * @author uyjam
 * @version 1.0
 */
public abstract class Gameobject {

    private int x;
    private int y;

    protected Gameobject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
