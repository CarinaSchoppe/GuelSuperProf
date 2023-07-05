import java.util.Scanner;

public class Main {

    private static Game game;

    private static void initSetup(String[] args) {
        game = Game.getInstance();
        var name1 = args[0].split(",")[0];
        var pos1x = Integer.parseInt(args[0].split(",")[1]);
        var pos1y = Integer.parseInt(args[0].split(",")[2]);
        var name2 = args[1].split(",")[0];
        var pos2x = Integer.parseInt(args[1].split(",")[1]);
        var pos2y = Integer.parseInt(args[1].split(",")[2]);

        var figure1 = new Playingfigure(name1, pos1x, pos1y);
        var figure2 = new Playingfigure(name2, pos2x, pos2y);
        var player1 = new Player(figure1, figure2, "P1");
        figure1.setOwner(player1);
        figure2.setOwner(player1);

        var name3 = args[2].split(",")[0];
        var pos3x = Integer.parseInt(args[2].split(",")[1]);
        var pos3y = Integer.parseInt(args[2].split(",")[2]);
        var name4 = args[3].split(",")[0];
        var pos4x = Integer.parseInt(args[3].split(",")[1]);
        var pos4y = Integer.parseInt(args[3].split(",")[2]);

        var figure3 = new Playingfigure(name3, pos3x, pos3y);
        var figure4 = new Playingfigure(name4, pos4x, pos4y);
        var player2 = new Player(figure3, figure4, "P2");
        figure3.setOwner(player2);
        figure4.setOwner(player2);

        game.setCurrentPlayer(player1);
    }

    public static void main(String[] args) {
        try {
            initSetup(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        var scanner = new Scanner(System.in);
        String line;
        while (!(line = scanner.nextLine()).equalsIgnoreCase("quit")) {
            if (line.startsWith("draw-card")) {
                drawCard(line);
            } else if (line.startsWith("list-cards")) {
                game.listCards();
            } else if (line.startsWith("move")) {
                var figureName = line.split(" ")[1];
                var x = Integer.parseInt(line.split(" ")[2]);
                var y = Integer.parseInt(line.split(" ")[3]);
                move(figureName, x, y);
            } else if (line.startsWith("build")) {
                var type = BuildObject.findBuildObject(line.split(" ")[1].charAt(0));
                var x = Integer.parseInt(line.split(" ")[2]);
                var y = Integer.parseInt(line.split(" ")[3]);
                build(type, x, y);
            } else if (line.startsWith("end-turn")) {
                game.getCurrentPlayer().endTurn();
            } else if (line.startsWith("surrender")) {
                game.getCurrentPlayer().surrender();
            } else if (line.startsWith("bag")) {
                game.bag();
            } else if (line.startsWith("cellprint")) {
                var x = Integer.parseInt(line.split(" ")[1]);
                var y = Integer.parseInt(line.split(" ")[2]);
                game.cellPrint(x, y);
            } else if (line.startsWith("print")) {
                game.print();
            } else {
                throw new IllegalArgumentException("ERROR: Invalid command");
            }
        }
    }

    private static void build(BuildObject type, int x, int y) {
        var field = game.getPlayingField()[y][x];
        game.getCurrentPlayer().build(type, field);
    }

    private static void move(String figureName, int x, int y) {
        var field = game.getPlayingField()[y][x];
        var figure = game.getFigure(figureName);
        game.getCurrentPlayer().moveFigure(figure, field);
    }


    private static void drawCard(String line) {
        var cardSymbol = line.split(" ")[1];
        Game.getInstance().getCurrentPlayer().drawGodCard(Godcard.findGodcard(cardSymbol));
    }
}
