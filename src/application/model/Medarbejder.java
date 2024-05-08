package application.model;

public class Medarbejder {
    private int nummer;
    private String name;
    private String cpr;
    private String signatur;

    public Medarbejder(int nummer, String name, String cpr, String signatur) {
        this.nummer = nummer;
        this.name = name;
        this.cpr = cpr;
        this.signatur = signatur;
    }

    public int getNummer() {
        return nummer;
    }

    @Override
    public String toString() {
        return nummer + " " + name + " " + cpr + " " + signatur;
    }
}
