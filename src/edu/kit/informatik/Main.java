package edu.kit.informatik;

import java.util.Scanner;

public class Main {

    private static final int FOUR = 4;
    private static final int THREE = 3;
    private static final String A_TO_Z = "[a-z]+";
    /**
     * Represents a static variable that holds the current game instance.
     * This variable is used to access and manipulate the game state across different parts of the code.
     * Note: This variable should only be accessed and modified by authorized classes within the application.
     * Example usage:
     * // Access the game instance
     * edu.kit.informatik.Game currentGame = GameInstance.getInstance();
     * // Modify game properties or invoke game methods
     *
     * @see Game
     */
    private static Game game;
    private static final String INVALID_NAME = "ERROR: Invalid name!";
    private static final String INVALID_POSITION = "ERROR: Invalid position!";

    /**
     * Initializes the setup of the game.
     *
     * @param args The command line arguments.
     * @return
     * @throws RuntimeException         If an error occurs during setup.
     * @throws IllegalArgumentException If the command is invalid.
     */
    private static boolean initSetup(String[] args) {
        if (args.length != FOUR) {
            System.out.println("ERROR: Invalid number of arguments!");
            return false;
        }
        game = Game.getInstance();
        var name1 = args[0].split(",")[0];
        var pos1y = Integer.parseInt(args[0].split(",")[1]);
        var pos1x = Integer.parseInt(args[0].split(",")[2]);
        var name2 = args[1].split(",")[0];
        var pos2y = Integer.parseInt(args[1].split(",")[1]);
        var pos2x = Integer.parseInt(args[1].split(",")[2]);
        var figure1 = new Figure(name1, pos1x, pos1y);
        var figure2 = new Figure(name2, pos2x, pos2y);
        var player1 = new Player(figure1, figure2, "P1");
        figure1.setOwner(player1);
        figure2.setOwner(player1);
        var name3 = args[2].split(",")[0];
        var pos3y = Integer.parseInt(args[2].split(",")[1]);
        var pos3x = Integer.parseInt(args[2].split(",")[2]);
        var nameFOUR = args[3].split(",")[0];
        var posFOURy = Integer.parseInt(args[3].split(",")[1]);
        var posFOURx = Integer.parseInt(args[3].split(",")[2]);
        var figure3 = new Figure(name3, pos3x, pos3y);
        var figureFOUR = new Figure(nameFOUR, posFOURx, posFOURy);
        var player2 = new Player(figure3, figureFOUR, "P2");
        figure3.setOwner(player2);
        figureFOUR.setOwner(player2);
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        game.setCurrentPlayer(player1);
        var a = name1.equals(name2);
        var b = name1.equals(name3);
        var c = name1.equals(nameFOUR);
        var d = name2.equals(name3);
        var e = name2.equals(nameFOUR);
        var f = name3.equals(nameFOUR);
        if (a || b || c || d || e || f) {
            System.out.println("ERROR: Names must be unique!");
            return false;
        }
        if (!name1.matches(A_TO_Z) || !name2.matches(A_TO_Z)) {
            System.out.println(INVALID_NAME);
            return false;
        }
        if (!name3.matches(A_TO_Z) || !nameFOUR.matches(A_TO_Z)) {
            System.out.println(INVALID_NAME);
            return false;
        }
        if (pos3y > FOUR || posFOURx < 0 || posFOURx > FOUR || posFOURy < 0 || posFOURy > FOUR) {
            System.out.println(INVALID_POSITION);
            return false;
        }
        if (pos2x < 0 || pos2x > FOUR || pos2y < 0 || pos2y > FOUR) {
            System.out.println(INVALID_POSITION);
            return false;
        }
        return testIt(figure1, figure2, figure3, figureFOUR);

    }

    private static boolean testIt(Figure figure1, Figure figure2, Figure figure3, Figure figureFOUR) {


        if (figure1.getX() == figure2.getX() && figure1.getY() == figure2.getY()) {
            System.out.println("ERROR: Invalid position!");
            return false;
        }

        if (figure3.getX() == figureFOUR.getX() && figure3.getY() == figureFOUR.getY()) {
            System.out.println("ERROR: Invalid position!");
            return false;
        }

        if (figure1.getX() == figure3.getX() && figure1.getY() == figure3.getY()) {
            System.out.println("ERROR: Invalid position!");
            return false;
        }

        if (figure1.getX() == figureFOUR.getX() && figure1.getY() == figureFOUR.getY()) {
            System.out.println("ERROR: Invalid position!");
            return false;
        }

        if (figure2.getX() == figure3.getX() && figure2.getY() == figure3.getY()) {
            System.out.println("ERROR: Invalid position!");
            return false;
        }

        if (figure2.getX() == figureFOUR.getX() && figure2.getY() == figureFOUR.getY()) {
            System.out.println("ERROR: Invalid position!");
            return false;
        }
        return true;
    }

    /**
     * The main method of the program.
     * It handles user input and executes corresponding game actions.
     *
     * @param args The command line arguments.
     * @throws RuntimeException         If an error occurs during setup.
     * @throws IllegalArgumentException If the command is invalid.
     */
    public static void main(String[] args) {
        try {
            if (!initSetup(args)) return;
        } catch (Exception e) {
            System.out.println("ERROR: While setup");
            return;
        }
        var scanner = new Scanner(System.in);
        String line;
        while (!(line = scanner.nextLine()).equals("quit")) {
            if (!game.isRunning())
                break;
            try {
                if (line.startsWith("draw-card")) {
                    if (line.split(" ").length != 2) throw new IllegalArgumentException("ERROR: Invalid command");
                    drawCard(line);
                } else if (line.equals("list-cards")) {
                    game.listCards();
                } else if (line.startsWith("move")) {
                    if (line.split(" ").length != FOUR) throw new IllegalArgumentException("ERROR: Invalid command");
                    var figureName = line.split(" ")[1];
                    var x = Integer.parseInt(line.split(" ")[2]);
                    var y = Integer.parseInt(line.split(" ")[3]);
                    move(figureName, y, x);
                } else if (line.startsWith("build")) {
                    if (line.split(" ").length != FOUR) throw new IllegalArgumentException("ERROR: Invalid command");
                    var type = BuildObject.findBuildObject(line.split(" ")[1].charAt(0));
                    var x = Integer.parseInt(line.split(" ")[2]);
                    var y = Integer.parseInt(line.split(" ")[3]);
                    build(type, y, x);
                } else if (line.equals("end-turn")) {
                    game.getCurrentPlayer().endTurn();
                } else if (line.equals("surrender")) {
                    game.getCurrentPlayer().surrender();
                    break;
                } else if (line.equals("bag")) {
                    game.bag();
                } else if (line.startsWith("cellprint")) {
                    if (line.split(" ").length != 3) throw new IllegalArgumentException("ERROR: Invalid command");
                    var x = Integer.parseInt(line.split(" ")[1]);
                    var y = Integer.parseInt(line.split(" ")[2]);
                    game.cellPrint(y, x);
                } else if (line.equals("print")) {
                    game.print();
                } else {
                    System.out.println("ERROR: Invalid command");
                }
            } catch (Exception e) {
                System.out.println("ERROR: Unexpected error");
            }
        }
    }

    /**
     * The build method is used to build a specific type of object on the playing field at a specified position.
     *
     * @param type The type of the object to be built.
     * @param x    The x coordinate of the position where the object should be built.
     * @param y    The y coordinate of the position where the object should be built.
     */
    private static void build(BuildObject type, int x, int y) {
        if (type == null) {
            throw new IllegalArgumentException("ERROR: Invalid command");

        }
        var field = game.getPlayingField()[y][x];
        game.getCurrentPlayer().build(type, field);
    }

    /**
     * Moves the specified figure to the given coordinates on the playing field.
     *
     * @param figureName The name of the figure to move.
     * @param x          The x-coordinate of the target position.
     * @param y          The y-coordinate of the target position.
     */
    private static void move(String figureName, int x, int y) {
        var field = game.getPlayingField()[y][x];
        var figure = game.getFigure(figureName);
        if (figure == null)
            return;
        game.getCurrentPlayer().moveFigure(figure, field);
    }


    /**
     * Draws a card for the current player based on the specified card symbol.
     *
     * @param line The input line containing the command and card symbol.
     */
    private static void drawCard(String line) {
        var cardSymbol = line.split(" ")[1];
        Game.getInstance().getCurrentPlayer().drawGodCard(Godcard.findGodcard(cardSymbol));
    }
}
