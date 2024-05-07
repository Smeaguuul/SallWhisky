package application.model;

public class Fad {
    private Træsort træsort;
    private String bemærkning;
    private TidligereIndhold tidligereIndhold;
    private int literStørrelse;
    private Forhandler forhandler;

    public Fad(Træsort træSort, String bemærkning, TidligereIndhold tidligereIndhold, int literStørrelse, Forhandler forhandler) {
        this.træsort = træSort;
        this.tidligereIndhold = tidligereIndhold;
        this.literStørrelse = literStørrelse;
        this.forhandler = forhandler;
    }

    public void setBemærkning(String bemærkning) {
        this.bemærkning = bemærkning;
    }
}
