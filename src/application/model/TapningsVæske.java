package application.model;

public class TapningsVæske {
    private double alkoholprocent;
    private double mængde;
    private Make make;

    public TapningsVæske(double alkoholprocent, double mængde, Make make) {
        this.alkoholprocent = alkoholprocent;
        this.mængde = mængde;
        this.make = make;
        this.make.brugVæske(mængde);
    }
}
