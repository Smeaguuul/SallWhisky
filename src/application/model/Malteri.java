package application.model;

public class Malteri {
    private String navn;
    private String beskrivelse;
    private Adresse adresse;

    public Malteri(String navn, String beskrivelse, Adresse adresse) {
        this.navn = navn;
        this.beskrivelse = beskrivelse;
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return "Malteri " + navn + ", " + beskrivelse;
    }
}
