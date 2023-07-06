package edu.kit.informatik;

/**
 * The edu.kit.informatik.Figure class represents a playing figure in the game.
 *
 * @author uyjam
 * @version 1.0
 */
public class Figure extends Gameobject {


    /**
     * The name of the object.
     */
    private String name;

    /**
     * The oldField variable represents the previous state of the game field.
     * It is a private variable of the current class and is used to keep track
     * of the state of the game field before any changes are made to it.
     *
     * @see Gamefield
     */
    private Gamefield oldField;

    /**
     * The owner of an object.
     *
     * The owner represents the person or entity that possesses or controls the object.
     * This variable is typically used in a class to track the owner of a particular instance.
     * This variable is marked as private to restrict direct access from outside the class.
     * To interact with or access the owner, appropriate getter and setter methods should be used.
     */
    private Player owner;

    /**
     * Creates a new edu.kit.informatik.Playingfigure object with the given name, x-coordinate, and y-coordinate.
     *
     * @param name the name of the playing figure
     * @param x    the x-coordinate of the playing figure's position
     * @param y    the y-coordinate of the playing figure's position
     */
    public Figure(String name, int x, int y) {
        super(x, y);
        this.name = name;
    }

    /**
     * Retrieves the game field element at the current position.
     *
     * @return the game field element at the current position
     */
    public Gamefield getGameField() {
        var game = Game.getInstance();
        return game.getPlayingField()[getY()][getX()];
    }

    /**
     * Sets the current game field for the figure.
     *
     * @param where The new game field to set.
     */
    public void setGameField(Gamefield where) {
        oldField = getGameField();
        var game = Game.getInstance();
        oldField.removeFigure(this);
        where.setFigure(this);
        setX(where.getX());
        setY(where.getY());
    }

    /**
     * Retrieves the owner of the object.
     *
     * @return The owner of the object as a edu.kit.informatik.Player.
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the object.
     *
     * @param owner the player object to be set as the owner
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Retrieves the name of an object.
     *
     * @return The name of the object as a String.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the object.
     *
     * @param name the new name for the object
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the old game field.
     *
     * @return The old game field.
     */
    public Gamefield getOldField() {
        return oldField;
    }
}
