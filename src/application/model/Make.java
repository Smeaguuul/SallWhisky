package application.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Make extends Væske {

    private LocalDate påfyldningsdato;
    private Fad fad;
    private HashMap<Væske, Double> væskeDoubleHashMap = new HashMap<>();
    private static int antalMakes = 0;
    private int makeNummer;


    @Override
    public String getOpbygning() {
        String opbygning = "Make " + makeNummer;

        for (Map.Entry<Væske, Double> væskeDoubleEntry : væskeDoubleHashMap.entrySet()) {
            //Splitte den tidligere opbygning op i linjer af en, som en array af strings
            String makeOpbygning = væskeDoubleEntry.getKey().getOpbygning();
            String[] strings = makeOpbygning.split("\\r?\\n|\\r");

            //Vi tilføjer procenten til væsken
            double procent = Math.round(100.00 * ((væskeDoubleEntry.getValue() / startmængde) * 100.00)) / 100.00;
            strings[0] += " - " + procent + "%";
            for (String string : strings) {
                opbygning += "\n \t" + string;
            }
        }
        return opbygning;
    }

    public Make(Fad fad, HashMap<Væske, Double> væskeDoubleHashMap) {
        antalMakes++;
        this.makeNummer = antalMakes;

        this.fad = fad;
        fad.setMake(this);

        //Udregner totalmængde
        for (Double liter : væskeDoubleHashMap.values()) {
            this.startmængde += liter;
        }

        this.væskeDoubleHashMap = væskeDoubleHashMap;
        this.startmængde = startmængde;
        this.påfyldningsdato = LocalDate.now();
        this.nuværendeMængde = startmængde;
    }

    @Override
    public String toString() {
        return "Nr. " + makeNummer +
                ", " + fad +
                ", " + påfyldningsdato +
                ", startmængde: " + startmængde +
                ", nuværende mængde: " + nuværendeMængde;
    }
}
