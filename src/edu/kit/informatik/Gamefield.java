package edu.kit.informatik;

public class Gamefield extends Gameobject {

    /**
     * This variable represents an array of edu.kit.informatik.Gameobject instances.
     * The array has a fixed length of 4 and is marked as final, 
     * meaning that it cannot be reassigned or modified after initialization.
     * It is recommended to access this variable through the getter method,
     * to ensure encapsulation and maintain data integrity.
     */
    private final Gameobject[] gameobjects = new Gameobject[4];

    /**
     * Creates a new instance of the edu.kit.informatik.Gamefield class.
     *
     * @param x the width of the game field
     * @param y the height of the game field
     */
    public Gamefield(int x, int y) {
        super(x, y);
    }


    /**
     * Returns an array of GameObjects.
     *
     * @return the array of GameObjects
     */
    public Gameobject[] getGameobjects() {
        return gameobjects;
    }

    /**
     * Checks if the tile is buildable.
     *
     * @return true if the tile is buildable, false otherwise.
     */
    public boolean isBuildable() {
        //if the height is not 4 and it has no player on it
        return !isPlayerOnIt() && !isPillar();
    }


    /**
     * Returns whether or not the object is on top.
     *
     * @return true if the object's height squares equal 3 and the player is on it,
     *         false otherwise.
     */
    public boolean isOnTop() {
        return getHeightSquares() == 3 && isPlayerOnIt();
    }

    /**
     * Checks if the current object is doable.
     *
     * @return {@code true} if the object is doable, otherwise {@code false}.
     */
    public boolean isDomeable() {
        //if the height is 4 and it has no player on it
        return isBuildable() && getHeightSquares() == 3;
    }

    /**
     * Returns the number of edu.kit.informatik.Cuboid objects in the gameobjects list.
     *
     * @return the number of edu.kit.informatik.Cuboid objects in the gameobjects list.
     */
    public int getHeightSquares() {
        var height = 0;
        for (Gameobject gameobject : gameobjects) {
            if (gameobject instanceof Cuboid) {
                height++;
            }
        }
        return height;
    }

    /**
     * Checks if the object is climable.
     *
     * @param apolloMove a boolean representing if Apollo is making the move
     * @return true if the object is climable, false otherwise
     */
    //it is is climable if there is not player on it and its not a pillar
    public boolean isClimeable(boolean apolloMove) {
        if (apolloMove) {
            return !isPillar();
        }
        return !isPillar() && !isPlayerOnIt();
    }

    /**
     * Checks if there is a playing figure object on the game board.
     *
     * @return true if a playing figure object is found on the game board, false otherwise
     */
    private boolean isPlayerOnIt() {
        for (Gameobject gameobject : gameobjects) {
            if (gameobject instanceof Figure) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the last game object in the list is a pillar.
     *
     * @return true if the last game object is a pillar, false otherwise.
     */
    public boolean isPillar() {
        //if last gameobject is a pillar
        for (var object : gameobjects) {
            if (object instanceof Dome) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks if any edu.kit.informatik.Playingfigure objects are occupying the game board.
     *
     * @return true if there is at least one Playingfigure object on the game board, otherwise false.
     */
    public boolean isOccupied() {
        for (Gameobject gameobject : gameobjects) {
            if (gameobject instanceof Figure) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the given gamefield is adjacent to this gamefield.
     *
     * @param where the gamefield to check adjacency with
     * @return true if the gamefield is adjacent, false otherwise
     */
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

    /**
     * Retrieves the playing figure from the list of game objects.
     *
     * @return the playing figure object if found in the list, otherwise null.
     */
    public Figure getPlayingFigure() {
        for (Gameobject gameobject : gameobjects) {
            if (gameobject instanceof Figure) {
                return (Figure) gameobject;
            }
        }
        return null;
    }

    /**
     * Sets the given playing figure in the game objects array.
     *
     * @param figure the playing figure to be set
     */
    public void setFigure(Figure figure) {
        for (int i = 0; i < gameobjects.length; i++) {
            if (gameobjects[i] == null || gameobjects[i] instanceof Figure) {
                gameobjects[i] = figure;
                return;
            }
        }
    }

    /**
     * Returns a string representation of the current object.
     * The string contains the names of the objects stored in the `gameobjects` array,
     * separated by commas. If the `gameobjects` array is empty, the string "Empty" is returned.
     *
     * @return A string representation of the current object, or "Empty" if there are no objects.
     */
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
            } else if (element instanceof Figure figure) {
                string += figure.getName().charAt(0) + ",";
            }

        }
        string = string.substring(0, string.length() - 1);
        return string;
    }

    /**
     * Retrieves the top character representing the top game object.
     *
     * @return the top character representing the top game object. Returns '.' if there is no top game object.
     */
    public Character getTopCharacter() {
        var topGameObject = getTopGameObject();
        if (topGameObject == null)
            return '.';

        if (topGameObject instanceof Cuboid) {
            return 'C';
        } else if (topGameObject instanceof Dome) {
            return 'D';
        } else if (topGameObject instanceof Figure figure) {
            //Return the first letter of the name
            return figure.getName().charAt(0);
        }

        return '.';
    }

    /**
     * Retrieves the topmost GameObject from the gameobjects array.
     *
     * @return the topmost GameObject, or null if there are no objects present
     */
    private Gameobject getTopGameObject() {
        for (int i = gameobjects.length - 1; i >= 0; i--) {
            if (gameobjects[i] != null) {
                return gameobjects[i];
            }
        }
        return null;
    }

    /**
     * Removes the specified edu.kit.informatik.Playingfigure from the game.
     *
     * @param figure the edu.kit.informatik.Playingfigure to be removed
     */
    public void removeFigure(Figure figure) {
        for (int i = 0; i < gameobjects.length; i++) {
            if (gameobjects[i] == figure) {
                gameobjects[i] = null;
                return;
            }
        }
    }
}
