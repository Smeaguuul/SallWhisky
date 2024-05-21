package application.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Fad {
    private Træsort træsort;
    private String bemærkning;
    private TidligereIndhold tidligereIndhold;
    private int literStørrelse;
    private Forhandler forhandler;
    private ArrayList<Tidsperiode> tidsperioder = new ArrayList<Tidsperiode>();
    private static int antalFade = 0;
    private int fadNr = 0;
    private int[] lagerLokation = new int[3];;
    private Lager lager;

    public Fad(Træsort træSort, String bemærkning, TidligereIndhold tidligereIndhold, int literStørrelse, Forhandler forhandler) {
        antalFade++;
        this.fadNr = antalFade;
        this.træsort = træSort;
        this.tidligereIndhold = tidligereIndhold;
        this.literStørrelse = literStørrelse;
        this.forhandler = forhandler;
    }

    public int getFadNr() {
        return fadNr;
    }

    public void setBemærkning(String bemærkning) {
        this.bemærkning = bemærkning;
    }

    public Træsort getTræsort() {
        return træsort;
    }

    public String getBemærkning() {
        return bemærkning;
    }

    public TidligereIndhold getTidligereIndhold() {
        return tidligereIndhold;
    }

    public int getLiterStørrelse() {
        return literStørrelse;
    }

    // Tilføjet da den skulle bruges til test
    public ArrayList<Tidsperiode> getTidsperioder() {
        return tidsperioder;
    }

    // Tilføjet da den skulle bruges til test
    public int getLagerLokation(int i) {
        return lagerLokation[i];
    }
    // Tilføjet da den skulle bruges til test
    public Lager getLager() {
        return lager;
    }

    public Forhandler getForhandler() {
        return forhandler;
    }

    public Tidsperiode addMake(Make make) {
        Tidsperiode tidsperiode = new Tidsperiode(this, make);
        this.tidsperioder.add(tidsperiode);
        return tidsperiode;
    }

    //til test
    public Tidsperiode addMake(Make make, LocalDate påfyldningsDato) {
        Tidsperiode tidsperiode = new Tidsperiode(this, make, påfyldningsDato);
        this.tidsperioder.add(tidsperiode);
        return tidsperiode;
    }

    public Make getMake() throws Exception {
        if (this.tidsperioder.get(lastIndex()).getTømningsDato() != null){
            if (this.tidsperioder.get(lastIndex()).getTømningsDato().isBefore(LocalDate.now())) {
                throw new NoSuchElementException("Intet nuværende make.");
            }
        }
        return this.tidsperioder.get(lastIndex()).getMake();
    }

    public int lastIndex() {
        int lastIndex = tidsperioder.size() - 1;
        if (lastIndex < 0) {
            lastIndex = 0;
        }
        return lastIndex;
    }

    @Override
    public String toString() {
        return "Nr: " + fadNr + //TODO lav den mindre, og mere overskuellig. Ikke nødvendig med alt det her info, når vi har en extended info metode
                ", " + træsort +
                ", " + tidligereIndhold +
                ", " + literStørrelse + " L";
    }

    public String allFadInfo() {
        StringBuilder stBuilder = new StringBuilder();
        stBuilder.append("Nr: " + fadNr);
        stBuilder.append("\n Liter: " + literStørrelse);
        stBuilder.append("\n Træsort: " + træsort);
        stBuilder.append("\n Tidligere indhold: " + tidligereIndhold);
        stBuilder.append("\n Forhandler: " + forhandler);
        if (this.tidsperioder.size() != 0) {
            stBuilder.append("\n Nuværende make " + this.tidsperioder.get(lastIndex()).getMake().toStringWithoutFad());
        }
        if (this.lager != null) {
            stBuilder.append("\n Lager: " + lager);
            stBuilder.append("\n\t Lagerlokation: Reolnr: " + this.lagerLokation[0] + ", hyldeNr: " + this.lagerLokation[1] + ", Hylde placering: " + this.lagerLokation[2] + ".");
        }
        stBuilder.append("\n Bemærkning: " + bemærkning);
        return stBuilder.toString();
    }

    public boolean erKlar() {
        boolean erKlar = false;
        if (this.tidsperioder.size() > 0) {
            erKlar = this.tidsperioder.get(lastIndex()).erKlar();
        }
        return erKlar;
    }

    public boolean hasMake() {
        try {
            if (this.getMake() != null) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public void setLagerlokation(Lager lager, int reolNummer, int højdeNummer, int placeringsnummer) throws Exception{
        lager.pladserFad(reolNummer, højdeNummer, placeringsnummer); //Checker pladsen

        this.lager = lager;

        // this.lagerLokation = new int[3]; Flyttet op til toppen. Skulle bruges til test //TODO
        this.lagerLokation[0] = reolNummer;
        this.lagerLokation[1] = højdeNummer;
        this.lagerLokation[2] = placeringsnummer;
    }

    public void fjernLagerLokation() {
        this.lager = null;
        this.lagerLokation = null;
    }

    public boolean harLagerlokation () {
        boolean harLagerlokation = false;
        if (this.lagerLokation != null) {
            harLagerlokation = true;
        }
        return harLagerlokation;
    }

    public LocalDate getPåfyldningsDato() throws Exception {
        return getMake().getPåfyldningsDato();
    }

}
