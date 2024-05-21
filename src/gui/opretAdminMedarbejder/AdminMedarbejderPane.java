package gui.opretAdminMedarbejder;

import application.controller.Controller;
import application.model.Medarbejder;
import gui.motherClasses.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import storage.Storage;

public class AdminMedarbejderPane extends MotherPaneWithImageBackground {
    private ListView<Medarbejder> medarbejderListView = new ListView();
    private TextField navnOpretTextfield = new TextField();
    private TextField cprOpretTextfield = new TextField();
    private TextField signaturOpretTexfield = new TextField();
    private TextField navnRedigerTextfield = new TextField();
    private TextField cprRedigerTextfield = new TextField();
    private TextField signaturRedigerTextfield = new TextField();

    public AdminMedarbejderPane(String title, MotherTab owner) {
        super("/gui/images/mark.jpg", owner);

        // Se og slet medarbejdere. Opretter Label, sætter content i listview, og opretter en knap til slet.
        InfoLabel medArbejderListeLabel = new InfoLabel("Medarbejder liste:");
        resetView();
        Button sletButton = new MotherButton("Slet medarbejder");
        sletButton.setOnAction(e -> sletMedarbejder());
        centralPane.addRow(0, medArbejderListeLabel, medarbejderListView, sletButton);


        // Rediger medarbejder labels i VBox
        InfoLabel redigerMedarbejder = new InfoLabel("Valgt medarbejder:");
        centralPane.add(redigerMedarbejder, 1, 0, 2, 1);
        VBox redigerVboxLabels = new VBox();
        redigerVboxLabels.getChildren().addAll(new InfoLabel("Navn:"), new InfoLabel("Cpr:"), new InfoLabel("Signatur:"));
        redigerVboxLabels.setSpacing(30);
        centralPane.add(redigerVboxLabels, 1, 1);

        //Rediger Medarbejder textfields i VBox
        VBox redigerVboxTextfields = new VBox();
        redigerVboxTextfields.getChildren().addAll(navnRedigerTextfield, cprRedigerTextfield, signaturRedigerTextfield);
        redigerVboxTextfields.setSpacing(30);
        centralPane.add(redigerVboxTextfields, 2, 1);

        Button redigerButton = new MotherButton("Rediger medarbejder");
        redigerButton.setOnAction(e -> redigerMedarbejder());
        centralPane.add(redigerButton, 1, 2);

        // Indsætter værdier fra valgte medarbejder til textfields i rediger medarbejder
        medarbejderListView.getSelectionModel().selectedIndexProperty().addListener(observable -> {
            try {
                navnRedigerTextfield.setText(medarbejderListView.getSelectionModel().getSelectedItem().getNavn());
                cprRedigerTextfield.setText(medarbejderListView.getSelectionModel().getSelectedItem().getCpr());
                signaturRedigerTextfield.setText(medarbejderListView.getSelectionModel().getSelectedItem().getSignatur());
            } catch (Exception e) {
            }
        });


        // Opret medarbejder titel
        centralPane.add(new InfoLabel("Opret medarbejder:"), 3, 0, 2, 1);

        //Opret medarbejder labels
        VBox opretVboxLabels = new VBox();
        opretVboxLabels.getChildren().addAll(new InfoLabel("Navn:"), new InfoLabel("Cpr:"), new InfoLabel("Signatur:"));
        opretVboxLabels.setSpacing(30);

        //Opret medarbejder textfields
        VBox opretVboxTextfields = new VBox();
        opretVboxTextfields.getChildren().addAll(navnOpretTextfield, cprOpretTextfield, signaturOpretTexfield);
        opretVboxTextfields.setSpacing(30);
        centralPane.addColumn(1, opretVboxLabels, opretVboxTextfields);

        //Opret medarbejderButton
        Button opretButton = new MotherButton("Opret medarbejder");
        opretButton.setOnAction(e -> opretMedarbejder());
        centralPane.add(opretButton, 3, 2);

        // Knap for at komme tilbage til start
        Button tilForside = new MotherButton("Til forsiden");
        tilForside.setOnAction(e -> this.owner.drawDefault());
        centralPane.add(tilForside, 4, 2);

        //Tilføjer centralPane til ydre pane
        this.add(centralPane, 0, 0);
    }

    // Metode til "opret medarbejder" knappen. Opretter en medarbejder
    private void opretMedarbejder() {
        //Checker om man mangle at intaste noget
        if (navnOpretTextfield.getText().isEmpty() || cprOpretTextfield.getText().isEmpty() || signaturOpretTexfield.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Udfyld venligst de tomme felter under Opret medarbejder");
            alert.showAndWait();
        } else {
            String navn = navnOpretTextfield.getText().trim();
            String cpr = cprOpretTextfield.getText().trim();
            String signatur = signaturOpretTexfield.getText().trim();
            Controller.opretMedarbejder(navn, cpr, signatur);
            resetView();
        }
    }

    private void resetView() {
        medarbejderListView.getItems().setAll(Storage.getMedarbejdere());
        navnOpretTextfield.clear();
        cprOpretTextfield.clear();
        signaturOpretTexfield.clear();
    }

    // Metode til "rediger medarbejder" knappen. Ændre info omkring en medarbejder
    private void redigerMedarbejder() {
        if (medarbejderListView.getSelectionModel().isEmpty()) {
            alarm();
        } else {
            String navn = navnRedigerTextfield.getText().trim();
            String cpr = cprRedigerTextfield.getText().trim();
            String signatur = signaturRedigerTextfield.getText().trim();
            Medarbejder medarbejder = medarbejderListView.getSelectionModel().getSelectedItem();
            Controller.rediger(medarbejder, navn, cpr, signatur);
            resetView();
        }
    }

    // Metode til "slet medarbejder" knappen. Fjerner en medarbejder fra systemet
    private void sletMedarbejder() {
        if (medarbejderListView.getSelectionModel().isEmpty()) {
            alarm();
        } else {
            Controller.fjernMedarbejder(medarbejderListView.getSelectionModel().getSelectedItem());
            resetView();
        }
    }

    // Alert der fortæller man skal vælge en medarbejder
    private void alarm() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Vælg venligst en medarbejder.");
        alert.setContentText("Vælg en medarbejder fra listen.");
        alert.showAndWait();
    }


}
