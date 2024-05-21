package gui.opretDestillat;

import application.model.MaltBatch;
import application.model.Medarbejder;
import application.model.RygningsType;

import java.time.LocalDate;

public class CommonClass {
    private LocalDate startDato;
    private LocalDate slutDato;
    private String antalLiter;
    private String alkoholProcent;
    private RygningsType rygningsType;
    private MaltBatch maltBatch;
    private String kommentar;
    private Medarbejder medarbejder;

    public CommonClass(LocalDate startDato, LocalDate slutDato, String antalLiter, String alkoholProcent, RygningsType rygningsType, MaltBatch maltBatch, String kommentar, Medarbejder medarbejder) {
        this.startDato = startDato;
        this.slutDato = slutDato;
        this.antalLiter = antalLiter;
        this.alkoholProcent = alkoholProcent;
        this.rygningsType = rygningsType;
        this.maltBatch = maltBatch;
        this.kommentar = kommentar;
        this.medarbejder = medarbejder;
    }

    public LocalDate getStartDato() {
        return startDato;
    }

    public LocalDate getSlutDato() {
        return slutDato;
    }

    public String getAntalLiter() {
        return antalLiter;
    }

    public String getAlkoholProcent() {
        return alkoholProcent;
    }

    public RygningsType getRygningsType() {
        return rygningsType;
    }

    public MaltBatch getMaltBatch() {
        return maltBatch;
    }

    public String getKommentar() {
        return kommentar;
    }

    public Medarbejder getMedarbejder() {
        return medarbejder;
    }

    //Giver et fint overview over den indtastede information
    @Override
    public String toString() {
        String info = "";
        info += "Startdato : " + this.getStartDato();
        info += "\nSlutdato: " + this.getSlutDato();
        info += "\nAntal liter: " + this.getAntalLiter();
        info += "\nAlkoholprocent: " + this.getAlkoholProcent();
        info += "\nRygningstype: " + this.getRygningsType();
        info += "\nMaltbatch: " + this.getMaltBatch();
        info += "\nMedarbejder signatur: " + this.getMedarbejder().getSignatur();
        if (this.getKommentar().isEmpty()) {
            info += "\nIngen ydeligere kommentarer.";
        } else {
            info += "\nKommentar: " + this.getKommentar();
        }
        return info;
    }

}
