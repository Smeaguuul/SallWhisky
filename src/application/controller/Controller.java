package application.controller;

import application.model.*;
import storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Controller {
    public static Fad opretFad(Træsort træsort, Forhandler forhandler, TidligereIndhold tidligereIndhold, int literStørrelse, String bemærkning) throws IllegalArgumentException {
        if (literStørrelse <= 0) {
            throw new IllegalArgumentException("Liter størrelse skal være over 0");
        }
        Fad fad = new Fad(træsort, bemærkning, tidligereIndhold, literStørrelse, forhandler);
        Storage.addFad(fad);
        return fad;
    }

    public static Destillat opretDestillat(LocalDate startDato, LocalDate slutDato, double literVæske, double alkoholProcent, RygningsType rygningsType, String kommentar, MaltBatch maltbatch, Medarbejder medarbejder) {
        if (literVæske <= 0) {
            throw new IllegalArgumentException("Litermængde skal være over 0.");
        }
        if (alkoholProcent < 40 || alkoholProcent > 100) {
            throw new IllegalArgumentException("Alkoholprocent skal være mellem 40 og 100.");
        }
        if (startDato.isAfter(slutDato) || startDato.isEqual(slutDato)) {
            throw new IllegalArgumentException("Startdato skal være før slutdato.");
        }
        Destillat destillat = new Destillat(startDato, slutDato, literVæske, alkoholProcent, rygningsType, kommentar, medarbejder, maltbatch);
        Storage.addDestillat(destillat);
        return destillat;
    }

    public static ArrayList<MaltBatch> getMaltBatches() {
        return Storage.getMaltBatches();
    }

    // Medarbejder
    public static Medarbejder opretMedarbejder(String navn, String cpr, String signatur) {
        Medarbejder med = new Medarbejder(navn, cpr, signatur);
        Storage.addMedarbejder(med);
        return med;
    }
    public static Medarbejder getMedarbejder(int medarbejderNummer) {
        Medarbejder medarbejder = null;

        //Kaster en error hvis medarbejder nummer ikke er gyldigt
        if (!(medarbejderNummer > 0)) {
            throw new IllegalArgumentException("Medarbejdernummer skal være positivt.");
        }

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

        //Kaster en error hvis ingen medarbejder findes
        if (medarbejder == null) {
            throw new NoSuchElementException("Medarbejder eksistere ikke.");
        }
        //Returnere medarbejder
        return medarbejder;
    }

    // Forhandler
    public static ArrayList<Forhandler> getForhandlere() {
        ArrayList<Forhandler> forhandlerer;
        forhandlerer = Storage.getForhandlere();
        return forhandlerer;
    }
    public static Forhandler opretForhandler(String navn, String region, String land) {
        Forhandler hand = new Forhandler(navn, region, land);
        Storage.addForhandler(hand);
        return hand;
    }
}
