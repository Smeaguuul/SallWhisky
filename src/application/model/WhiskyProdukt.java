package application.model;

import java.time.LocalDate;

public class WhiskyProdukt {
    private Whisky whisky;
    private int flaskeStørrelse;
    private LocalDate hældtPåFlaskeDato;

    public WhiskyProdukt(Whisky whisky, int flaskeStørrelse) {
        this.whisky = whisky;
        this.flaskeStørrelse = flaskeStørrelse;
        this.hældtPåFlaskeDato = LocalDate.now();
    }

    // Udregner hvor manger flasker der skal bruges for at man kan have hele whiskyen
    // i flasker med en givenstørrelse.
    public int antalFlasker(){
        int antalFlasker = (whisky.getVæskeMængde() * 100) / flaskeStørrelse;
        return antalFlasker;
    }


}
