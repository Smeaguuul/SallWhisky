package application.model;

import java.util.ArrayList;

public class Forhandler {
    private String navn;
    private String region;
    private String land;

    public Forhandler(String navn, String region, String land) {
        this.navn = navn;
        this.region = region;
        this.land = land;
    }

    @Override
    public String toString() {
        return navn + " (" + region + ", "+ land + ")";
    }
}
