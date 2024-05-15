package application.model;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Væske {
    protected double startmængde;
    protected double nuværendeMængde;

    public abstract HashMap<HashMap,Double> getOpbygning();
}
