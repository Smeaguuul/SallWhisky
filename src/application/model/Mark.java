package application.model;

public class Mark {
    private String navn;
    private String beskrivelse;
    private Adresse adresse;

    public Mark(String beskrivelse, String navn, Adresse adresse) {
        this.beskrivelse = beskrivelse;
        this.navn = navn;
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return navn + ", " + beskrivelse + ".";
    }
}
