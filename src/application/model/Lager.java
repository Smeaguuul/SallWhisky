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

    public void pladserFad(int reolNummer, int højdeNummer, int placeringsnummer) throws Exception {
        if (reolNummer > this.lagerLokationer.length || højdeNummer > this.lagerLokationer[0].length || placeringsnummer > this.lagerLokationer[0][0].length){
            throw new IllegalArgumentException("Det er ikke en lokation på lageret");
        }
        //Checker om lokationen er optaget
        if (this.lagerLokationer[reolNummer][højdeNummer][placeringsnummer]){
            throw new IllegalArgumentException("Der er ikke plads der.");
        }

        this.lagerLokationer[reolNummer][højdeNummer][placeringsnummer] = true;
    }
}
