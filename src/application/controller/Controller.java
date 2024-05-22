package application.controller;

import application.model.*;
import storage.Storage;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

public class Controller {
    public static Fad opretFad(Træsort træsort, Forhandler forhandler, TidligereIndhold tidligereIndhold, int literStørrelse, String bemærkning) throws IllegalArgumentException {
        opretFadCheck(træsort, forhandler, tidligereIndhold, literStørrelse);
        Fad fad = new Fad(træsort, bemærkning, tidligereIndhold, literStørrelse, forhandler);
        Storage.addFad(fad);
        return fad;
    }

    public static void opretFadCheck(Træsort træsort, Forhandler forhandler, TidligereIndhold tidligereIndhold, int literStørrelse) {
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
    }

    public static Destillat opretDestillat(LocalDate startDato, LocalDate slutDato, double literVæske, double alkoholProcent, RygningsType rygningsType, String kommentar, MaltBatch maltbatch, Medarbejder medarbejder) throws Exception {
        opretDestillatCheck(startDato, slutDato, literVæske, alkoholProcent, rygningsType);
        Destillat destillat = new Destillat(startDato, slutDato, literVæske, alkoholProcent, rygningsType, kommentar, medarbejder, maltbatch);
        Storage.addvæske(destillat);
        return destillat;
    }

    public static void opretDestillatCheck(LocalDate startDato, LocalDate slutDato, double literVæske, double alkoholProcent, RygningsType rygningsType) throws Exception {
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

    public static Medarbejder getMedarbejder(int medarbejderNummer) throws Exception {
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


    public static Make opretMake(Fad fad, HashMap<Væske, Double> væskerOgLiter) throws Exception {
        //Tilføjer fadets make til listen, hvis det har et
        try {
            Make fadMake = fad.getMake();
            //Checker lige for en sikkerhedsskyld om den allerede er inkluderet i listen
            if (!væskerOgLiter.containsKey(fadMake)) {
                væskerOgLiter.put(fadMake, fadMake.getNuværendeMængde());
            }
        } catch (Exception e){}


        //Checker om det er et gyldigt input
        opretMakeCheck(fad, væskerOgLiter);


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

    public static void opretMakeCheck(Fad fad, HashMap<Væske, Double> væskerOgLiter) throws Exception {
        //Checker om fad er null
        if (fad == null) {
            throw new IllegalArgumentException("Du skal medgive mindst en væske");
        }
        //Checker om der faktisk er medgivet >0 væsker
        if (væskerOgLiter.size() == 0) {
            throw new IllegalArgumentException("Du skal medgive mindst en væske");
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
    }

    public static ArrayList<Fad> getModneFade() {
        ArrayList<Fad> fade = Storage.getFade();
        ArrayList<Fad> modneFade = new ArrayList<>();

        for (Fad fad : fade) {
            try {
                if (fad.hasMake() && fad.getMake().getNuværendeMængde() > 0) { //TODO Sorter fadene
                    modneFade.add(fad);
                }
            } catch (Exception e) {
            }
        }

        return modneFade; //TODO Eventuelt smid en error så vi i GUI kan sige at der ikker er nogle klar
    }

    public static TapningsVæske opretTapningsVæske(Fad fad, double alkoholprocent, double mængde) throws Exception {
        //Får fat i det make vi skal tappe
        Make make;
        make = fad.getMake();

        //Tester om det er gyldige tal vi får
        opretTapningsVæskeCheck(fad, alkoholprocent, mængde);

        //Opretter tapningsvæske
        TapningsVæske tapningsVæske = new TapningsVæske(alkoholprocent, mængde, make);

        //Tilføjer det til Storage
        Storage.addTapningsVæske(tapningsVæske);

        return tapningsVæske;
    }

    public static void opretTapningsVæskeCheck(Fad fad, double alkoholprocent, double mængde) throws Exception {
        Make make;
        make = fad.getMake();
        if (alkoholprocent < 40 || alkoholprocent > 100) {
            throw new IllegalArgumentException("Alkoholprocent skal være mellem 40 og 100.");
        }
        if (mængde > make.getNuværendeMængde()) {
            throw new IllegalArgumentException("Ikke nok resterende væske i Make.");
        }
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
        opretWhiskyCheck(tapningsVæsker, literVand);

        double totalMængdeTapningsVæske = 0;
        for (TapningsVæske tapningsVæske : tapningsVæsker) {
            totalMængdeTapningsVæske += tapningsVæske.getMængde();
        }
        double fortyndingsFaktor = literVand / totalMængdeTapningsVæske;

        Whisky whisky = new Whisky(fortyndingsFaktor, kommentar, new ArrayList<>(tapningsVæsker));

        Storage.addWhisky(whisky);

        return whisky;
    }

    public static void opretWhiskyCheck(List<TapningsVæske> tapningsVæsker, int literVand) {
        double estimeretAlkoholprocent = udregnAlkoholProcent(new ArrayList<>(tapningsVæsker), literVand);
        if (estimeretAlkoholprocent < 40 || estimeretAlkoholprocent > 100) {
            throw new IllegalArgumentException("En whisky skal have en alkoholprocent over 40");
        }
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
        Lager lager = new Lager(navn, adresse, reolAntal, højde, placeringerPrHylde);

        Storage.addLager(lager);

        return lager;
    }

    public static List<Fad> getFadMedLokation() {
        List<Fad> fade;
        fade = Storage.getFade();
        ArrayList<Fad> fadeMedLokation = new ArrayList<>();

        fade.stream().filter(Predicate.not(fad -> !fad.harLagerlokation())).forEach(fad -> fadeMedLokation.add(fad));

        return fadeMedLokation;
    }

    public static List<Fad> getFadUdenLokation() {
        List<Fad> fadMedLokation = getFadMedLokation();
        List<Fad> fadeUdenLokation = Storage.getFade();
        fadeUdenLokation.removeAll(fadMedLokation);

        return fadeUdenLokation;
    }

    public static ArrayList<Lager> getLagrer() {
        return Storage.getLagrer();
    }

    public static void setLagerLokation(Fad fad, Lager lager, int reolNummer, int højdeNummer, int placeringsnummer) throws Exception {
        //TODO lav et check om det faktisk er inde for begrænsingerne og sørg for at den tilsvarende lokation i lageret bliver registreret som brugt
        //TODO konverter mellem 0-9 til 1-10 f.eks. Vi tæller fra 1 i GUI i ikke 0, så index passer ikke.
        try {
            fad.setLagerlokation(lager, reolNummer, højdeNummer, placeringsnummer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Whisky> getWhisky() {
        return Storage.getWhiskyer();
    }

    // Opretter et whiskyprodukt og tilføjer det tioll storage
    public static WhiskyProdukt haeldPaaFlaske(Whisky whisky, int flaskeStoerrelse) {
        WhiskyProdukt whiskyProdukt = new WhiskyProdukt(whisky,flaskeStoerrelse);
        Storage.addWhiskyProdukt(whiskyProdukt);
        return whiskyProdukt;
    }
    // Finder alle whiskies fra storage som er ubrugte
    public static ArrayList<Whisky> getUbrugteWhiskyer(){
        ArrayList<Whisky> whiskies = Storage.getWhiskyer();
        ArrayList<Whisky> ubrugtWhiskies = new ArrayList<>();
        for (Whisky whiskey : whiskies) {
            if (!whiskey.isBrugt()){
                ubrugtWhiskies.add(whiskey);
            }
        }
        return ubrugtWhiskies;
    }

    public static ArrayList<Destillat> getDestillater() {
        ArrayList<Destillat> destlliatArraylist = new ArrayList<>();

        //Løber listen igennem, og tilføjer alle destillater til listen
        for (int i = 0; i < Storage.getVæsker().size(); i++) {
            if (Storage.getVæsker().get(i) instanceof Destillat && Storage.getVæsker().get(i).getNuværendeMængde() > 0) {
                Destillat destillat = (Destillat) Storage.getVæsker().get(i);
                destlliatArraylist.add(destillat);
            }
        }

        return destlliatArraylist;
    }

    public static ArrayList<Make> getMakes() {
        ArrayList<Make> makeArrayList = new ArrayList<>();

        //Løber listen igennem, og tilføjer alle makes til listen
        for (int i = 0; i < Storage.getVæsker().size(); i++) {
            if (Storage.getVæsker().get(i) instanceof Make && Storage.getVæsker().get(i).getNuværendeMængde() > 0) {
                Make make = (Make) Storage.getVæsker().get(i);
                makeArrayList.add(make);
            }
        }

        return makeArrayList;
    }
}
