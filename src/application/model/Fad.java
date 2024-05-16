package application.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    public Fad(Træsort træSort, String bemærkning, TidligereIndhold tidligereIndhold, int literStørrelse, Forhandler forhandler) {
        antalFade++;
        this.fadNr = antalFade;
        this.træsort = træSort;
        this.tidligereIndhold = tidligereIndhold;
        this.literStørrelse = literStørrelse;
        this.forhandler = forhandler;
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

    public Forhandler getForhandler() {
        return forhandler;
    }

    public Tidsperiode addMake(Make make) {
        Tidsperiode tidsperiode = new Tidsperiode(this, make);
        this.tidsperioder.add(tidsperiode);
        return tidsperiode;
    }

    public Make getMake() throws Exception {
        if (this.tidsperioder.get(lastIndex()).getTømningsDato().isBefore(LocalDate.now())) {
            throw new NoSuchElementException("Intet nuværende make.");
        }
        return this.tidsperioder.get(lastIndex()).getMake();
    }

    public int lastIndex() {
        int lastIndex = tidsperioder.size()-1;
        if (lastIndex < 0 ){
            lastIndex = 0;
        }
        return lastIndex;
    }

    @Override
    public String toString() {
        return "Nr: " + fadNr +
                ", " + træsort +
                ", " + tidligereIndhold +
                ", " + literStørrelse +
                " L, " + forhandler;
    }
    public String allFadInfo(){
        return
                "Nr: " + fadNr +
                "\n Liter: " + literStørrelse +
                "\n Træsort: " + træsort +
                "\n Tidligere indhold: " + tidligereIndhold +
                "\n Forhandler: " + forhandler +
                //"\n Nuværende make " + this.tidsperioder.get(lastIndex()).getMake() + //TODO lav check om det har et nuværende make
                "\n Bemærkning: " + bemærkning;
    }

    public boolean erKlar() {
        return this.tidsperioder.get(lastIndex()).erKlar();
    }
}
