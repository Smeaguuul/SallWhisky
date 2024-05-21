package application.model;

import java.time.LocalDate;

public class MaltBatch implements historieInterface{
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

    @Override
    public String getHistorie() {
        StringBuilder historieString = new StringBuilder();

        historieString.append("Maltbatch nr. " + nummer);
        historieString.append("\n\tKornsort: " + kornsort);
        historieString.append("\n\tMalteri: " + malteri);
        historieString.append("\n\tMark: " + mark);

        return historieString.toString();
    }
}
