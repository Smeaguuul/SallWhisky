package application.model;

import java.util.ArrayList;

public class Whisky {
    private double fortyndningsFaktor; //Er i procent
    private String kommentar;
    private ArrayList<TapningsVæske> tapningsVæsker = new ArrayList<>();
    private static int antalWhiskyer = 0;
    private int nummer;
    private boolean brugt;

    public Whisky(double fortyndningsFaktor, String kommentar, ArrayList<TapningsVæske> tapningsVæsker) {
        //Sætter alle tapningsVæsker til 0
        for (TapningsVæske tapningsVæske : tapningsVæsker) {
            tapningsVæske.setErBrugt(true);
        }
        this.fortyndningsFaktor = fortyndningsFaktor;
        this.kommentar = kommentar;
        this.tapningsVæsker = new ArrayList<>(tapningsVæsker);
        antalWhiskyer++;
        this.nummer = antalWhiskyer;
    }

    public boolean isCaskStrength() {
        boolean iscaskstrength = false;
        if (this.fortyndningsFaktor == 0) {
            iscaskstrength = true;
        }
        return iscaskstrength;
    }

    public boolean isSingleCask() {
        boolean isSingleCask = false;
        if (this.tapningsVæsker.size() == 1) {
            isSingleCask = true;
        }
        return isSingleCask;
    }

    public String getHistorie() {
        StringBuilder historieString = new StringBuilder();
        historieString.append("Whiskynr: " + this.nummer);

        //Kvaliteter
        historieString.append("\nKvaliteter: ");
        if (isSingleCask()) {
            historieString.append("\n\tSingemalt");
        } else {
            historieString.append("\n\tSingemalt");
        }
        if (isCaskStrength()) {
            historieString.append("\n\tCaskstrength");
        }

        //Kommentar
        historieString.append("\nKommentar: ");
        if (kommentar.isEmpty()) {
            historieString.append("\n\tIngen");
        } else {
            historieString.append("\n\t" + kommentar);
        }


        historieString.append("\n");

        historieString.append("\nBestår af: ");
        historieString.append("\n_____________________________________________________________________________________________________________");
        for (TapningsVæske tapningsVæske : this.tapningsVæsker) {
            historieString.append("\n" + tapningsVæske.getMake().getHistorie());
            historieString.append("\n\t Mængde væske brugt herfra: " + tapningsVæske.getMængde() + " L");
            historieString.append("\n");
        }
        return historieString.toString();
    }

    // Udregner den samlede væskemængde
    public int getVæskeMængde(){
        int væskeMængde = 0;
        for (TapningsVæske tapningsVæske : tapningsVæsker) {
            væskeMængde += tapningsVæske.getMængde();
        }
        return væskeMængde;
    }

    public void setBrugt(boolean brugt) {
        this.brugt = brugt;
    }

    public boolean isBrugt() {
        return brugt;
    }

    @Override
    public String toString() {
        return "Whisky Nr: " + nummer;
    }
}
