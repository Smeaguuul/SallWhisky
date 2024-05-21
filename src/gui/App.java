package gui;

import application.controller.Controller;
import application.model.*;
import javafx.application.Application;
import storage.Storage;

import java.time.LocalDate;
import java.util.HashMap;

public class App {
    public static void main(String[] args) {
        initStorage();
        Application.launch(StartWindow.class);
    }

    private static void initStorage() {
        Storage.addMedarbejder(new Adminstrator("Snævar Albertsson", "130676-1234", "SNA", "kode"));
        Storage.addMedarbejder(new Medarbejder("Mads Medarbejder", "010203-4555", "MAM"));
        Storage.addMedarbejder(new Medarbejder("Thor Testesen", "020304-6969", "TOT"));
        Storage.addForhandler(new Forhandler("Juan Igleasas", "Catalonien", "Spanien"));
        Storage.addForhandler(new Forhandler("Donald Duck", "Scotland", "UK"));
        Storage.addMark(new Mark("En meget fin Økologisk mark, som dyrkes af Lars Landmand", "Langdahl", new Adresse("3", "Nørre allé", "8000", "dk")));
        Storage.addMalteri(new Malteri("Thy Whisky Malteri", "Et stort malteri i Thy, som opereres af Thy Whisky.", new Adresse("14", "Gyrupvej", "7752", "Danmark")));
        Storage.addMaltbatch(new MaltBatch(Kornsort.EVERGREEN, 1, LocalDate.of(2024, 05, 03), Storage.getMalterier().get(0), Storage.getMarker().get(0)));
        Storage.addMaltbatch(new MaltBatch(Kornsort.IRINA, 2, LocalDate.of(2024, 04, 17), Storage.getMalterier().get(0), Storage.getMarker().get(0)));

        Storage.addFad(new Fad(Træsort.QUERCUSALBA, "", TidligereIndhold.SHERRY, 200, Storage.getForhandlere().get(0)));
        Storage.addFad(new Fad(Træsort.QUERCUSPATREA, "", TidligereIndhold.SHERRY, 240, Storage.getForhandlere().get(0)));

        //Laver et make, som har lagt på lager i 4 år
        Storage.addvæske(new Destillat(LocalDate.now().minusYears(4), LocalDate.now().minusYears(4).plusDays(3), 50, 45, RygningsType.TØRVRØGET, "", Storage.getMedarbejdere().get(0), Storage.getMaltBatches().get(0)));
        HashMap<Væske, Double> hashMap = new HashMap<>();
        hashMap.put(Storage.getVæsker().get(0), 10.00);
        Storage.addvæske(new Make(Storage.getFade().get(1), hashMap, LocalDate.now().minusYears(4).plusDays(3)));

        //Laver et ekstra nyt destillat
        Storage.addvæske(new Destillat(LocalDate.now().minusDays(5), LocalDate.now(), 50, 60, RygningsType.TØRVRØGET, "", Storage.getMedarbejdere().get(0), Storage.getMaltBatches().get(0)));

        //Laver en tapningsVæske
        Destillat destillat = Controller.opretDestillat(LocalDate.now().minusYears(4), LocalDate.now().minusYears(4).plusDays(3), 50, 60, RygningsType.TØRVRØGET, "", Storage.getMaltBatches().get(0), Storage.getMedarbejdere().get(0));
        Fad fad = Controller.opretFad(Træsort.QUERCUSPATREA, Storage.getForhandlere().get(0), TidligereIndhold.SHERRY, 255, "");
        HashMap<Væske, Double> hashMap2 = new HashMap<>();
        hashMap2.put(destillat, 50.00);
        Make make = new Make(fad, hashMap2, LocalDate.now().minusYears(4).plusDays(3));
        Storage.addvæske(make);
        try {
            Controller.opretTapningsVæske(fad, 58, 50);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Laver endnu tapningsVæske
        Destillat destillat2 = Controller.opretDestillat(LocalDate.now().minusYears(4), LocalDate.now().minusYears(4).plusDays(10), 35, 70, RygningsType.IKKERØGET, "En meget god blanding.", Storage.getMaltBatches().get(0), Storage.getMedarbejdere().get(0));
        Fad fad2 = Controller.opretFad(Træsort.QUERCUSALBA, Storage.getForhandlere().get(0), TidligereIndhold.BOURBON, 260, "");
        HashMap<Væske, Double> hashMap3 = new HashMap<>();
        hashMap3.put(destillat2, 35.00);
        Make make2 = new Make(fad2, hashMap3, LocalDate.now().minusYears(4).plusDays(10));
        Storage.addvæske(make2);
        try {
            Controller.opretTapningsVæske(fad2, 67.5, 35);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Laver et lager
        Adresse adresse = new Adresse("45","Gadehej","8700","DK");
        Storage.addLager(new Lager("Lars' hal",adresse,10,3,20));
    }
}
