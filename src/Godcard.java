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
}
