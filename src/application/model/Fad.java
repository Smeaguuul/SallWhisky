package application.model;

import java.util.NoSuchElementException;

public class Fad {
    private Træsort træsort;
    private String bemærkning;
    private TidligereIndhold tidligereIndhold;
    private int literStørrelse;
    private Forhandler forhandler;
    private Make make;
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

    public void setMake(Make make) {
        this.make = make;
    }

    public Make getMake() throws Exception {
        if (this.make == null) {
            throw new NoSuchElementException("Intet make i dette fad.");
        }
        return this.make;
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
                "\n Nuværende make " + make +
                "\n Bemærkning: " + bemærkning;
    }
}
