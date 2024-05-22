package gui.tapFad;

import application.model.Fad;
import application.model.TapningsVæske;

public class CommonClass {
    private Fad fad;
    private double alkoholprocent;
    private double mængde;
    private TapningsVæske tapningsVæske;

    public TapningsVæske getTapningsVæske() {
        return tapningsVæske;
    }

    public void setTapningsVæske(TapningsVæske tapningsVæske) {
        this.tapningsVæske = tapningsVæske;
    }

    public CommonClass(Fad fad, double alkoholprocent, double mængde) {
        this.fad = fad;
        this.alkoholprocent = alkoholprocent;
        this.mængde = mængde;
    }

    public Fad getFad() {
        return fad;
    }

    public double getAlkoholprocent() {
        return alkoholprocent;
    }

    public double getMængde() {
        return mængde;
    }

    @Override
    public String toString() {
        String info = "";
        info += "Fad: " + this.fad;
        info += "\nAlkoholprocent: " + alkoholprocent;
        info += "\nMængde: " + mængde;
        return info;
    }
}
