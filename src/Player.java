public class Player {


    private final Playingfigure[] figures;


    private boolean canDrawNow = true;
    private boolean canMoveNow = true;
    private boolean canBuildNow;
    private final String name;
    private int godCardsDrawn = 0;

    private boolean athenaBlocked;

    private boolean athenaBlockedMove;

    private boolean apolloMove;

    private boolean artemisMove;

    private boolean atlasBuild;

    private boolean demeterBuild;

    private boolean hermesTeleport;
    private boolean canEndTurn = false;

    private boolean hasBuild = false;
    private boolean hasMoved = false;

    public Player(Playingfigure playingfigure1, Playingfigure playingfigure2, String name) {
        figures = new Playingfigure[]{playingfigure1, playingfigure2};
        Game.getInstance().getPlayingField()[playingfigure1.getY()][playingfigure1.getX()].setFigure(playingfigure1);
        Game.getInstance().getPlayingField()[playingfigure2.getY()][playingfigure2.getX()].setFigure(playingfigure2);
        this.name = name;
    }


    public void drawGodCard(Godcard godcard) {

        if (!canDrawNow) throw new IllegalStateException("ERROR: You can't draw a godcard now");
        //check if godcard is already drawn
        if (!Game.getInstance().getGodcards().contains(godcard)) {
            throw new IllegalStateException("ERROR: This godcard is already drawn");
        }

        if (godCardsDrawn > 3) {
            throw new IllegalStateException("ERROR: You can only draw 2 godcards");
        }
        Game.getInstance().getGodcards().remove(godcard);


        switch (godcard) {
            case APOLLO -> apolloMove = true;
            case ARTEMIS -> artemisMove = true;
            case ATLAS -> atlasBuild = true;
            case DEMETER -> demeterBuild = true;
            case HERMES -> hermesTeleport = true;
            case ATHENA -> athenaBlockedMove = true;
        }
        System.out.println("OK");
        canDrawNow = false;
        godCardsDrawn++;
    }


    public void build(BuildObject whatToBuild, Gamefield whereToBuild) {
        if (!canBuildNow) throw new IllegalStateException("ERROR: You can't build now");
        if (hasBuild && !demeterBuild) {
            throw new IllegalArgumentException("ERROR: Allready build!");
        }
        if (demeterBuild) {
            demeterBuild = false;
        } else {
            canBuildNow = false;
            hasBuild = true;
        }


        //one figure needs to be adjecent to the field

        var adj = false;

        for (var figure : figures) {
            if (figure.getGameField().isAdjecent(whereToBuild)) {
                adj = true;
                break;
            }
        }

        if (!adj) throw new IllegalArgumentException("ERROR: No figure adjecent to this field");

        if (!whereToBuild.isBuildable()) throw new IllegalArgumentException("ERROR: Not Buildable");

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
        } else {
            System.out.println("OK");
        }


    }

    public void moveFigure(Playingfigure playingfigure, Gamefield where) {
        if (!canMoveNow) throw new IllegalStateException("ERROR: You can't move now");

        var oldPosition = playingfigure.getGameField();

        //check if figure is part of this player
        if (!playingfigure.getOwner().equals(this)) throw new IllegalStateException("ERROR: This figure is not part of this player");

        if (where.equals(oldPosition)) throw new IllegalArgumentException("ERROR: Move to old Position");

        if (hasMoved && !artemisMove) {
            throw new IllegalArgumentException("ERROR: Allready moved");
        }
        if (artemisMove) {
            artemisMove = false;
        } else {
            hasMoved = true;
            canMoveNow = false;
            canBuildNow = true;
        }

        if (!Game.getInstance().isReachable(playingfigure, where)) throw new IllegalStateException("ERROR: This field is not reachable");
        if (hermesTeleport && where.getHeightSquares() != playingfigure.getGameField().getHeightSquares())
            throw new IllegalStateException("ERROR: You can't teleport to a different height");
        if (where.isOccupied() && apolloMove) {
            var otherFigure = where.getPlayingFigure();
            playingfigure.setGameField(where);
            otherFigure.setGameField(oldPosition);
        } else {
            playingfigure.setGameField(where);
        }
        if (Game.getInstance().checkWinning()) {
            System.out.println(name + " wins!");
        } else {
            System.out.println("OK");
        }

        if (athenaBlockedMove && (playingfigure.getGameField().getHeightSquares() > oldPosition.getHeightSquares())) {
            Game.getInstance().getOpponent(this).setAthenaBlocked(true);
        }
    }

    public boolean isHermesTeleport() {
        return hermesTeleport;
    }

    public void setAthenaBlocked(boolean athenaBlocked) {
        this.athenaBlocked = athenaBlocked;
    }

    public void endTurn() {
        checkEndTurn();
        if (!canEndTurn) throw new IllegalStateException("ERROR: You can't end your turn yet");
        athenaBlocked = false;
        apolloMove = false;
        artemisMove = false;
        atlasBuild = false;
        athenaBlockedMove = false;
        demeterBuild = false;
        hermesTeleport = false;
        canEndTurn = false;
        hasBuild = false;
        hasMoved = false;
        canMoveNow = true;
        canDrawNow = true;
        Game.getInstance().setCurrentPlayer(Game.getInstance().getOpponent(this));
        System.out.println(Game.getInstance().getCurrentPlayer().name);
    }

    private void checkEndTurn() {
        if (hasBuild && hasMoved) {
            canEndTurn = true;
        }
    }


    public void surrender() {
        Game.getInstance().setRunning(false);
        System.out.println(Game.getInstance().getOpponent(this).name + " wins!");
    }


    public boolean isAthenaBlocked() {
        return athenaBlocked;
    }


    public boolean isApolloMove() {
        return apolloMove;
    }


    public void setAtlasBuild(boolean atlasBuild) {
        this.atlasBuild = atlasBuild;
    }

    public Playingfigure[] getFigures() {
        return figures;
    }


    public void setDemeterBuild(boolean demeterBuild) {
        this.demeterBuild = demeterBuild;
    }

    public String getName() {
        return name;
    }
}
