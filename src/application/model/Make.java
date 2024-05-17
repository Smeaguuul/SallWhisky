package application.model;

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
        this.startmængde = startmængde;
        this.nuværendeMængde = startmængde;
    }

    public int lastIndex() {
        int lastIndex = tidsperioder.size()-1;
        if (lastIndex < 0 ){
            lastIndex = 0;
        }
        return lastIndex;
    }
    //TODO lav check om det er på et fad

    @Override
    public String toString() {

        StringBuilder st = new StringBuilder();
        st.append("Nr. " + makeNummer);
        st.append("\n\t" + tidsperioder.get(lastIndex()).getFad());
        st.append("\n\t" + tidsperioder.get(lastIndex()).getPåfyldningsdato());
        st.append("\n\tstartmængde: " + startmængde);
        st.append("\n\tResterende væske: " + nuværendeMængde);
        return  st.toString();
    }
}
