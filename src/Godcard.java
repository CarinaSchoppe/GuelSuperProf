/**
 * Enumeration representing the available God cards in the game.
 * Each God card has a name associated with it.
 */

public enum Godcard {

    APOLLO("Apollo"),
    ARTEMIS("Artemis"),
    ATHENA("Athena"),
    ATLAS("Atlas"),
    DEMETER("Demeter"),
    HERMES("Hermes");

    private final String name;

    Godcard(String name) {
        this.name = name;
    }

    /**
     * Finds a Godcard based on its name.
     *
     * @param name the name of the Godcard to find
     * @return the Godcard matching the given name, or null if no matching Godcard is found
     */
    public static Godcard findGodcard(String name) {
        for (Godcard godcard : Godcard.values()) {
            if (godcard.getName().equalsIgnoreCase(name)) {
                return godcard;
            }
        }
        return null;
    }

    /**
     * Returns the name of the Godcard.
     *
     * @return the name of the Godcard
     */
    public String getName() {
        return name;
    }
}
