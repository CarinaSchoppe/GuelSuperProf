package edu.kit.informatik;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Game {


    /**
     * Represents the current instance of the edu.kit.informatik.Game class.
     * <p>
     * This variable is used to keep track of the single instance of the Game class throughout the application.
     * It is declared as private and static to ensure that only one instance is created and to allow access
     * to the instance from anywhere within the class.
     * <p>
     * The instance is initially set to null and should be initialized by calling the getInstance() method
     * of the edu.kit.informatik.Game class. If the instance is null, a new instance of the Game class is created;
     * otherwise,
     * the existing instance is returned.
     * <p>
     * Example usage:
     * edu.kit.informatik.Game gameInstance = edu.kit.informatik.Game.getInstance();
     *
     * @see Game#getInstance()
     */
    private static Game instance = null;
    /**
     * A private final instance variable representing a list of edu.kit.informatik.Cuboid objects.
     *
     * <p>
     * This variable is initialized as an empty ArrayList of edu.kit.informatik.Cuboid objects.
     * It is declared as private final to ensure that the reference to the ArrayList
     * cannot be changed and the variable cannot be assigned a new ArrayList instance.
     * </p>
     *
     * @see Cuboid
     * @since Insert the version number or date when the variable was first introduced
     */
    private final ArrayList<Cuboid> cuboidList = new ArrayList<>();
    /**
     * The playingField variable represents the game field,
     * which stores a two-dimensional array of Gamefield objects.
     * It is a private and final variable, indicating that it cannot be modified after initialization,
     * and can only be accessed within the class.
     * <p>
     * The playingField variable is used to store and manipulate the game state,
     * allowing the game logic to be applied on it.
     * It represents the layout and current state of the game board.
     * <p>
     * The two-dimensional array consists of rows and columns,
     * where each element represents a specific position on the game board.
     * Each element is of type edu.kit.informatik.Gamefield,
     * which encapsulates information about the state of that particular position on the game board.
     * <p>
     * Note that edu.kit.informatik.Gamefield is not defined in this documentation,
     * and should be replaced with the appropriate class name in your implementation.
     * <p>
     * Examples of accessing and manipulating the playingField variable:
     * - Displaying the state of a specific position on the game board:
     * edu.kit.informatik.Gamefield field = playingField[row][column];
     * System.out.println(field.getState());
     * <p>
     * - Updating the state of a specific position on the game board:
     * <p>
     * - Getting the dimensions of the playingField:
     * int numRows = playingField.length;
     * int numColumns = playingField[0].length;
     */
    private final Gamefield[][] playingField;
    /**
     * The {@code godcards} variable is a private final List that represents the collection
     * of available God cards.
     *
     * <p>
     * Each element in the list is an instance of the {@link Godcard} enumeration, which
     * represents a specific God card.
     *
     * <p>
     * This list is initialized with all the values from the {@link Godcard} enumeration using
     * the {@link List#of} method, which creates an immutable list with the given elements.
     *
     * <p>
     * This list is then stored in an {@link ArrayList}, which allows us to modify its contents
     * if needed. However, the variable itself is declared as final, which means it cannot be
     * reassigned to a different list.
     *
     * @see Godcard
     * @see List
     * @see ArrayList
     */
    private final List<Godcard> godcards = new ArrayList<>(List.of(Godcard.values()));
    /**
     * The domeList variable represents a private, final ArrayList of type edu.kit.informatik.Dome.
     * It is used to store a collection of edu.kit.informatik.Dome objects.
     * <p>
     * edu.kit.informatik.Dome objects stored in the domeList can be accessed and modified only within the scope
     * of the class where the variable is declared, due to its private access modifier.
     * <p>
     * The domeList variable is declared as final, indicating that its reference cannot be
     * changed once initialized. However, the contents of the ArrayList can still be modified.
     * <p>
     * edu.kit.informatik.Dome objects can be added to the domeList using the add() method provided by the
     * ArrayList class. Similarly, they can be removed using the remove() method.
     * <p>
     * Example usage:
     * <p>
     * // Create a new edu.kit.informatik.Dome object
     * edu.kit.informatik.Dome dome1 = new edu.kit.informatik.Dome();
     * <p>
     * // Add the edu.kit.informatik.Dome object to the domeList
     * domeList.add(dome1);
     * <p>
     * // Remove the edu.kit.informatik.Dome object from the domeList
     * domeList.remove(dome1);
     * <p>
     * Note that the documentation does not provide implementation details, but focuses on the
     * purpose, behavior, and usage of the variable.
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
     * This variable stores information about the second player in the program.
     * It is used to track and manage the state, actions, and attributes
     * of the player within the software.
     * <p>
     * This variable is declared as private in order to restrict its
     * accessibility and ensure proper encapsulation. It can only be accessed
     * within the class in which it is declared.
     * <p>
     * To manipulate the player2 variable or access its data, appropriate
     * getter and setter methods should be used.
     * <p>
     * Please refer to the documentation of the edu.kit.informatik.Player class to understand
     * the structure and attributes of the edu.kit.informatik.Player object, as player2 is an
     * instance of the edu.kit.informatik.Player class.
     */
    private Player player2;
    /**
     * The current player in the game.
     * <p>
     * This variable represents the player who is currently playing the game.
     * It holds the instance of the edu.kit.informatik.Player class representing the current player.
     * <p>
     * Please note that this variable is private and can only be accessed within
     * the scope of the class it belongs to.
     */
    private Player currentPlayer;
    /**
     * This variable represents the state of a program or system indicating whether it is currently running or not.
     */
    private boolean isRunning;

    /**
     * Initializes a new instance of the game.
     * <p>
     * This method sets up the playing field, cuboidList, domeList, and sets the isRunning flag to true.
     * <p>
     * The playing field is a 5x5 grid represented by a 2D array of Gamefield objects.
     * Each Gamefield object represents
     * a cell on the grid and is initialized with its x and y coordinates.
     * <p>
     * The cuboidList is a list of edu.kit.informatik.Cuboid objects.
     * The method creates 54 instances of Cuboid and adds them to the list.
     * <p>
     * The domeList is a list of edu.kit.informatik.Dome objects.
     * The method creates 18 instances of Dome and adds them to the list.
     * <p>
     * The flag isRunning is set to true to indicate that the game is currently running.
     * <p>
     * This method is private to ensure that the game can only be initialized internally.
     */
    private Game() {
        instance = this;
        playingField = new Gamefield[5][5];
        for (var x = 0; x < 5; x++) {
            for (var y = 0; y < 5; y++) {
                playingField[y][x] = new Gamefield(x, y);
            }
        }


        for (var i = 0; i < 54; i++) {
            cuboidList.add(new Cuboid(0, 0));
        }

        for (var i = 0; i < 18; i++) {
            domeList.add(new Dome(0, 0));
        }
        isRunning = true;
    }

    /**
     * Gets the instance of the edu.kit.informatik.Game class.
     * <p>
     * This method is used to create an instance of the edu.kit.informatik.Game class if it does not already exist,
     * and return the instance to the caller.
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
     * @throws IllegalArgumentException If the target field is a pillar, or if the target field is the same as the current field,
     *                                  or if the target field is occupied without ApolloMove, or if the target field is not adjacent
     *                                  to the current field without HermesMove.
     */
    //its reachable if its not a pillar and if it is adjectent to the currentField
    public boolean isReachable(Playingfigure figure, Gamefield where) {
        var currentField = playingField[figure.getY()][figure.getX()];
        var targetField = where;
        if (targetField.isPillar()) {
            throw new IllegalArgumentException("ERROR: You can't move to a pillar");
        }
        if (currentField == targetField) {
            throw new IllegalArgumentException("ERROR: You can't move to the same field");
        }

        if (targetField.isOccupied() && !figure.getOwner().isApolloMove()) {
            throw new IllegalArgumentException("ERROR: You can't move to an occupied field without apolloMove");
        }

        if (!isClose(currentField, targetField)) {
            var msg = "ERROR: You can't move to a field that is not adjecent to you without hermesMove";
            throw new IllegalArgumentException(msg);
        }


        if (figure.getOwner().isHermesTeleport()) {
            return targetField.getHeightSquares() == currentField.getHeightSquares();
        }
        var a = targetField.getHeightSquares() - 1 <= currentField.getHeightSquares();
        var b = targetField.getHeightSquares() > currentField.getHeightSquares();
        var c = figure.getOwner().isAthenaBlocked();
        return a && !(b && c);
    }

    /*
        Field:
        
           0  1  2  3  4
        
       0   0, 1, 2, 3, 4
       1   0, 1, 2, 3, 4
       2   0, 1, 2, 3, 4
       3   0, 1, 2, 3, 4
       4   0, 1, 2, 3, 4
         */

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
        if (x + 1 < 5 && y - 1 >= 0) {
            adjacentFields[0][2] = playingField[y - 1][x + 1];
        }
        //middle left
        if (x - 1 >= 0) {
            adjacentFields[1][0] = playingField[y][x - 1];
        }
        //middle right
        if (x + 1 < 5) {
            adjacentFields[1][2] = playingField[y][x + 1];
        }
        //bottom left
        if (x - 1 >= 0 && y + 1 < 5) {
            adjacentFields[2][0] = playingField[y + 1][x - 1];
        }
        //bottom middle
        if (y + 1 < 5) {
            adjacentFields[2][1] = playingField[y + 1][x];
        }
        //bottom right
        if (x + 1 < 5 && y + 1 < 5) {
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
     * <p>
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
            throw new IllegalArgumentException("ERROR: Invalid coordinates: " + x + ", " + y);
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
        if (!command.equalsIgnoreCase("quit"))
            throw new IllegalArgumentException("ERROR: Invalid command");

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


        //print them like this: Name1,Name2,Name3,Name4
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


    public Playingfigure getFigure(String figureName) {
        for (var player : new Player[]{player1, player2}) {
            for (var figure : player.getFigures()) {
                if (figure.getName().equalsIgnoreCase(figureName)) {
                    return figure;
                }
            }
        }
        throw new IllegalArgumentException("ERROR: Invalid figure name");
    }
}
