package gui.opretFad;

import application.model.Forhandler;
import application.model.TidligereIndhold;
import application.model.Træsort;

public class CommonClass {
    private Træsort træsort;
    private Forhandler forhandler;
    private TidligereIndhold tidligereIndhold;
    private String literStørrelse;
    private String kommentar;

    public CommonClass(Træsort træsort, Forhandler forhandler, TidligereIndhold tidligereIndhold, String literStørrelse, String kommentar) {
        this.træsort = træsort;
        this.forhandler = forhandler;
        this.tidligereIndhold = tidligereIndhold;
        this.literStørrelse = literStørrelse;
        this.kommentar = kommentar;
    }

    public Træsort getTræsort() {
        return træsort;
    }

    public Forhandler getForhandler() {
        return forhandler;
    }

    public TidligereIndhold getTidligereIndhold() {
        return tidligereIndhold;
    }

    public String getLiterStørrelse() {
        return literStørrelse;
    }

    public String getKommentar() {
        return kommentar;
    }

    @Override
    public String toString() {
        String info = "";
        info += "Træsort : " + this.getTræsort();
        info += "\nForhandler: " + this.getForhandler();
        info += "\nTidligere indhold: " + this.getTidligereIndhold();
        info += "\nAntal liter i fad: " + this.getLiterStørrelse();
        if (this.getKommentar().isEmpty()) {
            info += "\nIngen ydeligere kommentarer.";
        } else {
            info += "\nKommentar: " + this.getKommentar();
        }
        return info;
    }
}
