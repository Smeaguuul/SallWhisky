package application.model;

public class Malteri {
    private String navn;
    private String beskrivelse;
    private Addresse addresse;

    public Malteri(String navn, String beskrivelse, Addresse addresse) {
        this.navn = navn;
        this.beskrivelse = beskrivelse;
        this.addresse = addresse;
    }
}
