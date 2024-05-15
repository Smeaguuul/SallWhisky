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

    public String getNavn() {
        return navn;
    }

    public String getCpr() {
        return cpr;
    }

    public String getSignatur() {
        return signatur;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public void setSignatur(String signatur) {
        this.signatur = signatur;
    }
}
