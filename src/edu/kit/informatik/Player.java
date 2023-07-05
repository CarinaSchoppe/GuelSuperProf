package edu.kit.informatik;

/**
 * The edu.kit.informatik.Player class represents a player of the game. Each player has a name and two playing figures.
 * The player can draw god cards, move figures, and build objects on the game field.
 */

public class Player {


    /**
     * A class representing an array of playing figures.
     * This class provides a container for storing an array of edu.kit.informatik.Playingfigure objects.
     */
    private final Playingfigure[] figures;
    /**
     * The name of an object.
     *
     * This variable represents the name of an object. It is a constant value and cannot be modified once initialized.
     *
     * @since 1.0
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
    private boolean canBuildNow;
    /**
     * Keeps track of the number of god cards drawn.
     */
    private int godCardsDrawn = 0;

    /**
     * Indicates whether Athena is blocked or not.
     *
     * The value of this variable determines if Athena, the virtual assistant, is currently blocked or not.
     * When Athena is blocked, it means that the user has restricted or disabled its functionality.
     *
     * This variable should only be accessed and modified by the relevant logic handling Athena's blocking status.
     * The default value of {@code athenaBlocked} is {@code false}, indicating that Athena is initially not blocked.
     *
     * @see #isAthenaBlocked()
     * @see #setAthenaBlocked(boolean)
     */
    private boolean athenaBlocked;

    /**
     * Determines if the movement of Athena is currently blocked.
     *
     * @return {@code true} if Athena's movement is blocked, {@code false} otherwise.
     */
    private boolean athenaBlockedMove;

    /**
     * Represents the status of the Apollo move.
     *
     * The variable is used to determine whether an Apollo move is in progress or not.
     * It is used as a flag to control the flow of the program when interacting with the Apollo move.
     *
     * @since No specific version
     */
    private boolean apolloMove;
    /**
     * Represents the status of Artemis movement.
     *
     * This variable indicates whether Artemis is currently in motion or not.
     * When the value is set to true, it means Artemis is moving. On the other hand,
     * when the value is set to false, it means Artemis is not moving.
     *
     * @since 1.0
     */
    private boolean artemisMove;

    /**
     * Represents the status of an Atlas build.
     * This variable is used to determine whether an Atlas build is currently happening or not.
     * It is a boolean value that is true if an Atlas build is in progress, and false otherwise.
     * The value of this variable can only be accessed within the class it is declared in.
     */
    private boolean atlasBuild;

    /**
     * Determines if the Demeter build is enabled or disabled.
     *
     * @return true if the Demeter build is enabled, false otherwise
     */
    private boolean demeterBuild;

    /**
     * Indicates if the Hermes Teleport is active or not.
     */
    private boolean hermesTeleport;
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
     *
     * The hasMoved variable is used to track the movement state of an object.
     * It is set to true when the object has moved, and false otherwise.
     * Example usage:
     *
     * @return true if the object has moved, false otherwise
     */
    private boolean hasMoved = false;

    /**
     * Creates a new player object with two playing figures and a name.
     *
     * @param playingfigure1 the first playing figure for the player
     * @param playingfigure2 the second playing figure for the player
     * @param name           the name of the player
     */
    public Player(Playingfigure playingfigure1, Playingfigure playingfigure2, String name) {
        figures = new Playingfigure[]{playingfigure1, playingfigure2};
        Game.getInstance().getPlayingField()[playingfigure1.getY()][playingfigure1.getX()].setFigure(playingfigure1);
        Game.getInstance().getPlayingField()[playingfigure2.getY()][playingfigure2.getX()].setFigure(playingfigure2);
        this.name = name;
    }


    /**
     * Draws a Godcard for the player.
     *
     * @param godcard the edu.kit.informatik.Godcard to be drawn
     * @throws IllegalStateException if it is not currently allowed to draw a edu.kit.informatik.Godcard
     */
    public void drawGodCard(Godcard godcard) {

        if (!canDrawNow) throw new IllegalStateException("ERROR: You can't draw a godcard now");
        //check if godcard is already drawn
        if (!Game.getInstance().getGodcards().contains(godcard)) {
            throw new IllegalStateException("ERROR: This godcard is already drawn");
        }

        if (godCardsDrawn > 3) {
            throw new IllegalStateException("ERROR: You can only draw 2 godcards");
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
        System.out.println("OK");
        canDrawNow = false;
        if (canOnlySurrenderMove() && hermesTeleport) {
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
                    if (Game.getInstance().isReachable(figure, field))
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
        if (!canBuildNow) throw new IllegalStateException("ERROR: You can't build now");
        if (hasBuild && !demeterBuild) {
            throw new IllegalArgumentException("ERROR: Allready build!");
        }
        if (demeterBuild) {
            demeterBuild = false;
        } else {
            canBuildNow = false;
            hasBuild = true;
        }


        //one figure needs to be adjecent to the field

        var adj = false;

        for (var figure : figures) {
            if (figure.getGameField().isAdjecent(whereToBuild)) {
                adj = true;
                break;
            }
        }

        if (!adj) throw new IllegalArgumentException("ERROR: No figure adjecent to this field");

        if (!whereToBuild.isBuildable()) throw new IllegalArgumentException("ERROR: Not Buildable");

        if (whatToBuild == BuildObject.DOME) {
            if (Game.getInstance().getDomeList().isEmpty()) {
                throw new IllegalStateException("ERROR: No domes left");
            }

            var dome = Game.getInstance().getDomeList().get(0);
            Game.getInstance().getDomeList().remove(0);
            //player wants to build a dome: if atlas is active can build at any height
            if (atlasBuild || whereToBuild.isDomeable()) {
                whereToBuild.getGameobjects()[whereToBuild.getHeightSquares()] = dome;
            }
        } else if (whatToBuild == BuildObject.CUBOID) {
            //check if enough cuboids are left
            if (Game.getInstance().getCuboidList().isEmpty()) {
                throw new IllegalStateException("ERROR: No cuboids left");
            }

            var cube = Game.getInstance().getCuboidList().get(0);
            Game.getInstance().getCuboidList().remove(0);
            whereToBuild.getGameobjects()[whereToBuild.getHeightSquares()] = cube;
        }

        if (Game.getInstance().checkWinning()) {
            System.out.println(name + " wins!");
        } else {
            System.out.println("OK");
        }


    }

    /**
     * Moves the given playing figure to the specified game field.
     *
     * @param playingfigure The playing figure to be moved.
     * @param where         The game field to which the playing figure will be moved.
     * @throws IllegalStateException    If the move cannot be performed due to certain conditions.
     * @throws IllegalArgumentException If the move is not valid.
     */
    public void moveFigure(Playingfigure playingfigure, Gamefield where) {
        if (!canMoveNow) throw new IllegalStateException("ERROR: You can't move now");

        var oldPosition = playingfigure.getGameField();

        //check if figure is part of this player
        if (!playingfigure.getOwner().equals(this)) {
            throw new IllegalStateException("ERROR: This figure is not part of this player");
        }
        if (where.equals(oldPosition)) throw new IllegalArgumentException("ERROR: Move to old Position");

        if (hasMoved && !artemisMove) {
            throw new IllegalArgumentException("ERROR: Already moved");
        }
        if (artemisMove) {
            artemisMove = false;
        } else {
            hasMoved = true;
            canMoveNow = false;
            canBuildNow = true;
        }

        if (!Game.getInstance().isReachable(playingfigure, where)) {
            throw new IllegalStateException("ERROR: This field is not reachable");
        }
        if (hermesTeleport && where.getHeightSquares() != playingfigure.getGameField().getHeightSquares())
            throw new IllegalStateException("ERROR: You can't teleport to a different height");
        if (where.isOccupied() && apolloMove) {
            var otherFigure = where.getPlayingFigure();
            playingfigure.setGameField(where);
            otherFigure.setGameField(oldPosition);
        } else {
            playingfigure.setGameField(where);
        }
        if (Game.getInstance().checkWinning()) {
            System.out.println(name + " wins!");
        } else {
            System.out.println("OK");
        }

        if (athenaBlockedMove && (playingfigure.getGameField().getHeightSquares() > oldPosition.getHeightSquares())) {
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
        if (!canEndTurn) throw new IllegalStateException("ERROR: You can't end your turn yet");
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
        System.out.println(Game.getInstance().getOpponent(this).name + " wins!");
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
     * Sets the flag indicating whether an Atlas build is enabled or not.
     *
     * @param atlasBuild true to enable Atlas build, false otherwise
     */
    public void setAtlasBuild(boolean atlasBuild) {
        this.atlasBuild = atlasBuild;
    }

    /**
     * Returns the array of edu.kit.informatik.Playingfigure objects.
     *
     * @return the array of edu.kit.informatik.Playingfigure objects
     */
    public Playingfigure[] getFigures() {
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
