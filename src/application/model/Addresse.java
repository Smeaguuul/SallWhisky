package application.model;

public class Addresse {
    private String gadeNummer;
    private String gadeNavn;
    private String postnummer;
    private String land;

    public Addresse(String gadeNummer, String gadeNavn, String postnummer, String land) {
        this.gadeNummer = gadeNummer;
        this.gadeNavn = gadeNavn;
        this.postnummer = postnummer;
        this.land = land;
    }
}
