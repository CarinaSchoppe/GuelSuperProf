import java.util.ArrayList;
import java.util.List;

public class Game {


    private static Game instance = null;
    private final Player player1;
    private final Player player2;
    private final Gamefield[][] playingField = new Gamefield[5][5];

    private final List<Godcard> godcards = new ArrayList<>(List.of(Godcard.values()));

    private Player currentPlayer;

    private Game() {
        instance = this;
        var figure1 = new Playingfigure("blue", 0, 0);
        var figure2 = new Playingfigure("green", 0, 0);
        player1 = new Player(figure1, figure2);
        figure1.setOwner(player1);
        figure2.setOwner(player1);
        var figure3 = new Playingfigure("red", 0, 0);
        var figure4 = new Playingfigure("yellow", 0, 0);
        player2 = new Player(figure3, figure4);
        figure3.setOwner(player2);
        figure4.setOwner(player2);
        currentPlayer = player1;
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }


    public boolean isClose(Gamefield currentField, Gamefield targetField) {
        var adjecentFields = adjecentFields(currentField);
        for (Gamefield[] gamefields : adjecentFields) {
            for (Gamefield gamefield : gamefields) {
                if (gamefield == targetField) {
                    return true;
                }
            }
        }
        return false;
    }

    //its reachable if its not a pillar and if it is adjectent to the currentField
    public boolean isReachable(Playingfigure figure, Gamefield where) {
        var currentField = playingField[figure.getY()][figure.getX()];
        var targetField = where;
        if (targetField.isPillar()) {
            return false;
        }
        if (currentField == targetField) {
            return false;
        }

        if (targetField.isOccupied() && !figure.getOwner().isApolloMove()) return false;

        if (!isClose(currentField, targetField)) return false;


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
    public Gamefield[][] adjecentFields(Gamefield field) {
        var x = field.getX();
        var y = field.getY();
        Gamefield[][] adjecentFields = new Gamefield[3][3];
        if (playingField.length - 1 >= y - 1) {
            if (playingField[y - 1].length - 1 >= x - 1) {
                adjecentFields[0][0] = playingField[y - 1][x - 1];
            }
            if (playingField[y - 1].length - 1 >= x) {
                adjecentFields[0][1] = playingField[y - 1][x];
            }
            if (playingField[y - 1].length - 1 >= x + 1) {
                adjecentFields[0][2] = playingField[y - 1][x + 1];
            }
        }

        if (playingField.length - 1 >= y) {
            if (playingField[y].length - 1 >= x - 1) {
                adjecentFields[1][0] = playingField[y][x - 1];
            }
            if (playingField[y].length - 1 >= x) {
                adjecentFields[1][1] = playingField[y][x];
            }
            if (playingField[y].length - 1 >= x + 1) {
                adjecentFields[1][2] = playingField[y][x + 1];
            }
        }

        if (playingField.length - 1 >= y + 1) {
            if (playingField[y + 1].length - 1 >= x - 1) {
                adjecentFields[2][0] = playingField[y + 1][x - 1];
            }
            if (playingField[y + 1].length - 1 >= x) {
                adjecentFields[2][1] = playingField[y + 1][x];
            }
            if (playingField[y + 1].length - 1 >= x + 1) {
                adjecentFields[2][2] = playingField[y + 1][x + 1];
            }
        }
        return adjecentFields;

    }

    public Player getOpponent(Player player) {
        return player == player1 ? player2 : player1;
    }

    //game is over when a player has reached a tower
    public boolean checkWinning() {
        for (var player : new Player[]{player1, player2}) {
            for (var figure : player.getFigures()) {
                if (figure.getGameField().isOnTop()) {
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
            var adjecent = adjecentFields(figure.getGameField());
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

    //if a player is


    public List<Godcard> getGodcards() {
        return godcards;
    }

    public Gamefield[][] getPlayingField() {
        return playingField;
    }
}
