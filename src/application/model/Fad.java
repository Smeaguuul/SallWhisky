package application.model;

public class Fad {
    private TræSort træSort;
    private String bemærkning;
    private TidligereIndhold tidligereIndhold;
    private int literStørrelse;
    private Forhandler forhandler;

    public Fad(TræSort træSort, String bemærkning, TidligereIndhold tidligereIndhold, int literStørrelse, Forhandler forhandler) {
        this.træSort = træSort;
        this.bemærkning = bemærkning;
        this.tidligereIndhold = tidligereIndhold;
        this.literStørrelse = literStørrelse;
        this.forhandler = forhandler;
    }
}
