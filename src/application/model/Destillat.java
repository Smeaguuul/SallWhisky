package application.model;

import java.time.LocalDate;
import java.util.HashMap;

public class Destillat extends Væske {
    private LocalDate startDato;
    private LocalDate slutDato;
    private double alkoholprocent;
    private RygningsType rygningsType;
    private String kommentar;
    private MaltBatch maltBatch;
    private static int antalBatches = 0;
    private int destillatnummer;


    private Medarbejder medarbejder;

    public int getDestillatnummer() {
        return destillatnummer;
    }

    public Destillat(LocalDate startDato, LocalDate slutDato, double literVæske, double alkoholprocent, RygningsType rygningsType, String kommentar, Medarbejder medarbejder, MaltBatch maltBatch) {
        antalBatches++;
        destillatnummer = antalBatches;
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
        String toString = "Destillat " + this.destillatnummer;
        toString += "\n\t" + maltBatch.toString();
        toString += "\n\tRygningstype: " + rygningsType.toString();
        toString += "\n\tAlkoholprocent: " + alkoholprocent + "%";
        return toString;
    }
}
