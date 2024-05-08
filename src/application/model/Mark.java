package application.model;

public class Mark {
    private String navn;
    private String beskrivelse;
    private Addresse addresse;

    public Mark(String beskrivelse, String navn, Addresse addresse) {
        this.beskrivelse = beskrivelse;
        this.navn = navn;
        this.addresse = addresse;
    }
}
