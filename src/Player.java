public class Player {


    private final Playingfigure[] figures = new Playingfigure[2];


    private final String name;
    private int godCardsDrawn = 0;

    private boolean athenaBlocked;

    private boolean apolloMove;

    private boolean artemisMove;

    private boolean atlasBuild;

    private boolean demeterBuild;

    private boolean hermesTeleport;
    private Godcard godcard;
    private boolean canEndTurn = false;

    public Player(Playingfigure playingfigure1, Playingfigure playingfigure2, String name) {
        figures[0] = playingfigure1;
        figures[1] = playingfigure2;
        this.name = name;
    }


    public void drawGodCard(Godcard godcard) {
        //check if godcard is already drawn
        if (!Game.getInstance().getGodcards().contains(godcard)) {
            throw new IllegalStateException("ERROR: This godcard is already drawn");
        }

        if (godCardsDrawn > 3) {
            throw new IllegalStateException("ERROR: You can only draw 2 godcards");
        }
        Game.getInstance().getGodcards().remove(godcard);
        System.out.println("OK");
        this.godcard = godcard;
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

            if (Game.getInstance().getDomeList().isEmpty()) {
                throw new IllegalStateException("ERROR: No domes left");
            }


            var dome = Game.getInstance().getDomeList().get(0);
            Game.getInstance().getDomeList().remove(0);
            //player wants to build a dome: if atlas is active can build at any height
            if (atlasBuild || whereToBuild.isDomeable()) {
                whereToBuild.getGameobjects()[whereToBuild.getHeightSquares()] = dome;
            }
        } else if (whatToBuild == BuildObject.CUBOID) {
            //check if enough cuboids are left

            if (Game.getInstance().getCuboidList().isEmpty()) {
                throw new IllegalStateException("ERROR: No cuboids left");
            }

            var cube = Game.getInstance().getCuboidList().get(0);
            Game.getInstance().getCuboidList().remove(0);
            whereToBuild.getGameobjects()[whereToBuild.getHeightSquares()] = cube;
        }

        if (Game.getInstance().checkWinning()) {
            System.out.println(name + " wins!");
        }

        canEndTurn = true;

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

        if (Game.getInstance().checkWinning()) {
            System.out.println(name + " wins!");
        }
    }

    public void endTurn() {
        if (!canEndTurn) return;
        athenaBlocked = false;
        apolloMove = false;
        artemisMove = false;
        atlasBuild = false;
        demeterBuild = false;
        hermesTeleport = false;
        canEndTurn = false;
        Game.getInstance().setCurrentPlayer(Game.getInstance().getOpponent(this));
        System.out.println(Game.getInstance().getCurrentPlayer().name);
    }

    public void surrender() {
        Game.getInstance().setRunning(false);
        System.out.println(Game.getInstance().getOpponent(this).name + " wins!");
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

    public String getName() {
        return name;
    }
}
