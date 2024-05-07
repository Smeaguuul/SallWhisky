package gui;

import application.controller.Controller;
import application.model.Forhandler;
import javafx.application.Application;
import storage.Storage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        initStorage();
        Application.launch(StartWindow.class);
    }

    private static void initStorage() {
        Storage.addForhandler(new Forhandler("Juan Igleasas", "Catalonien", "Spanien"));
        Storage.addForhandler(new Forhandler("Donald Duck", "Scotland", "UK"));

    }
}
