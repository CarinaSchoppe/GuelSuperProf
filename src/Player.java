import java.util.ArrayList;
import java.util.List;

public class Player {

    private final Playingfigure[] figures = new Playingfigure[2];
    private final List<Godcard> godcards = new ArrayList<>();
    private boolean canDrawTwice;
    private boolean isUnswappable;
    private int godCardsDrawn = 0;

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

    public Playingfigure[] getFigures() {
        return figures;
    }

    public List<Godcard> getGodcards() {
        return godcards;
    }

    public int getGodCardsDrawn() {
        return godCardsDrawn;
    }
}
