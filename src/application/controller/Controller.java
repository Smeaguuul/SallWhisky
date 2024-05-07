package application.controller;

import application.model.*;
import storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {
    static public Medarbejder getMedarbejder(int medarbejderNummer) {
        Medarbejder medarbejder = null;

        //Får fat i alle medarbejdere i Storage
        ArrayList<Medarbejder> medarbejdere = Storage.getMedarbejdere();
        boolean found = false;
        int index = 0;

        //Løber listen igennem, for at finde den ønskede medarbejder
        while (!found && index < medarbejdere.size()) {
            Medarbejder kandidat = medarbejdere.get(index);
            if (kandidat.getNummer() == medarbejderNummer) {
                medarbejder = kandidat;
                found = true;
            } else {
                index++;
            }
        }

        //Returnere medarbejder
        return medarbejder;
    }

    static public ArrayList<Forhandler> getForhandlere() {
        ArrayList<Forhandler> forhandlerer;
        forhandlerer = Storage.getForhandlere();
        return forhandlerer;
    }

    static public Fad opretFad(Træsort træsort, Forhandler forhandler, TidligereIndhold tidligereIndhold, int literStørrelse, String bemærkning) {
        Fad fad = new Fad(træsort, bemærkning, tidligereIndhold, literStørrelse, forhandler);
        Storage.addFad(fad);
        return fad;
    }

    static public Destillat opretDestillat(LocalDate startDato, LocalDate slutDato, int literVæske, double alkoholProcent, RygningsType rygningsType, String kommentar, MaltBatch maltbatch, Medarbejder medarbejder){
        Destillat destillat = new Destillat(startDato, slutDato, literVæske, alkoholProcent, rygningsType, kommentar, medarbejder, maltbatch);
        Storage.addDestillat(destillat);
        return destillat;
    }

    public static ArrayList<MaltBatch> getMaltBatches() {
        return Storage.getMaltBatches();
    }
}
