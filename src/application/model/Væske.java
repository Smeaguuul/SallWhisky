package application.model;

public abstract class Væske {
    protected double startmængde;
    protected double nuværendeMængde;

    public abstract String getOpbygning();

    public double getNuværendeMængde() {
        return nuværendeMængde;
    }

    public void brugVæske(Double bruges) {
        this.nuværendeMængde -= bruges;
    }
}
