package edu.kit.informatik;

/**
 * @author uyjam
 *
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

    private final String name;

    Godcard(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Godcard findGodcard(String name) {
        for (Godcard godcard : Godcard.values()) {
            if (godcard.getName().equalsIgnoreCase(name)) {
                return godcard;
            }
        }
        return null;
    }
}