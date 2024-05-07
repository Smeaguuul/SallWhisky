package application.model;

public enum Træsort {
    QUERCUSPATREA("Quercus Patrea"), QUERCUSROBER("Quercus Rober"), QUERCUSALBA ("Quercus Alba");

    private final String name;

    private Træsort(String s) {
        name = s;
    }

    public String toString() {
        return this.name;
    }
}
