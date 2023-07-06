package edu.kit.informatik;

/**
 * The edu.kit.informatik.Player class represents a player of the game. 
 * Each player has a name and two playing figures.
 * The player can draw god cards, move figures, and build objects on the game field.
 *
 * @author uyjam
 * @version 1.0
 */
public class Player {


    /**
     * A class representing an array of playing figures.
     * This class provides a container for storing an array of edu.kit.informatik.Playingfigure objects.
     */
    private final Figure[] figures;
    /**
     * The name of an object.
     * This variable represents the name of an object. It is a constant value and cannot be modified once initialized.
     */
    private final String name;
    /**
     * Represents the current status of whether drawing is permitted or not.
     * The value of this variable determines whether the drawing operation can be performed or not.
     * If this variable is set to 'true', it indicates that drawing is allowed. On the other hand, if
     * it is set to 'false', drawing is not permitted.
     * This variable is private and can only be accessed within the class it is defined in.
     */
    private boolean canDrawNow = true;
    /**
     * Determines if the object can move at the current time.
     *
     * @return true if the object can move now, false otherwise.
     */
    private boolean canMoveNow = true;
    /**
     * Determines whether the build can be started immediately.
     * This variable represents whether the build can be initiated right away. A value of true indicates that the build
     * can be started immediately, while a value of false indicates that the build should be delayed.
     *
     * @return true if the build can be started now, false otherwise.
     */
    private boolean canBuildNow = false;
    /**
     * Keeps track of the number of god cards drawn.
     */
    private int godCardsDrawn = 0;

    /**
     * Indicates whether Athena is blocked or not.
     * This variable should only be accessed and modified by the relevant logic handling Athena's blocking status.
     * The default value of {@code athenaBlocked} is {@code false}, indicating that Athena is initially not blocked.
     *
     * @see #isAthenaBlocked()
     * @see #setAthenaBlocked(boolean)
     */
    private boolean athenaBlocked = false;

    /**
     * Determines if the movement of Athena is currently blocked.
     *
     * @return {@code true} if Athena's movement is blocked, {@code false} otherwise.
     */
    private boolean athenaBlockedMove = false;

    /**
     * Represents the status of the Apollo move.
     * The variable is used to determine whether an Apollo move is in progress or not.
     * It is used as a flag to control the flow of the program when interacting with the Apollo move.
     *
     */
    private boolean apolloMove = false;
    /**
     * Represents the status of Artemis movement.
     * This variable indicates whether Artemis is currently in motion or not.
     * When the value is set to true, it means Artemis is moving. On the other hand,
     * when the value is set to false, it means Artemis is not moving.
     *
     */
    private boolean artemisMove = false;

    /**
     * Represents the status of an Atlas build.
     * This variable is used to determine whether an Atlas build is currently happening or not.
     * It is a boolean value that is true if an Atlas build is in progress, and false otherwise.
     * The value of this variable can only be accessed within the class it is declared in.
     */
    private boolean atlasBuild = false;

    /**
     * Determines if the Demeter build is enabled or disabled.
     *
     * @return true if the Demeter build is enabled, false otherwise
     */
    private boolean demeterBuild = false;

    /**
     * Indicates if the Hermes Teleport is active or not.
     */
    private boolean hermesTeleport = false;
    /**
     * Determines whether the current turn can be ended.
     *
     * @return <code>true</code> if the current turn can be ended, <code>false</code> otherwise.
     */
    private boolean canEndTurn = false;

    /**
     * Represents a variable indicating whether a build has been performed.
     */
    private boolean hasBuild = false;
    /**
     * Indicates whether an object has moved.
     * The hasMoved variable is used to track the movement state of an object.
     * It is set to true when the object has moved, and false otherwise.
     * Example usage:
     *
     * @return true if the object has moved, false otherwise
     */
    private boolean hasMoved = false;

    private static final int THREE = 3;
    /**
     * Represents a string constant indicating that someone wins.
     */
    private static final String WINS = " wins!";

    /**
     * Creates a new player object with two playing figures and a name.
     *
     * @param figure1 the first playing figure for the player
     * @param figure2 the second playing figure for the player
     * @param name    the name of the player
     */
    public Player(Figure figure1, Figure figure2, String name) {
        figures = new Figure[]{figure1, figure2};
        Game.getInstance().getPlayingField()[figure1.getY()][figure1.getX()].setFigure(figure1);
        Game.getInstance().getPlayingField()[figure2.getY()][figure2.getX()].setFigure(figure2);
        this.name = name;
    }

    /**
     * Draws a Godcard for the player.
     *
     * @param godcard the edu.kit.informatik.Godcard to be drawn
     * @throws IllegalStateException if it is not currently allowed to draw a edu.kit.informatik.Godcard
     */
    public void drawGodCard(Godcard godcard) {
        if (!canDrawNow) {
            System.out.println("ERROR: You can't draw a godcard now");
            return;
        }
        //check if godcard is already drawn
        if (!Game.getInstance().getGodcards().contains(godcard)) {
            System.out.println("ERROR: This godcard is already drawn");
            return;
        }

        if (godCardsDrawn >= THREE) {
            System.out.println("ERROR: You can only draw 3 godcards");
            return;
        }
        Game.getInstance().getGodcards().remove(godcard);
        switch (godcard) {
            case APOLLO -> apolloMove = true;
            case ARTEMIS -> artemisMove = true;
            case ATLAS -> atlasBuild = true;
            case DEMETER -> demeterBuild = true;
            case HERMES -> hermesTeleport = true;
            case ATHENA -> athenaBlockedMove = true;
        }
        System.out.println(Main.OK);
        canDrawNow = false;
        if (hermesTeleport) {
            if (!canOnlySurrenderMove()) return;
            canMoveNow = false;
            canBuildNow = false;
        }
        godCardsDrawn++;
    }

    /**
     * Checks if the current player can only make a surrender move.
     *
     * @return true if the current player can only make a surrender move, false otherwise
     */
    private boolean canOnlySurrenderMove() {
        //check if there is any move possible
        for (var figure : figures) {
            for (var fields : Game.getInstance().getPlayingField()) {
                for (var field : fields) {
                    if (!Game.getInstance().isReachable(figure, field)) {
                        continue;
                    }
                    if (field.getHeightSquares() == figure.getGameField().getHeightSquares()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Builds a specified object on a game field.
     * Throws exceptions if the build conditions are not met.
     *
     * @param whatToBuild  The object to build
     * @param whereToBuild The game field on which to build the object
     * @throws IllegalStateException    If the build cannot be performed at the current state
     * @throws IllegalArgumentException If the build conditions are not met
     */
    public void build(BuildObject whatToBuild, Gamefield whereToBuild) {
        if (!canBuildNow) {
            System.out.println("ERROR: You can't build now");
            return;
        }
        if (hasBuild && !demeterBuild) {
            System.out.println("ERROR: Allready build!");
            return;
        }
        canMoveNow = false;
        hasMoved = true;
        if (demeterBuild) {
            canEndTurn = true;
            demeterBuild = false;
        } else {
            canBuildNow = false;
            hasBuild = true;
        }
        //one figure needs to be adjecent to the field
        var adj = false;
        for (var figure : figures) {
            if (figure.getGameField().isAdjacent(whereToBuild)) {
                adj = true;
                break;
            }
        }

        if (!adj) {
            System.out.println("ERROR: No figure adjecent to this field");
            return;
        }

        if (!whereToBuild.isBuildable()) {
            System.out.println("ERROR: Not Buildable");
            return;
        }
        if (whatToBuild == BuildObject.DOME) {
            if (Game.getInstance().getDomeList().isEmpty()) {
                System.out.println("ERROR: No domes left");
                return;
            }

            var dome = Game.getInstance().getDomeList().get(0);
            Game.getInstance().getDomeList().remove(0);
            //player wants to build a dome: if atlas is active can build at any height
            if (atlasBuild || whereToBuild.isDomeable()) {
                whereToBuild.getGameobjects()[whereToBuild.getHeightSquares()] = dome;
            } else {
                System.out.println("ERROR: Not domeable");
                return;
            }
        } else if (whatToBuild == BuildObject.CUBOID) {
            //check if enough cuboids are left
            if (Game.getInstance().getCuboidList().isEmpty()) {
                System.out.println("ERROR: No cuboids left");
                return;
            }

            var cube = Game.getInstance().getCuboidList().get(0);
            Game.getInstance().getCuboidList().remove(0);
            whereToBuild.getGameobjects()[whereToBuild.getHeightSquares()] = cube;
        }

        if (Game.getInstance().checkWinning()) {
            System.out.println(name + WINS);
            Game.getInstance().setRunning(false);
        } else {
            System.out.println(Main.OK);
        }


    }

    /**
     * Moves the given playing figure to the specified game field.
     *
     * @param figure The playing figure to be moved.
     * @param where  The game field to which the playing figure will be moved.
     * @throws IllegalStateException    If the move cannot be performed due to certain conditions.
     * @throws IllegalArgumentException If the move is not valid.
     */
    public void moveFigure(Figure figure, Gamefield where) {
        if (!canMoveNow) {
            System.out.println("ERROR: You can't move now");
            return;
        }
        var oldPosition = figure.getGameField();
        //check if figure is part of this player
        if (!figure.getOwner().equals(this)) {
            System.out.println("ERROR: This figure is not part of this player");
            return;
        }
        if (where.equals(oldPosition)) {
            System.out.println("ERROR: Move to old Position");
            return;
        }
        if (hasMoved && !artemisMove) {
            System.out.println("ERROR: Already moved");
            return;
        }
        if (artemisMove) {
            canBuildNow = true;
            artemisMove = false;
        } else {
            hasMoved = true;
            canMoveNow = false;
            canBuildNow = true;
        }
        if (!Game.getInstance().isReachable(figure, where)) {
            System.out.println("ERROR: This field is not reachable");
            return;
        }
        if (hermesTeleport && where.getHeightSquares() != figure.getGameField().getHeightSquares()) {
            System.out.println("ERROR: You can't teleport to a different height");
            return;
        }
        if (where.isOccupied() && apolloMove) {
            var otherFigure = where.getPlayingFigure();
            figure.setGameField(where);
            otherFigure.setGameField(oldPosition);
        } else {
            figure.setGameField(where);
        }
        if (Game.getInstance().checkWinning()) {
            System.out.println(name + WINS);
        } else {
            System.out.println(Main.OK);
        }
        if (athenaBlockedMove && (figure.getGameField().getHeightSquares() > oldPosition.getHeightSquares())) {
            Game.getInstance().getOpponent(this).setAthenaBlocked(true);
        }
    }

    /**
     * Checks if the teleport ability of Hermes is active.
     *
     * @return true if Hermes teleport ability is active, false otherwise.
     */
    public boolean isHermesTeleport() {
        return hermesTeleport;
    }

    /**
     * Ends the current player's turn.
     * This method checks if the turn can be ended and throws an exception if not.
     * It then resets various flags and attributes related to the game state.
     * Finally, it sets the current player to the opponent of the current player.
     *
     * @throws IllegalStateException if the turn cannot be ended
     */
    public void endTurn() {
        checkEndTurn();
        if (!canEndTurn) {
            System.out.println("ERROR: You can't end your turn yet");
            return;
        }
        athenaBlocked = false;
        apolloMove = false;
        artemisMove = false;
        atlasBuild = false;
        athenaBlockedMove = false;
        demeterBuild = false;
        hermesTeleport = false;
        canEndTurn = false;
        hasBuild = false;
        hasMoved = false;
        canMoveNow = true;
        canDrawNow = true;
        Game.getInstance().setCurrentPlayer(Game.getInstance().getOpponent(this));
        System.out.println(Game.getInstance().getCurrentPlayer().name);
    }

    /**
     * Checks if the turn can be ended based on whether the player has performed
     * both a build and a move action.
     */
    private void checkEndTurn() {
        if (hasBuild && hasMoved) {
            canEndTurn = true;
        }
    }

    /**
     * Stops the current game and declares the opponent as the winner.
     */
    public void surrender() {
        Game.getInstance().setRunning(false);
        System.out.println(Game.getInstance().getOpponent(this).name + WINS);
    }

    /**
     * Returns whether Athena is blocked or not.
     *
     * @return true if Athena is blocked, false otherwise
     */
    public boolean isAthenaBlocked() {
        return athenaBlocked;
    }

    /**
     * Sets the block status of Athena.
     *
     * @param athenaBlocked {@code true} to block Athena, {@code false} otherwise
     */
    public void setAthenaBlocked(boolean athenaBlocked) {
        this.athenaBlocked = athenaBlocked;
    }

    /**
     * Checks if the move is related to Apollo.
     *
     * @return true if the move is an Apollo move, false otherwise.
     */
    public boolean isApolloMove() {
        return apolloMove;
    }

    /**
     * Returns the array of edu.kit.informatik.Playingfigure objects.
     *
     * @return the array of edu.kit.informatik.Playingfigure objects
     */
    public Figure[] getFigures() {
        return figures;
    }


    /**
     * Sets the Demeter build flag to enable or disable Demeter build.
     *
     * @param demeterBuild the flag to enable or disable Demeter build
     */
    public void setDemeterBuild(boolean demeterBuild) {
        this.demeterBuild = demeterBuild;
    }

    /**
     * Returns the name associated with this object.
     *
     * @return the name of the object as a String.
     */
    public String getName() {
        return name;
    }
}
