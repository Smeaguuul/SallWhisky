package application.model;

public enum TidligereIndhold {
    SHERRY("Sherry"), BOURBON("Bourbon"), INTET("Intet");
    private final String name;

    private TidligereIndhold(String s) {
        name = s;
    }

    public String toString() {
        return this.name;
    }
}
