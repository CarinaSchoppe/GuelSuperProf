package edu.kit.informatik;

/**
 * @author uyjam
 * @version 1.0
 */
public class Playingfigure extends Gameobject {


    private String name;

    private Gamefield oldField;

    private Player owner;

    public Playingfigure(String name, int x, int y) {
        super(x, y);
        this.name = name;
    }

    public Gamefield getGameField() {
        var game = Game.getInstance();
        return game.getPlayingField()[getY()][getX()];
    }

    public void setGameField(Gamefield where) {
        oldField = getGameField();
        var game = Game.getInstance();
        oldField.removeFigure(this);
        where.setFigure(this);
        setX(where.getX());
        setY(where.getY());
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gamefield getOldField() {
        return oldField;
    }
}
