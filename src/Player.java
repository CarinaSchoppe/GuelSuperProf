public class Player {

    private final Playingfigure[] figures = new Playingfigure[2];

    private int godCardsDrawn = 0;

    private boolean athenaBlocked;

    private boolean apolloMove;

    private boolean artemisMove;

    private boolean atlasBuild;

    private boolean demeterBuild;

    private boolean hermesTeleport;

    public Player(Playingfigure playingfigure1, Playingfigure playingfigure2) {
        figures[0] = playingfigure1;
        figures[1] = playingfigure2;
    }

    public void drawGodCard() {
        if (godCardsDrawn > 3) {
            throw new IllegalStateException("You can only draw 2 godcards");
        }
        godCardsDrawn++;
    }


    public void performBuild(BuildObject whatToBuild, Gamefield whereToBuild) {
        //one figure needs to be adjecent to the field
        for (var figure : figures) {
            if (!figure.getGameField().isAdjecent(whereToBuild)) {
                return;
            }
        }

        if (!whereToBuild.isBuildable()) return;

        if (whatToBuild == BuildObject.DOME) {
            var dome = new Dome(whereToBuild.getX(), whereToBuild.getY());
            //player wants to build a dome: if atlas is active can build at any height
            if (atlasBuild || whereToBuild.isDomeable()) {
                whereToBuild.getGameobjects()[whereToBuild.getHeightSquares()] = dome;
            }
        } else if (whatToBuild == BuildObject.CUBOID) {
            var cube = new Cuboid(whereToBuild.getX(), whereToBuild.getY());
            whereToBuild.getGameobjects()[whereToBuild.getHeightSquares()] = cube;
        }
    }

    public void moveFigure(Playingfigure playingfigure, Gamefield where) {
        var oldPosition = playingfigure.getGameField();
        if (!Game.getInstance().isReachable(playingfigure, where)) return;
        if (hermesTeleport && where.getHeightSquares() != playingfigure.getGameField().getHeightSquares())
            return;
        if (where.isOccupied() && apolloMove) {
            var otherFigure = where.getPlayingFigure();
            playingfigure.setGameField(where);
            otherFigure.setGameField(oldPosition);
        } else {
            playingfigure.setGameField(where);
        }
    }

    public void endTurn() {
        athenaBlocked = false;
        apolloMove = false;
        artemisMove = false;
        atlasBuild = false;
        demeterBuild = false;
        hermesTeleport = false;
        Game.getInstance().setCurrentPlayer(Game.getInstance().getOpponent(this));
    }

    public boolean isAthenaBlocked() {
        return athenaBlocked;
    }

    public void setAthenaBlocked(boolean athenaBlocked) {
        this.athenaBlocked = athenaBlocked;
    }

    public boolean isApolloMove() {
        return apolloMove;
    }

    public void setApolloMove(boolean apolloMove) {
        this.apolloMove = apolloMove;
    }

    public boolean isArtemisMove() {
        return artemisMove;
    }

    public void setArtemisMove(boolean artemisMove) {
        this.artemisMove = artemisMove;
    }

    public boolean isAtlasBuild() {
        return atlasBuild;
    }

    public void setAtlasBuild(boolean atlasBuild) {
        this.atlasBuild = atlasBuild;
    }

    public Playingfigure[] getFigures() {
        return figures;
    }

    public int getGodCardsDrawn() {
        return godCardsDrawn;
    }

    public boolean isDemeterBuild() {
        return demeterBuild;
    }

    public void setDemeterBuild(boolean demeterBuild) {
        this.demeterBuild = demeterBuild;
    }

    public boolean isHermesTeleport() {
        return hermesTeleport;
    }

    public void setHermesTeleport(boolean hermesTeleport) {
        this.hermesTeleport = hermesTeleport;
    }
}
