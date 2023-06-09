package edu.kit.informatik;

/**
 * Enumeration representing the available God cards in the game.
 * Each God card has a name associated with it.
 * @author uyjam
 * @version 1.0
 */

public enum Godcard {

    /**
     * Apollo card for playing rules.
     */
    APOLLO("Apollo"),
    /**
     * Artemis card for playing rules.
     */
    ARTEMIS("Artemis"),
    /**
     * Athena card for playing rules.
     */
    ATHENA("Athena"),
    /**
     * Atlas card for playing rules.
     */
    ATLAS("Atlas"),
    /**
     * Demeter card for playing rules.
     */
    DEMETER("Demeter"),
    /**
     * Hermes card for playing rules.
     */
    HERMES("Hermes");

    /**
     * The name of the edu.kit.informatik.Godcard.
     */
    private final String name;

    /**
     * Constructs a new edu.kit.informatik.Godcard with the given name.
     *
     * @param name the name of the edu.kit.informatik.Godcard
     */
    Godcard(String name) {
        this.name = name;
    }

    /**
     * Finds a edu.kit.informatik.Godcard based on its name.
     *
     * @param name the name of the edu.kit.informatik.Godcard to find
     * @return the Godcard matching the given name, or null if no matching edu.kit.informatik.Godcard is found
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
     * @return the name of the edu.kit.informatik.Godcard
     */
    public String getName() {
        return name;
    }
}
