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


    private Medarbejder medarbejder;
    public Destillat(LocalDate startDato, LocalDate slutDato, double literVæske, double alkoholprocent, RygningsType rygningsType, String kommentar, Medarbejder medarbejder, MaltBatch maltBatch) {
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
    public double getStartMængde(){
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
    public HashMap<HashMap,Double> getOpbygning() {
        return null;
    }
}
