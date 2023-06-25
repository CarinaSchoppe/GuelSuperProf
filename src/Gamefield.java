public class Gamefield extends Gameobject {

    private final Gameobject[] gameobjects = new Gameobject[4];

    public Gamefield(int x, int y) {
        super(x, y);
    }


    public Gameobject[] getGameobjects() {
        return gameobjects;
    }

    public boolean isBuildable() {
        //if the height is not 4 and it has no player on it
        return getHeightSquares() != 4 && !isPlayerOnIt();
    }


    public boolean isOnTop() {
        return getHeightSquares() == 3 && isPlayerOnIt();
    }

    public boolean isRoofable() {
        //if the height is 4 and it has no player on it
        return isBuildable() && getHeightSquares() == 3;
    }

    public int getHeightSquares() {
        int height = 0;
        for (Gameobject gameobject : gameobjects) {
            if (gameobject instanceof Square) {
                height++;
            }
        }
        return height;
    }

    //it is is climable if there is not player on it and its not a pillar
    public boolean isClimeable() {
        return !isPillar() && !isPlayerOnIt();
    }

    private boolean isPlayerOnIt() {
        for (Gameobject gameobject : gameobjects) {
            if (gameobject instanceof Playingfigure) {
                return true;
            }
        }
        return false;
    }

    //it is a pillar when all gameobjects from 0-2 are square and the last is a roof like: square - square - square - roof
    public boolean isPillar() {
        return gameobjects[0] instanceof Square && gameobjects[1] instanceof Square && gameobjects[2] instanceof Square && gameobjects[3] instanceof Roof;
    }

    public boolean isOccupied() {
        for (Gameobject gameobject : gameobjects) {
            if (gameobject instanceof Playingfigure) {
                return true;
            }
        }
        return false;
    }
}
