package application.model;

import java.time.LocalDate;

public class Destillat {
    private LocalDate startDato;
    private LocalDate slutDato;
    private double literVæske;
    private double alkoholprocent;
    private RygningsType rygningsType;
    private String kommentar;
    private MaltBatch maltBatch;

    private Medarbejder medarbejder;
    public Destillat(LocalDate startDato, LocalDate slutDato, double literVæske, double alkoholprocent, RygningsType rygningsType, String kommentar, Medarbejder medarbejder, MaltBatch maltBatch) {
        this.startDato = startDato;
        this.slutDato = slutDato;
        this.literVæske = literVæske;
        this.alkoholprocent = alkoholprocent;
        this.rygningsType = rygningsType;
        this.kommentar = kommentar;
        this.medarbejder = medarbejder;
        this.maltBatch = maltBatch;
    }

    public LocalDate getStartDato() {
        return startDato;
    }

    public LocalDate getSlutDato() {
        return slutDato;
    }

    public double getLiterVæske() {
        return literVæske;
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

    public Medarbejder getMedarbejder() {
        return medarbejder;
    }
}
