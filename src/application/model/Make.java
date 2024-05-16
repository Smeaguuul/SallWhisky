package application.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Make extends Væske {

    private final ArrayList<Tidsperiode> tidsperioder = new ArrayList<>();
    private HashMap<Væske, Double> væskeDoubleHashMap = new HashMap<>();
    private static int antalMakes = 0;
    private int makeNummer;

//TODO lav en metode til tapning, som sætter tømningsdato til idag, hvis 0 liter rammes
    //TODO lav en metode så man kan sætte en tømningsdato ind tved oprettelsen af et nyt make
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

        //Laver associeringsklassen til at holde styr på datoer
        this.tidsperioder.add(fad.addMake(this));

        //Udregner totalmængde
        for (Double liter : væskeDoubleHashMap.values()) {
            this.startmængde += liter;
        }
        this.væskeDoubleHashMap = new HashMap<>(væskeDoubleHashMap);
        this.nuværendeMængde = startmængde;
    }

    //Til test
    public Make(Fad fad, HashMap<Væske, Double> væskeDoubleHashMap, LocalDate datoOprettet) {
        antalMakes++;
        this.makeNummer = antalMakes;

        //Laver associeringsklassen til at holde styr på datoer
        this.tidsperioder.add(fad.addMake(this, datoOprettet));

        //Udregner totalmængde
        for (Double liter : væskeDoubleHashMap.values()) {
            this.startmængde += liter;
        }
        this.væskeDoubleHashMap = new HashMap<>(væskeDoubleHashMap);
        this.nuværendeMængde = startmængde;
    }

    public int lastIndex() {
        int lastIndex = tidsperioder.size()-1;
        if (lastIndex < 0 ){
            lastIndex = 0;
        }
        return lastIndex;
    }

    @Override
    public String toString() {
        return "Nr. " + makeNummer +
                "\n\t" + tidsperioder.get(lastIndex()).getFad() + //TODO lav check om det er på et fad
                "\n\t" + tidsperioder.get(lastIndex()).getPåfyldningsdato() +
                "\n\tstartmængde: " + startmængde +
                "\n\tResterende væske: " + nuværendeMængde;
    }

    @Override
    public void brugVæske(Double bruges) {
        if (bruges > this.nuværendeMængde) {
            throw new IllegalArgumentException("Ikke nok resterende make.");
        }
        this.nuværendeMængde -= bruges;
        if (this.nuværendeMængde == 0){
            this.tidsperioder.get(lastIndex()).setTømningsDato();
        }
    }

    public int getMakeNummer() {
        return makeNummer;
    }
}
