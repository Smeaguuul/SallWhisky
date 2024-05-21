package application.model;

import java.time.LocalDate;

public class Destillat extends Væske {
    private LocalDate startDato;
    private LocalDate slutDato;
    private double alkoholprocent;
    private RygningsType rygningsType;
    private String kommentar;
    private MaltBatch maltBatch;
    private static int antalBatches = 0;
    private int nummer;


    private Medarbejder medarbejder;

    public int getNummer() {
        return nummer;
    }

    public Destillat(LocalDate startDato, LocalDate slutDato, double literVæske, double alkoholprocent, RygningsType rygningsType, String kommentar, Medarbejder medarbejder, MaltBatch maltBatch) {
        antalBatches++;
        nummer = antalBatches;
        this.startDato = startDato;
        this.slutDato = slutDato;
        this.alkoholprocent = alkoholprocent;
        this.rygningsType = rygningsType;
        this.kommentar = kommentar;
        this.medarbejder = medarbejder;
        this.maltBatch = maltBatch;
        this.startmængde = literVæske;
        this.nuværendeMængde = startmængde;
    }

    public Medarbejder getMedarbejder() {
        return this.medarbejder;
    }

    public LocalDate getStartDato() {
        return startDato;
    }

    public LocalDate getSlutDato() {
        return slutDato;
    }

    public double getNuværendemængde() {
        return nuværendeMængde;
    }

    public double getStartMængde() {
        return startmængde;
    }

    public double getAlkoholprocent() {
        return alkoholprocent;
    }

    public RygningsType getRygningsType() {
        return rygningsType;
    }

    public String getKommentar() {
        return kommentar;
    }

    public MaltBatch getMaltBatch() {
        return maltBatch;
    }

    @Override
    public String getOpbygning() {
        return this.toString();
    }

    @Override
    public String toString() {
        String toString = "Destillat " + this.nummer;
        toString += "\n\t" + maltBatch.toString();
        toString += "\n\tRygningstype: " + rygningsType.toString();
        toString += "\n\tAlkoholprocent: " + alkoholprocent + "%";
        toString += "\n\tMedarbejder: " + medarbejder.getSignatur();
        toString += "\n\tResterende væske: " + nuværendeMængde;
        return toString;
    }

    @Override
    public String getHistorie() {
        StringBuilder historieString = new StringBuilder();

        historieString.append("Destillat " + nummer);
        historieString.append("\n\tDestilleret " + startmængde + " L, i perioden " + startDato + " - " + slutDato + ", til en alkoholsprocent på " + alkoholprocent + "%.");
        historieString.append("\n\tRygningstype: " + rygningsType);

        //Maltbatch
        String maltBatchHistorie = maltBatch.getHistorie();
        String[] maltBatchHistorieArray = maltBatchHistorie.split("\\r?\\n|\\r");
        for (String string : maltBatchHistorieArray) {
            historieString.append("\n \t" + string);
        }

        //

        historieString.append("\n\t");

        return historieString.toString();
    }

    @Override
    public void brugVæske(Double bruges) {
            this.nuværendeMængde -= bruges;
    }
}
