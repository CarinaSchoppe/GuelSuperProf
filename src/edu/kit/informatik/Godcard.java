package edu.kit.informatik;

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
     * Finds a edu.kit.informatik.Godcard based on its name.
     *
     * @param name the name of the edu.kit.informatik.Godcard to find
     * @return the edu.kit.informatik.Godcard matching the given name, or null if no matching edu.kit.informatik.Godcard is found
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
     * Returns the name of the edu.kit.informatik.Godcard.
     *
     * @return the name of the edu.kit.informatik.Godcard
     */
    public String getName() {
        return name;
    }
}
