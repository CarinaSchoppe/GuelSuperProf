import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Game {


    private final ArrayList<Cuboid> cuboidList = new ArrayList<>();
    private static Game instance = null;
    private Player player1;
    private Player player2;
    private final Gamefield[][] playingField;


    private final List<Godcard> godcards = new ArrayList<>(List.of(Godcard.values()));

    private Player currentPlayer;
    private final ArrayList<Dome> domeList = new ArrayList<>();
    private boolean isRunning;

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

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }


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
            throw new IllegalArgumentException("ERROR: You can't move to a field that is not adjecent to you without hermesMove");
        }


        if (figure.getOwner().isHermesTeleport()) return targetField.getHeightSquares() == currentField.getHeightSquares();


        return targetField.getHeightSquares() - 1 <= currentField.getHeightSquares() && !(targetField.getHeightSquares() > currentField.getHeightSquares() && figure.getOwner().isAthenaBlocked());
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

    public Player getOpponent(Player player) {
        return player == player1 ? player2 : player1;
    }

    public void bag() {
        System.out.println("C " + cuboidList.size());
        System.out.println("D " + domeList.size());
    }

    public void cellPrint(int x, int y) {
        try {
            var field = playingField[y][x];
            System.out.println(field.toString());
        } catch (Exception e) {
            throw new IllegalArgumentException("ERROR: Invalid coordinates: " + x + ", " + y);
        }
    }

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


    public boolean quit(String command) {
        if (!command.startsWith("quit"))
            return false;
        if (!command.equalsIgnoreCase("quit"))
            throw new IllegalArgumentException("ERROR: Invalid command");

        return true;
    }


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


    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;


    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
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
