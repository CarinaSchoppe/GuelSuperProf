public class Playingfigure extends Gameobject {


    private String name;

    public Playingfigure(String name, int x, int y) {
        super(x, y);
        this.name = name;
    }

    public Gamefield getPlayingField() {
        var game = Game.getInstance();
        return game.getPlayingField()[getY()][getX()];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
