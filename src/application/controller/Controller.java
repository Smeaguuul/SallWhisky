package application.controller;

import application.model.*;
import storage.Storage;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class Controller {
    public static Fad opretFad(Træsort træsort, Forhandler forhandler, TidligereIndhold tidligereIndhold, int literStørrelse, String bemærkning) throws IllegalArgumentException {
        if (literStørrelse <= 0) {
            throw new IllegalArgumentException("Liter størrelse skal være over 0");
        }
        if (træsort == null) {
            throw new NoSuchElementException("Ingen Træsort Valgt!");
        }
        if (forhandler == null) {
            throw new NoSuchElementException("Intet Tidligere Indhold Valgt!");
        }
        if (tidligereIndhold == null) {
            throw new NoSuchElementException("Ingen Forhandler Valgt!");
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
        if (rygningsType == null) {
            throw new IllegalArgumentException("Der skal vælges en rygningstype.");
        }
        Destillat destillat = new Destillat(startDato, slutDato, literVæske, alkoholProcent, rygningsType, kommentar, medarbejder, maltbatch);
        Storage.addvæske(destillat);
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

    public static Medarbejder fjernMedarbejder(Medarbejder medarbejder) {
        Storage.removeMedarbejder(medarbejder);
        return medarbejder;
    }

    public static void rediger(Medarbejder medarbejder, String navn, String cpr, String signatur) {
        medarbejder.setNavn(navn);
        medarbejder.setCpr(cpr);
        medarbejder.setSignatur(signatur);
    }

    public static Adminstrator forsøgLogin(int medarbejderNummer, String kodeord) throws Exception {
        Medarbejder medarbejder = Controller.getMedarbejder(medarbejderNummer);
        if (medarbejder instanceof Adminstrator && ((Adminstrator) medarbejder).verficerKode(kodeord)) {
            return (Adminstrator) medarbejder;
        } else {
            throw new LoginException("Forkert Login information.");
        }
    }


    public static Make opretMake(Fad fad, HashMap<Væske, Double> væskerOgLiter) {
        //Tilføjer fadets make til listen, hvis det har et
        try {
            Make fadMake = fad.getMake();
            væskerOgLiter.put(fadMake, fadMake.getNuværendeMængde());
        } catch (Exception e) {
        }

        //Checker om det er plads
        double antalLiter = 0;
        for (Double liter : væskerOgLiter.values()) {
            antalLiter += liter;
        }
        if (antalLiter > fad.getLiterStørrelse()) {
            throw new IllegalArgumentException("Der er ikke  plads i fadet.");
        }

        //Checker om der er tilstrækkelig af resterende væske fra de valgte væsker
        for (Map.Entry<Væske, Double> væskeDoubleEntry : væskerOgLiter.entrySet()) {
            if (væskeDoubleEntry.getKey().getNuværendeMængde() < væskeDoubleEntry.getValue()){
                throw new IllegalArgumentException("Der er ikke nok væske i de valgte væsker."); //TODO evt. specificere hvilket destillat/make det er
            }
        }

        //Trækker det brugte væske fra
        for (Map.Entry<Væske, Double> væskeDoubleEntry : væskerOgLiter.entrySet()) {
            væskeDoubleEntry.getKey().brugVæske(væskeDoubleEntry.getValue());
        }

        //Laver det nye make
        Make make = new Make(fad, væskerOgLiter);
        Storage.addvæske(make);

        //Returnere make
        return make;
    }
}
