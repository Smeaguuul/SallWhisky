package application.model;

import java.util.ArrayList;

public class Whisky {
    private double fortyndningsFaktor;
    private String kommentar;
    private ArrayList<TapningsVæske> tapningsVæsker = new ArrayList<>();

    public Whisky(double fortyndningsFaktor, String kommentar, ArrayList<TapningsVæske> tapningsVæsker) {
        this.fortyndningsFaktor = fortyndningsFaktor;
        this.kommentar = kommentar;
        this.tapningsVæsker = new ArrayList<>(tapningsVæsker);
    }

    public boolean isCaskStrength() {
        boolean iscaskstrength = false;
        if (this.fortyndningsFaktor == 0){
            iscaskstrength = true;
        }
        return iscaskstrength;
    }

    public boolean isSingleCask() {
        boolean isSingleCask = false;
        if (this.tapningsVæsker.size() == 1){
            isSingleCask = true;
        }
        return isSingleCask;
    }
}
