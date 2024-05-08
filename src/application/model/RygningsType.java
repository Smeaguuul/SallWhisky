package application.model;

public enum RygningsType {
    IKKERØGET("Ikke røget"),
    TØRVRØGET("Tørv røget");
    private final String name;

    RygningsType(String s) {
        this.name = s;
    }

    public String toString() {
        return this.name;
    }

}
