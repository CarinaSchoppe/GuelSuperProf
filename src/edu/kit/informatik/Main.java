package edu.kit.informatik;

import java.util.Scanner;

public class Main {

    /**
     * Represents a static variable that holds the current game instance.
     * This variable is used to access and manipulate the game state across different parts of the code.
     * <p>
     * Note: This variable should only be accessed and modified by authorized classes within the application.
     * <p>
     * Example usage:
     * <p>
     * // Access the game instance
     * edu.kit.informatik.Game currentGame = GameInstance.getInstance();
     * <p>
     * // Modify game properties or invoke game methods
     * currentGame.start();
     * currentGame.pause();
     * currentGame.resume();
     * currentGame.end();
     *
     * @see Game
     */
    private static Game game;

    /**
     * Initializes the setup of the game.
     *
     * @param args The command line arguments.
     * @throws RuntimeException If an error occurs during setup.
     * @throws IllegalArgumentException If the command is invalid.
     */
    private static void initSetup(String[] args) {
        game = Game.getInstance();
        var name1 = args[0].split(",")[0];
        var pos1y = Integer.parseInt(args[0].split(",")[1]);
        var pos1x = Integer.parseInt(args[0].split(",")[2]);
        var name2 = args[1].split(",")[0];
        var pos2y = Integer.parseInt(args[1].split(",")[1]);
        var pos2x = Integer.parseInt(args[1].split(",")[2]);

        var figure1 = new Playingfigure(name1, pos1x, pos1y);
        var figure2 = new Playingfigure(name2, pos2x, pos2y);
        var player1 = new Player(figure1, figure2, "P1");
        figure1.setOwner(player1);
        figure2.setOwner(player1);

        var name3 = args[2].split(",")[0];
        var pos3y = Integer.parseInt(args[2].split(",")[1]);
        var pos3x = Integer.parseInt(args[2].split(",")[2]);
        var name4 = args[3].split(",")[0];
        var pos4y = Integer.parseInt(args[3].split(",")[1]);
        var pos4x = Integer.parseInt(args[3].split(",")[2]);

        var figure3 = new Playingfigure(name3, pos3x, pos3y);
        var figure4 = new Playingfigure(name4, pos4x, pos4y);
        var player2 = new Player(figure3, figure4, "P2");
        figure3.setOwner(player2);
        figure4.setOwner(player2);

        game.setPlayer1(player1);
        game.setPlayer2(player2);
        game.setCurrentPlayer(player1);
        //check if one name is equal
        if (name1.equals(name2) || name1.equals(name3) || name1.equals(name4) || name2.equals(name3) || name2.equals(name4) || name3.equals(name4)) {
            throw new RuntimeException("ERROR: Names must be unique!");
        }

    }

    /**
     * The main method of the program.
     * It handles user input and executes corresponding game actions.
     *
     * @param args The command line arguments.
     * @throws RuntimeException If an error occurs during setup.
     * @throws IllegalArgumentException If the command is invalid.
     */
    public static void main(String[] args) {
        try {
            initSetup(args);
        } catch (Exception e) {
            throw new RuntimeException("ERROR: While setup");
        }
        var scanner = new Scanner(System.in);
        String line;
        while (!(line = scanner.nextLine()).equalsIgnoreCase("quit") && game.isRunning()) {
            if (line.startsWith("draw-card")) {
                drawCard(line);
            } else if (line.startsWith("list-cards")) {
                game.listCards();
            } else if (line.startsWith("move")) {
                var figureName = line.split(" ")[1];
                var x = Integer.parseInt(line.split(" ")[2]);
                var y = Integer.parseInt(line.split(" ")[3]);
                move(figureName, y, x);
            } else if (line.startsWith("build")) {
                var type = BuildObject.findBuildObject(line.split(" ")[1].charAt(0));
                var x = Integer.parseInt(line.split(" ")[2]);
                var y = Integer.parseInt(line.split(" ")[3]);
                build(type, y, x);
            } else if (line.startsWith("end-turn")) {
                game.getCurrentPlayer().endTurn();
            } else if (line.startsWith("surrender")) {
                game.getCurrentPlayer().surrender();
            } else if (line.startsWith("bag")) {
                game.bag();
            } else if (line.startsWith("cellprint")) {
                var x = Integer.parseInt(line.split(" ")[1]);
                var y = Integer.parseInt(line.split(" ")[2]);
                game.cellPrint(y, x);
            } else if (line.startsWith("print")) {
                game.print();
            } else {
                throw new IllegalArgumentException("ERROR: Invalid command");
            }
        }
    }

    /**
     * The build method is used to build a specific type of object on the playing field at a specified position.
     *
     * @param type The type of the object to be built.
     * @param x The x coordinate of the position where the object should be built.
     * @param y The y coordinate of the position where the object should be built.
     */
    private static void build(BuildObject type, int x, int y) {
        var field = game.getPlayingField()[y][x];
        game.getCurrentPlayer().build(type, field);
    }

    /**
     * Moves the specified figure to the given coordinates on the playing field.
     *
     * @param figureName The name of the figure to move.
     * @param x The x-coordinate of the target position.
     * @param y The y-coordinate of the target position.
     */
    private static void move(String figureName, int x, int y) {
        var field = game.getPlayingField()[y][x];
        var figure = game.getFigure(figureName);
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
