package application.model;

import java.time.LocalDate;

public class MaltBatch {
    private Kornsort kornsort;
    private int nummer;
    private LocalDate ankomstDato;
    private Malteri malteri;
    private Mark mark;

    public MaltBatch(Kornsort kornsort, int nummer, LocalDate ankomstDato, Malteri malteri, Mark mark) {
        this.kornsort = kornsort;
        this.nummer = nummer;
        this.ankomstDato = ankomstDato;
        this.malteri = malteri;
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "MaltBatch nr. " + nummer + " af kornsort " + kornsort + " (" + ankomstDato + ")";
    }
}
