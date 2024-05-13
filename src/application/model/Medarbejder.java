package application.model;

public class Medarbejder {
    private static int antalMedarbejder = 0;
    private int nummer;
    private String navn;
    private String cpr;
    private String signatur;

    public Medarbejder(String navn, String cpr, String signatur) {
        antalMedarbejder++;
        this.nummer = antalMedarbejder;
        this.navn = navn;
        this.cpr = cpr;
        this.signatur = signatur;
    }

    public Medarbejder(int nummer, String navn, String cpr, String signatur) {
        antalMedarbejder++;
        this.nummer = nummer;
        this.navn = navn;
        this.cpr = cpr;
        this.signatur = signatur;
    }

    public int getNummer() {
        return nummer;
    }

    @Override
    public String toString() {
        String toString = "";
        toString += "Navn: " + navn;
        toString += "\nNummer: " + nummer;
        toString += "\nCPR: " + cpr.substring(0,7) + "****";
        toString += "\nSignatur: " + signatur;
        return toString;
    }

    public String getSignatur() {
        return signatur;
    }
}
