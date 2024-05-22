package application.model;

public class TapningsVæske {
    private double alkoholprocent;
    private double mængde;
    private Make make;
    private boolean erBrugt = false;

    public boolean isErBrugt() {
        return erBrugt;
    }

    public void setErBrugt(boolean erBrugt) {
        this.erBrugt = erBrugt;
    }

    public double getAlkoholprocent() {
        return alkoholprocent;
    }

    public double getMængde() {
        return mængde;
    }

    public Make getMake() {
        return make;
    }

    public TapningsVæske(double alkoholprocent, double mængde, Make make) {
        this.alkoholprocent = alkoholprocent;
        this.mængde = mængde;
        this.make = make;
        this.make.brugVæske(mængde);
    }

    @Override
    public String toString() {
        return mængde + " liter tapningsvæske, med " + alkoholprocent + "% alkohol, fra make " + make.getMakeNummer();
    }
}
