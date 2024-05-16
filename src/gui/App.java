package gui;

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
        Storage.addFad(new Fad(Træsort.QUERCUSPATREA, "", TidligereIndhold.SHERRY, 250, Storage.getForhandlere().get(0)));
        Storage.addvæske(new Destillat(LocalDate.now().minusDays(5), LocalDate.now(), 50, 45, RygningsType.TØRVRØGET, "", Storage.getMedarbejdere().get(0), Storage.getMaltBatches().get(0)));
        HashMap<Væske,Double> hashMap = new HashMap<>();
        hashMap.put(Storage.getVæsker().get(0),10.00);
        Storage.addvæske(new Make(Storage.getFade().get(1), hashMap));
        Storage.addvæske(new Destillat(LocalDate.now().minusDays(5),LocalDate.now(),50,60,RygningsType.TØRVRØGET,"",Storage.getMedarbejdere().get(0),Storage.getMaltBatches().get(0)));

    }
}
