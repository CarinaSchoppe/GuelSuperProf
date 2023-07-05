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

    public String getName() {
        return name;
    }

    public static Godcard findGodcard(String name) {
        for (Godcard godcard : Godcard.values()) {
            if (godcard.getName().equals(name)) {
                return godcard;
            }
        }
        return null;
    }
}
