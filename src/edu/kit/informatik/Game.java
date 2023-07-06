package edu.kit.informatik;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Game {
    /**
     * The {@code instance} variable is a private static variable that represents the singleton instance
     *
     * @see Game#getInstance()
     */
    private static Game instance = null;
    /**
     * A private final instance variable representing a list of edu.kit.informatik.Cuboid objects.
     *
     * @see Cuboid
     * @since Insert the version number or date when the variable was first introduced
     */
    private final ArrayList<Cuboid> cuboidList = new ArrayList<>();
    /**
     * The playingField variable represents the game field,
     */
    private final Gamefield[][] playingField;
    /**
     * @see Godcard
     * @see List
     * @see ArrayList
     */
    private final List<Godcard> godcards = new ArrayList<>(List.of(Godcard.values()));
    /**
     * The domeList variable represents a private, final ArrayList of type edu.kit.informatik.Dome.
     *
     * @see Dome
     */
    private final ArrayList<Dome> domeList = new ArrayList<>();
    /**
     * Represents the player 1 object in the game.
     */
    private Player player1;
    /**
     * Represents the player2 variable in the context of the software.
     */
    private Player player2;
    private static final int FIFTY_FOUR = 54;
    /**
     * This variable represents the state of a program or system indicating whether it is currently running or not.
     */
    private boolean isRunning;
    private static final int EIGHTEEN = 18;
    private static final int SIX = 6;
    private static final int FIVE = 5;
    private static final int FOUR = 4;
    private static final int THREE = 3;
    /**
     *
     */
    private Player currentPlayer;

    /**
     * Initializes a new instance of the game.
     * This method is private to ensure that the game can only be initialized internally.
     */
    private Game() {
        instance = this;
        playingField = new Gamefield[FIVE][FIVE];
        for (var x = 0; x < FIVE; x++) {
            for (var y = 0; y < FIVE; y++) {
                playingField[y][x] = new Gamefield(x, y);
            }
        }


        for (var i = 0; i < FIFTY_FOUR; i++) {
            cuboidList.add(new Cuboid(0, 0));
        }

        for (var i = 0; i < EIGHTEEN; i++) {
            domeList.add(new Dome(0, 0));
        }
        isRunning = true;
    }

    /**
     * Gets the instance of the edu.kit.informatik.Game class.
     *
     * @return The instance of the edu.kit.informatik.Game class.
     */
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    /**
     * Checks if the target field is close to the current field.
     *
     * @param currentField The current game field.
     * @param targetField  The target game field.
     * @return True if the target field is adjacent to the current field,
     * or if the current player has Hermes Teleport ability,
     * false otherwise.
     */
    public boolean isClose(Gamefield currentField, Gamefield targetField) {
        var adjecentFields = adjacentFields(currentField);
        for (var gamefields : adjecentFields) {
            for (var gamefield : gamefields) {
                if (gamefield == targetField) {
                    return true;
                }
            }
        }

        return currentPlayer.isHermesTeleport();
    }

    /**
     * Determines whether a given game field is reachable from the current playing figure.
     *
     * @param figure The playing figure from which the reachability is checked.
     * @param where  The target game field to check for reachability.
     * @return {@code true} if the target field is reachable, {@code false} otherwise.
     * to the current field without HermesMove.
     */
    //its reachable if its not a pillar and if it is adjectent to the currentField
    public boolean isReachable(Figure figure, Gamefield where) {
        var currentField = playingField[figure.getY()][figure.getX()];
        var targetField = where;
        if (targetField.isPillar()) {
            return false;
        }
        if (currentField == targetField) {
            return false;
        }

        if (targetField.isOccupied() && !figure.getOwner().isApolloMove()) {
            return false;
        }

        if (!isClose(currentField, targetField)) {
            return false;
        }


        if (figure.getOwner().isHermesTeleport()) {
            return targetField.getHeightSquares() == currentField.getHeightSquares();
        }
        var a = targetField.getHeightSquares() - 1 <= currentField.getHeightSquares();
        var b = targetField.getHeightSquares() > currentField.getHeightSquares();
        var c = figure.getOwner().isAthenaBlocked();
        return a && !(b && c);
    }

    /**
     * Returns a 2D array of the adjacent fields of the given {@link Gamefield}.
     *
     * @param field the field whose adjacent fields are to be retrieved
     * @return a 2D array of the adjacent fields of the given field. The array contains a total of 9 elements
     * arranged in a 3x3 grid, where the given field occupies the center position.
     * If a field does not exist at any adjacent position, the corresponding element of the array will be null.
     */
    //returns a 2d array of the adjecent fields of the given coordiantes
    public Gamefield[][] adjacentFields(Gamefield field) {
        var x = field.getX();
        var y = field.getY();
        Gamefield[][] adjacentFields = new Gamefield[3][3];

        //top left
        //check if the field exists
        if (x - 1 >= 0 && y - 1 >= 0) {
            adjacentFields[0][0] = playingField[y - 1][x - 1];
        }
        //top middle
        if (y - 1 >= 0) {
            adjacentFields[0][1] = playingField[y - 1][x];
        }
        //top right
        if (x + 1 < FIVE && y - 1 >= 0) {
            adjacentFields[0][2] = playingField[y - 1][x + 1];
        }
        //middle left
        if (x - 1 >= 0) {
            adjacentFields[1][0] = playingField[y][x - 1];
        }
        //middle right
        if (x + 1 < FIVE) {
            adjacentFields[1][2] = playingField[y][x + 1];
        }
        //bottom left
        if (x - 1 >= 0 && y + 1 < FIVE) {
            adjacentFields[2][0] = playingField[y + 1][x - 1];
        }
        //bottom middle
        if (y + 1 < FIVE) {
            adjacentFields[2][1] = playingField[y + 1][x];
        }
        //bottom right
        if (x + 1 < FIVE && y + 1 < FIVE) {
            adjacentFields[2][2] = playingField[y + 1][x + 1];
        }
        adjacentFields[1][1] = field;


        return adjacentFields;

    }

    /**
     * Gets the opponent of the specified player.
     *
     * @param player the player for whom to find the opponent
     * @return the opponent of the specified player
     */
    public Player getOpponent(Player player) {
        return player == player1 ? player2 : player1;
    }

    /**
     * Prints the count of cuboids and count of domes in the bag.
     * The count of cuboids and the count of domes are printed to the console.
     */
    public void bag() {
        System.out.println("C " + cuboidList.size());
        System.out.println("D " + domeList.size());
    }

    /**
     * Prints the value of a cell at the given coordinates.
     *
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     * @throws IllegalArgumentException if the coordinates are invalid.
     */
    public void cellPrint(int x, int y) {
        try {
            var field = playingField[y][x];
            System.out.println(field.toString());
        } catch (Exception e) {
            System.out.println("ERROR: Invalid coordinates: " + x + ", " + y);
        }
    }

    /**
     * Prints the playing field.
     */
    public void print() {
        for (var row : playingField) {
            var rowString = "";
            for (var field : row) {
                var lastCharacter = field.getTopCharacter();
                rowString += lastCharacter + " ";
            }
            rowString = rowString.substring(0, rowString.length() - 1);
            System.out.println(rowString);
        }
    }


    /**
     * Checks if the given command is "quit".
     *
     * @param command the command to check
     * @return true if the command is "quit", otherwise false.
     * @throws IllegalArgumentException if the command is not exactly "quit"
     */
    public boolean quit(String command) {
        if (!command.startsWith("quit"))
            return false;
        if (!command.equalsIgnoreCase("quit")) {

            System.out.println("ERROR: Invalid command");
            return false;
        }
        return true;
    }


    /**
     * Checks if a player has won the game.
     *
     * @return true if a player has reached the top of a tower, false otherwise
     */
    //game is over when a player has reached a tower
    public boolean checkWinning() {
        for (var player : new Player[]{player1, player2}) {
            for (var figure : player.getFigures()) {
                if (figure.getGameField().isOnTop()) {
                    isRunning = false;
                    return true;
                }
            }
        }
        var opponent = getOpponent(currentPlayer);
        //if that opponent cant make any more build moves
        return !canBuildAnything(opponent);
    }

    /**
     * Checks if the given player can build anything on their adjacent game fields.
     *
     * @param player The player object to check.
     * @return Returns true if the player can build anything, false otherwise.
     */
    private boolean canBuildAnything(Player player) {
        for (var figure : player.getFigures()) {
            var adjecent = adjacentFields(figure.getGameField());
            for (var gamefields : adjecent) {
                for (var gamefield : gamefields) {
                    if (gamefield == null) continue;
                    if (gamefield.isBuildable()) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    /**
     * Retrieves the current player.
     *
     * @return The current player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Set the current player of the game.
     *
     * @param currentPlayer the player to be set as the current player
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;


    }

    /**
     * Retrieves the player1 object.
     *
     * @return the player1 object.
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Sets the value of player1.
     *
     * @param player1 The edu.kit.informatik.Player object to set as player1.
     */
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    /**
     * Retrieves the edu.kit.informatik.Player 2 object.
     *
     * @return the edu.kit.informatik.Player 2 object.
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * Sets the player 2 for the game.
     *
     * @param player2 the player 2 object
     */
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void listCards() {

        //sort godcards lexiographically by their name
        godcards.sort(Comparator.comparing(Godcard::getName));


        //print them like this: Name1,Name2,Name3,NameFOUR
        String stringBuilder = "";
        for (Godcard godcard : godcards) {
            stringBuilder += godcard.getName() + ",";
        }
        stringBuilder = stringBuilder.substring(0, stringBuilder.length() - 1);
        System.out.println(stringBuilder);
    }

    public List<Godcard> getGodcards() {
        return godcards;
    }


    public ArrayList<Cuboid> getCuboidList() {
        return cuboidList;
    }

    public ArrayList<Dome> getDomeList() {
        return domeList;
    }

    public Gamefield[][] getPlayingField() {
        return playingField;
    }


    public Figure getFigure(String figureName) {
        for (var player : new Player[]{player1, player2}) {
            for (var figure : player.getFigures()) {
                if (figure.getName().equalsIgnoreCase(figureName)) {
                    return figure;
                }
            }
        }
        System.out.println("ERROR: Invalid figure name");
        return null;
    }
}
