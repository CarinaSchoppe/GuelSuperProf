package edu.kit.informatik;

/**
 * @author uyjam
 * @version 1.0
 */

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
        return !isPlayerOnIt() && !isPillar();
    }


    public boolean isOnTop() {
        return getHeightSquares() == 3 && isPlayerOnIt();
    }

    public boolean isDomeable() {
        //if the height is 4 and it has no player on it
        return isBuildable() && getHeightSquares() == 3;
    }

    public int getHeightSquares() {
        int height = 0;
        for (Gameobject gameobject : gameobjects) {
            if (gameobject instanceof Cuboid) {
                height++;
            }
        }
        return height;
    }

    //it is is climable if there is not player on it and its not a pillar
    public boolean isClimeable(boolean apolloMove) {
        if (apolloMove) {
            return !isPillar();
        }
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

    public boolean isPillar() {
        //if last gameobject is a pillar
        for (var object : gameobjects) {
            if (object instanceof Dome) {
                return true;
            }
        }
        return false;
    }

    public boolean isOccupied() {
        for (Gameobject gameobject : gameobjects) {
            if (gameobject instanceof Playingfigure) {
                return true;
            }
        }
        return false;
    }

    public boolean isAdjecent(Gamefield where) {
        var adj = Game.getInstance().adjacentFields(this);
        for (Gamefield[] gamefields : adj) {
            for (Gamefield gamefield : gamefields) {
                if (gamefield == where) {
                    return true;
                }
            }
        }
        return false;
    }

    public Playingfigure getPlayingFigure() {
        for (Gameobject gameobject : gameobjects) {
            if (gameobject instanceof Playingfigure) {
                return (Playingfigure) gameobject;
            }
        }
        return null;
    }

    public void setFigure(Playingfigure figure) {
        for (int i = 0; i < gameobjects.length; i++) {
            if (gameobjects[i] == null || gameobjects[i] instanceof Playingfigure) {
                gameobjects[i] = figure;
                return;
            }
        }
    }

    @Override
    public String toString() {
        if (gameobjects[0] == null)
            return "Empty";
        var string = "";
        for (var element : gameobjects) {
            if (element instanceof Cuboid cube) {
                string += cube + ",";
            } else if (element instanceof Dome dome) {
                string += dome + ",";
            } else if (element instanceof Playingfigure figure) {
                string += figure.getName() + ",";
            }

        }
        string = string.substring(0, string.length() - 1);
        return string;
    }

    public Character getTopCharacter() {
        var topGameObject = getTopGameObject();
        if (topGameObject == null)
            return '.';

        if (topGameObject instanceof Cuboid) {
            return 'C';
        } else if (topGameObject instanceof Dome) {
            return 'D';
        } else if (topGameObject instanceof Playingfigure figure) {
            //Return the first letter of the name
            return figure.getName().charAt(0);
        }

        return '.';
    }

    private Gameobject getTopGameObject() {
        for (int i = gameobjects.length - 1; i >= 0; i--) {
            if (gameobjects[i] != null) {
                return gameobjects[i];
            }
        }
        return null;
    }

    public void removeFigure(Playingfigure playingfigure) {
        for (int i = 0; i < gameobjects.length; i++) {
            if (gameobjects[i] == playingfigure) {
                gameobjects[i] = null;
                return;
            }
        }
    }
}
