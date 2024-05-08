package gui;

import application.model.*;
import javafx.application.Application;
import storage.Storage;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        initStorage();
        Application.launch(StartWindow.class);
    }

    private static void initStorage() {
        Storage.addForhandler(new Forhandler("Juan Igleasas", "Catalonien", "Spanien"));
        Storage.addForhandler(new Forhandler("Donald Duck", "Scotland", "UK"));
        Storage.addMark(new Mark("En meget fin Økologisk mark, som dyrkes af Lars Landmand", "Langdahl", new Adresse("3","Nørre allé","8000","dk")));
        Storage.addMalteri(new Malteri("Thy Whisky Malteri", "Et stort malteri i Thy, som opereres af Thy Whisky.", new Adresse("14", "Gyrupvej", "7752", "Danmark")));
        Storage.addMaltbatch(new MaltBatch(Kornsort.EVERGREEN, 1, LocalDate.of(2024, 05, 03), Storage.getMalterier().get(0), Storage.getMarker().get(0)));
        Storage.addMaltbatch(new MaltBatch(Kornsort.IRINA, 2, LocalDate.of(2024, 04, 17), Storage.getMalterier().get(0), Storage.getMarker().get(0)));

    }
}
