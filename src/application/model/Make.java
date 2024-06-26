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

    @Override
    public String getHistorie() {
        StringBuilder historieString = new StringBuilder();
        historieString.append("Make " + makeNummer);
        historieString.append("\nBestår af: ");
        for (Map.Entry<Væske, Double> væskeDoubleEntry : væskeDoubleHashMap.entrySet()) {
            //Splitte den tidligere opbygning op i linjer af en, som en array af strings
            String makeOpbygning = væskeDoubleEntry.getKey().getHistorie();
            String[] strings = makeOpbygning.split("\\r?\\n|\\r");

            //Vi tilføjer procenten til væsken
            double procent = Math.round(100.00 * ((væskeDoubleEntry.getValue() / startmængde) * 100.00)) / 100.00;
            strings[0] += " - " + procent + "%";
            for (String string : strings) {
                historieString.append("\n \t" + string);
            }
        }

        historieString.append("\nLagringsHistorie: ");
        for (Tidsperiode tidsperiode : tidsperioder) {
            historieString.append("\n\tLagret på fad " + tidsperiode.getFad().getFadNr() + " fra " + tidsperiode.getPåfyldningsdato() + " til " + tidsperiode.getTømningsDato());
        }

        return historieString.toString();
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
        int lastIndex = tidsperioder.size() - 1;
        if (lastIndex < 0) {
            lastIndex = 0;
        }
        return lastIndex;
    }

    @Override
    public String toString() {

        StringBuilder st = new StringBuilder();
        st.append("Nr. " + makeNummer);
        st.append("\n\t" + tidsperioder.get(lastIndex()).getFad());
        st.append("\n\t" + tidsperioder.get(lastIndex()).getPåfyldningsdato());
        st.append("\n\tstartmængde: " + startmængde);
        st.append("\n\tResterende væske: " + nuværendeMængde);
        return st.toString();
    }

    @Override
    public void brugVæske(Double bruges) {
        if (bruges > this.nuværendeMængde) {
            throw new IllegalArgumentException("Ikke nok resterende make.");
        }
        this.nuværendeMængde -= bruges;
        if (this.nuværendeMængde == 0) {
            this.tidsperioder.get(lastIndex()).setTømningsDato();
        }
    }

    public HashMap<Væske, Double> getVæskeOgLiter() {
        return new HashMap<>(væskeDoubleHashMap);
    }

    public int getMakeNummer() {
        return makeNummer;
    }

    public String toStringWithoutFad() {
        StringBuilder st = new StringBuilder();
        st.append("Nr. " + makeNummer);
        st.append("\n\tPåfyldningsdato: " + tidsperioder.get(lastIndex()).getPåfyldningsdato());
        if (tidsperioder.get(lastIndex()).getTømningsDato() != null) {
            st.append("\n\tTømningsdato: " + tidsperioder.get(lastIndex()).getTømningsDato());
        }
        st.append("\n\tstartmængde: " + startmængde);
        st.append("\n\tResterende væske: " + nuværendeMængde);
        return st.toString();
    }

    public LocalDate getPåfyldningsDato() {
        return this.tidsperioder.get(lastIndex()).getPåfyldningsdato();
    }
}
