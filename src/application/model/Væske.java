package application.model;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Væske {
    protected double startmængde;
    protected double nuværendeMængde;

    public abstract String getOpbygning();

    public double getNuværendeMængde() {
        return nuværendeMængde;
    }

    public abstract void brugVæske(Double bruges);
}
