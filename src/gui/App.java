package gui;

import application.controller.Controller;
import application.model.*;
import javafx.application.Application;
import storage.Storage;

import java.time.LocalDate;
import java.util.HashMap;

public class App {
    public static void main(String[] args) {
        try {
            initStorage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Application.launch(StartWindow.class);
    }

    private static void initStorage() throws Exception {
        /*
        Laver en masse data til startData.
         */

        //Laver nogle medarbejdere
        Medarbejder snævar = new Adminstrator("Snævar Albertsson", "130676-1234", "SNA", "kode");
        Storage.addMedarbejder(snævar);
        Medarbejder mads = Controller.opretMedarbejder("Mads Medarbejder", "010203-4555", "MAM");
        Medarbejder thor = Controller.opretMedarbejder("Thor Testesen", "020304-6969", "TOT");

        //Laver nogle forhandlere
        Forhandler juan = Controller.opretForhandler("Juan Igleasas", "Catalonien", "Spanien");
        Forhandler donald = Controller.opretForhandler("Donald Duck", "Scotland", "UK");

        //Laver nogle marker
        Adresse markAdresse = new Adresse("3", "Nørre allé", "8000", "dk");
        Mark langdahlMark = new Mark("En meget fin Økologisk mark, som dyrkes af Lars Landmand", "Langdahl", markAdresse);
        Storage.addMark(langdahlMark);

        //Laver et malteri
        Adresse malteriAdresse = new Adresse("14", "Gyrupvej", "7752", "Danmark");
        Malteri thyMalteri = new Malteri("Thy Whisky Malteri", "Et stort malteri i Thy, som opereres af Thy Whisky.", malteriAdresse);
        Storage.addMalteri(thyMalteri);

        //Laver nogle maltBatches
        MaltBatch maltBatch1 = new MaltBatch(Kornsort.EVERGREEN, LocalDate.of(2020, 04, 01), thyMalteri, langdahlMark);
        Storage.addMaltbatch(maltBatch1);
        MaltBatch maltBatch2 = new MaltBatch(Kornsort.IRINA, LocalDate.of(2024, 04, 17), thyMalteri, langdahlMark);
        Storage.addMaltbatch(maltBatch2);
        MaltBatch maltBatch3 = new MaltBatch(Kornsort.STAIRWAY, LocalDate.of(2024, 05, 20), thyMalteri, langdahlMark);
        Storage.addMaltbatch(maltBatch3);


        //Laver to lagrer
        Adresse lagerAdresse = new Adresse("45", "Gadehej", "8700", "DK");
        Lager larsHal = Controller.opretLager("Lars' hal", lagerAdresse, 10, 3, 20);

        Adresse containerAdresse = new Adresse("12", "Gadehej", "8700", "DK");
        Lager containerLager = Controller.opretLager("Container", containerAdresse, 1, 3, 6);

        //Laver nogle fade
        Fad fad1 = Controller.opretFad(Træsort.QUERCUSALBA, juan, TidligereIndhold.SHERRY, 200, "");
        Fad fad2 = Controller.opretFad(Træsort.QUERCUSPATREA, juan, TidligereIndhold.BOURBON, 60, "");
        Fad fad3 = Controller.opretFad(Træsort.QUERCUSPATREA, juan, TidligereIndhold.BOURBON, 60, "");
        Fad fad4 = Controller.opretFad(Træsort.QUERCUSROBER, donald, TidligereIndhold.BOURBON, 90, "");
        Fad fad5 = Controller.opretFad(Træsort.QUERCUSROBER, donald, TidligereIndhold.BOURBON, 30, "");

        //Laver 3 makes, som er klar til tapning
        LocalDate destillatFærdig1 = LocalDate.of(2020, 11, 02);
        Destillat destillat1 = Controller.opretDestillat(LocalDate.of(2020, 02, 22), destillatFærdig1, 35, 45, RygningsType.TØRVRØGET, "", maltBatch1, mads);
        HashMap<Væske, Double> væskeOgLiter1 = new HashMap<>();
        væskeOgLiter1.put(destillat1, 45.00);
        Storage.addvæske(new Make(fad1, væskeOgLiter1, destillatFærdig1.plusDays(1)));

        LocalDate destillatFærdig2 = LocalDate.of(2020, 11, 02);
        Destillat destillat2 = Controller.opretDestillat(LocalDate.of(2020, 10, 30), LocalDate.of(2020, 11, 02), 60, 55, RygningsType.TØRVRØGET, "", maltBatch1, mads);
        HashMap<Væske, Double> væskeOgLiter2 = new HashMap<>();
        væskeOgLiter2.put(destillat1, 60.00);
        Storage.addvæske(new Make(fad2, væskeOgLiter1, destillatFærdig2.plusDays(1)));

        LocalDate destillatFærdig3 = LocalDate.of(2020, 11, 02);
        Destillat destillat3 = Controller.opretDestillat(LocalDate.of(2020, 11, 03), LocalDate.of(2020, 11, 05), 90, 45, RygningsType.IKKERØGET, "", maltBatch1, thor);
        HashMap<Væske, Double> væskeOgLiter3 = new HashMap<>();
        væskeOgLiter3.put(destillat1, 90.00);
        Storage.addvæske(new Make(fad3, væskeOgLiter1, destillatFærdig3.plusDays(1)));

        //Laver 1 nyt make
        Destillat destillat4 = Controller.opretDestillat(LocalDate.of(2024, 05, 17), LocalDate.of(2024, 05, 18), 90, 67, RygningsType.IKKERØGET, "", maltBatch2, thor);
        Destillat destillat5 = Controller.opretDestillat(LocalDate.of(2024, 05, 19), LocalDate.of(2024, 05, 20), 90, 67, RygningsType.IKKERØGET, "", maltBatch2, thor);
        HashMap<Væske, Double> væskeOgLiter4 = new HashMap<>();
        væskeOgLiter4.put(destillat4, 45.00);
        væskeOgLiter4.put(destillat5, 45.00);
        Controller.opretMake(fad4,væskeOgLiter4);

        //Laver et ekstra nyt destillat
        Storage.addvæske(new Destillat(LocalDate.now().minusDays(5), LocalDate.now(), 50, 60, RygningsType.TØRVRØGET, "", thor, maltBatch3));

        //Laver en tapningsVæske
        Destillat destillat6 = Controller.opretDestillat(LocalDate.now().minusYears(4), LocalDate.now().minusYears(4).plusDays(3), 50, 60, RygningsType.TØRVRØGET, "", maltBatch1, snævar);
        Fad fad = Controller.opretFad(Træsort.QUERCUSPATREA, juan, TidligereIndhold.SHERRY, 250, "");
        HashMap<Væske, Double> hashMap2 = new HashMap<>();
        hashMap2.put(destillat6, 50.00);
        Make make = new Make(fad, hashMap2, LocalDate.now().minusYears(4).plusDays(3));
        Storage.addvæske(make);
        try {
            Controller.opretTapningsVæske(fad, 58, 50);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Laver endnu tapningsVæske
        Destillat destillat7 = Controller.opretDestillat(LocalDate.now().minusYears(4), LocalDate.now().minusYears(4).plusDays(10), 35, 70, RygningsType.IKKERØGET, "En meget god blanding.", Storage.getMaltBatches().get(0), Storage.getMedarbejdere().get(0));
        Fad fad6 = Controller.opretFad(Træsort.QUERCUSALBA, Storage.getForhandlere().get(0), TidligereIndhold.BOURBON, 250, "");
        HashMap<Væske, Double> hashMap3 = new HashMap<>();
        hashMap3.put(destillat7, 35.00);
        Make make2 = new Make(fad2, hashMap3, LocalDate.now().minusYears(4).plusDays(10));
        Storage.addvæske(make2);
        try {
            Controller.opretTapningsVæske(fad2, 67.5, 35);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
