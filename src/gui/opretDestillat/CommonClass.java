package gui.opretDestillat;

import application.model.Medarbejder;

import java.time.LocalDate;

public class CommonClass {
    private LocalDate startDato;
    private LocalDate slutDato;
    private String antalLiter;
    private String alkoholProcent;
    private Object rygningsType;
    private Object maltBatch;
    private String kommentar;
    private Medarbejder medarbejder;

    public CommonClass(LocalDate startDato, LocalDate slutDato, String antalLiter, String alkoholProcent, Object rygningsType, Object maltBatch, String kommentar, Medarbejder medarbejder) {
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

    public Object getRygningsType() {
        return rygningsType;
    }

    public Object getMaltBatch() {
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
