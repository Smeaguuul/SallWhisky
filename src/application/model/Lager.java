package application.model;

import java.util.Arrays;

public class Lager {
    private String navn;
    private Adresse adresse;
    private boolean[][][] lagerLokationer;

    public Lager(String navn, Adresse adresse, int reolAntal, int højde, int placeringerPrHylde) {
        this.navn = navn;
        this.adresse = adresse;
        lagerLokationer = new boolean[reolAntal][højde][placeringerPrHylde];
    }



    public boolean harPlads() {
        //Løber alle pladser igennem
        for (boolean[][] reoler : lagerLokationer) {
            for (boolean[] højde : reoler) {
                for (boolean plads : højde) {
                    if (!plads) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String getNavn() {
        return navn;
    }

    @Override
    public String toString() {
        return this.navn;
    }

    public String lagerInformation() {
        StringBuilder toString = new StringBuilder();
        toString.append(this.navn);
        toString.append("\n\tAntal Reoler: " + this.lagerLokationer.length);
        toString.append("\n\tHylder pr. Reol: " + this.lagerLokationer[0].length);
        toString.append("\n\tPlaceringer pr. Hylde " + this.lagerLokationer[0][0].length);
        return toString.toString();
    }
}
