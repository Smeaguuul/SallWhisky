package application.model;

import java.time.LocalDate;

public class Destillat {
    private LocalDate startDato;
    private LocalDate slutDato;
    private double literVæske;
    private double alkoholprocent;
    private RygningsType rygningsType;
    private String kommentar;

    public Destillat(LocalDate startDato, LocalDate slutDato, double literVæske, double alkoholprocent, RygningsType rygningsType, String kommentar) {
        this.startDato = startDato;
        this.slutDato = slutDato;
        this.literVæske = literVæske;
        this.alkoholprocent = alkoholprocent;
        this.rygningsType = rygningsType;
        this.kommentar = kommentar;
    }

}
