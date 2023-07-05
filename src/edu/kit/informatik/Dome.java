package edu.kit.informatik;

/**
 * @author uyjam
 * @version 1.0
 */
public class Dome extends Gameobject {

    private static final BuildObject TYPE = BuildObject.DOME;

    public Dome(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        var string = "";
        return string + TYPE.getSymbol();
    }
}
