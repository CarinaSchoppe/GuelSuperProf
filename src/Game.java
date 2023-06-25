public class Game {


    private static Game instance = null;
    private final Player player1;
    private final Player player2;
    private final Gamefield[][] playingField = new Gamefield[5][5];

    private Player currentPlayer;

    private Game() {
        instance = this;
        player1 = new Player(new Playingfigure("blue", 0, 0), new Playingfigure("green", 0, 0));
        player2 = new Player(new Playingfigure("red", 0, 0), new Playingfigure("yellow", 0, 0));
        currentPlayer = player1;
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public boolean isBuildable(Playingfigure figure, int x, int y) {
        //check if the field is adjectend
        return isClose(figure.getPlayingField(), getPlayingField()[y][x]) && playingField[y][x].isBuildable();
    }

    public boolean isClose(Gamefield currentField, Gamefield targetField) {
        var adjecentFields = adjecentFields(currentField);
        for (Gamefield[] gamefields : adjecentFields) {
            if (gamefields == null) continue;
            for (Gamefield gamefield : gamefields) {
                if (gamefield == targetField) {
                    return true;
                }
            }
        }
        return false;
    }

    //its reachable if its not a pillar and if it is adjectent to the currentField
    public boolean isReachable(Playingfigure figure, int x, int y) {
        var currentField = playingField[figure.getY()][figure.getX()];
        var targetField = playingField[y][x];
        if (targetField.isPillar()) {
            return false;
        }
        if (currentField == targetField) {
            return false;
        }

        if (targetField.isOccupied()) return false;

        if (!isClose(currentField, targetField)) return false;

        return targetField.getHeightSquares() <= currentField.getHeightSquares() + 1;
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
                if (figure.getPlayingField().isOnTop()) {
                    return true;
                }
            }
        }
        var opponent = getOpponent(currentPlayer);
        //if that opponent cant make any more build moves
        return !canBuild(opponent);
    }

    private boolean canBuild(Player player) {
        for (var figure : player.getFigures()) {
            var fields = adjecentFields(figure.getPlayingField());
            for (var fieldRow : fields) {
                for (var field : fieldRow) {
                    if (field != null && field.isBuildable()) {
                        return true;
                    }
                }
            }
        }


        return false;
    }


    public boolean isPillar(int x, int y) {
        return playingField[y][x].isPillar();

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


    public Gamefield[][] getPlayingField() {
        return playingField;
    }
}
