package application.model;

import java.time.LocalDate;
import java.util.NoSuchElementException;

public class Tidsperiode {
    private LocalDate påfyldningsdato;
    private LocalDate tømningsDato;
    private Fad fad;
    private Make make;

    public Tidsperiode(Fad fad, Make make) {
        this.påfyldningsdato = LocalDate.now();
        this.fad = fad;
        this.make = make;
        this.tømningsDato = null;
    }

    //Til test
    public Tidsperiode(Fad fad, Make make,LocalDate påfyldningsdato) {
        this.påfyldningsdato = påfyldningsdato;
        this.fad = fad;
        this.make = make;
        this.tømningsDato = null;
    }

    public Fad getFad() {
        return fad;
    }

    public LocalDate getPåfyldningsdato() {
        return påfyldningsdato;
    }

    public LocalDate getTømningsDato() throws Exception {
        return tømningsDato;
    }

    public Make getMake() {
        return make;
    }

    public void setTømningsDato() {
        this.tømningsDato = LocalDate.now();
    }

    public boolean erKlar() {
        boolean erKlar = false;
        if (påfyldningsdato.plusYears(3).isBefore(LocalDate.now())) {
            erKlar = true;
        }
        return erKlar;
    }
}
