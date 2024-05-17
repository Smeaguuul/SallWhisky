package application.model;

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
}
