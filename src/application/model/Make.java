package application.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Make extends Væske{

    private LocalDate påfyldningsdato;
    private Fad fad;
    private HashMap<Væske,Double> væskeDoubleHashMap = new HashMap<>();


    @Override
    public HashMap<HashMap, Double> getOpbygning() {
        return null;
    }

    public Make(Fad fad, HashMap<Væske, Double> væskeDoubleHashMap, double startmængde) {
        this.fad = fad;
        this.væskeDoubleHashMap = væskeDoubleHashMap;
        this.startmængde = startmængde;
        this.påfyldningsdato = LocalDate.now();
        this.nuværendeMængde = startmængde;
    }
}
