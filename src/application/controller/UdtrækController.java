package application.controller;

import application.model.Fad;
import application.model.Lager;
import application.model.TidligereIndhold;
import application.model.Træsort;
import storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;

public class UdtrækController {
    public static ArrayList<Fad> getFad() {
        return Storage.getFade();
    }

    public static ArrayList<Fad> getFilterFad(LocalDate senestePåfyldningsDato, TidligereIndhold tidligereIndhold, Træsort træsort, Lager lager) {
        //Opretter det specificerede filter
        Predicate<Fad> fadFilter = new Predicate<Fad>() {
            @Override
            public boolean test(Fad fad) {
                boolean matches = true; //Vi starter med at antage at den matcher predicate

                //Checker påfyldningsdato
                if (senestePåfyldningsDato != null) {
                    if (fad.hasMake()) {
                        try {
                            matches = senestePåfyldningsDato.isAfter(fad.getPåfyldningsDato().minusDays(1)); //Trækker 1 dag fra, for at inkludere den sidste dag
                        } catch (Exception e) {
                            matches = false;
                        }
                    } else {
                        matches = false;
                    }
                }

                //Checker tidligere indhold
                if (tidligereIndhold != null && matches) { //Hvis den har fejlet filteret tidligere, så er der ingen grund til at teste mere
                    matches = fad.getTidligereIndhold() == tidligereIndhold;
                }

                //Checker træsort
                if (træsort != null && matches) {
                    matches = fad.getTræsort() == træsort;
                }

                //Checker Lager
                if (lager != null && matches) {
                    matches = fad.getLager() == lager;
                }

                return matches;
            }
        };

        //Opretter listerne
        ArrayList<Fad> fad = getFad();
        ArrayList<Fad> fadEfterFilter = new ArrayList<Fad>();

        //Filtrere listen efter det givne predicate
        fad.stream().filter(fadFilter).forEach(fadElement -> fadEfterFilter.add(fadElement));

        return fadEfterFilter;
    }
}
