package application.controller;

import application.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import storage.Storage;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

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
        try { //TODO ryd op her
            Make fadMake = fad.getMake();
            //Checker lige for en sikkerhedsskyld om den allerede er inkluderet i listen
            if (!væskerOgLiter.containsKey(fadMake)) {
                væskerOgLiter.put(fadMake, fadMake.getNuværendeMængde());

            }
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
            if (væskeDoubleEntry.getKey().getNuværendeMængde() < væskeDoubleEntry.getValue()) {
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

    public static ArrayList<Fad> getModneFade() {
        ArrayList<Fad> fade = Storage.getFade();
        ArrayList<Fad> modneFade = new ArrayList<>();

        for (Fad fad : fade) {
            try {
                if (fad.hasMake() && fad.getMake().getNuværendeMængde() > 0) {
                    modneFade.add(fad);
                }
            } catch (Exception e) {}
        }

        return modneFade; //TODO Eventuelt smid en error så vi i GUI kan sige at der ikker er nogle klar
    }

    public static TapningsVæske opretTapningsVæske(Fad fad, double alkoholprocent, double mængde) throws Exception {
        //Får fat i det make vi skal tappe
        Make make;
        make = fad.getMake();


        //Tester om det er gyldige tal vi får
        if (alkoholprocent < 40 || alkoholprocent > 100) {
            throw new IllegalArgumentException("Alkoholprocent skal være mellem 40 og 100.");
        }
        if (mængde > make.getNuværendeMængde()) {
            throw new IllegalArgumentException("Ikke nok resterende væske i Make.");
        }

        //Opretter tapningsvæske
        TapningsVæske tapningsVæske = new TapningsVæske(alkoholprocent, mængde, make);

        //Tilføjer det til Storage
        Storage.addTapningsVæske(tapningsVæske);

        return tapningsVæske;
    }

    /*
    Returnere alle de tapningsvæsker, som ikke er brugt
     */
    public static ArrayList<TapningsVæske> getTapningsVæskerTilWhisky() {
        ArrayList<TapningsVæske> tapningsVæsker = Storage.getTapningsVæsker();
        ArrayList<TapningsVæske> tapningsVæskerMedResterendeVæske = new ArrayList<>();

        //Filtrere listen efter ikke brugte tapninger
        tapningsVæsker.stream().filter(Predicate.not(tapningsVæske -> tapningsVæske.isErBrugt())).forEach(tapningsVæske -> tapningsVæskerMedResterendeVæske.add(tapningsVæske));

        return tapningsVæskerMedResterendeVæske;
    }

    public static Whisky opretWhisky(List<TapningsVæske> tapningsVæsker, int literVand, String kommentar) throws Exception {
        double estimeretAlkoholprocent = udregnAlkoholProcent(new ArrayList<>(tapningsVæsker), literVand);
        if (estimeretAlkoholprocent < 40 || estimeretAlkoholprocent > 100) {
            throw new IllegalArgumentException("En whisky skal have en alkoholprocent over 40");
        }

        double totalMængdeTapningsVæske = 0;
        for (TapningsVæske tapningsVæske : tapningsVæsker) {
            totalMængdeTapningsVæske += tapningsVæske.getMængde();
        }
        double fortyndingsFaktor = literVand / totalMængdeTapningsVæske;

        Whisky whisky = new Whisky(fortyndingsFaktor, kommentar, new ArrayList<>(tapningsVæsker));

        return whisky;
    }

    public static double udregnAlkoholProcent(ArrayList<TapningsVæske> tapningsVæsker, double literVandTilFortynding) {
        //Findet total antal liter
        double totalLiter = 0;
        for (TapningsVæske tapningsVæske : tapningsVæsker) {
            totalLiter += tapningsVæske.getMængde();
        }
        totalLiter += literVandTilFortynding;

        //Udregner procent alkohol i en vægtede udregning
        double estimeretAlkoholProcent = 0;
        for (TapningsVæske tapningsVæske : tapningsVæsker) {
            estimeretAlkoholProcent += tapningsVæske.getAlkoholprocent() * (tapningsVæske.getMængde() / totalLiter);
        }

        return estimeretAlkoholProcent;
    }

    public static Lager opretLager(String navn, Adresse adresse, int reolAntal, int højde, int placeringerPrHylde) {
        Lager lager = new Lager(navn, adresse, reolAntal, højde,placeringerPrHylde);

        Storage.addLager(lager);

        return lager;
    }

    public static void textFieldFilter(TextField text) {
        text.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    text.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
}
