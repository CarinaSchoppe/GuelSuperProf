public class Cuboid extends Gameobject {

    private static final BuildObject TYPE = BuildObject.CUBOID;

    public Cuboid(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        var string = "";
        return string + TYPE.getSymbol();
    }
}
