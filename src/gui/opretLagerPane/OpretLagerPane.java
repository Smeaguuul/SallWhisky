package gui.opretLagerPane;

import application.controller.Controller;
import application.model.Adresse;
import gui.motherClasses.*;
import gui.tabs.OpretRedigerTab;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OpretLagerPane extends MotherPaneWithImageBackground {
    private TextField lagerNavnTextfield = new TextField();
    private TextField landTextfield = new TextField();
    private TextField postnrTextfield = new TextField();
    private TextField gadeNavnTextfield = new TextField();
    private TextField gadeNrTextfield = new TextField();
    private TextField antalReolerTextfield = new NumberTextField();
    private TextField hyldePrReolTextfield = new NumberTextField();
    private TextField pladserPrHylde = new NumberTextField();

    public OpretLagerPane(String s, OpretRedigerTab owner) {
        super("/gui/images/mark.jpg", owner);

        centralPane.add(new MotherLabel("Opret Lager:"), 0, 0, 2, 1);
        centralPane.add(new InfoLabel("Navn:"), 0, 1);
        centralPane.add(lagerNavnTextfield, 1, 1);

        // Opretter vbox med labels til adresse
        centralPane.add(new InfoLabel("Adresse:"), 0, 2);
        VBox adresseLabelVbox = new VBox();
        adresseLabelVbox.setSpacing(10);
        adresseLabelVbox.getChildren().addAll(new InfoLabel("Land:"), new InfoLabel("Postnr:"), new InfoLabel("Gade navn:"), new InfoLabel("Gade nr:"));
        centralPane.add(adresseLabelVbox, 0, 3);

        // Opretter vbox med texfields til adresse
        VBox adresseTextfieldVbox = new VBox();
        adresseTextfieldVbox.setSpacing(10);
        adresseTextfieldVbox.getChildren().addAll(landTextfield, postnrTextfield, gadeNavnTextfield, gadeNrTextfield);
        centralPane.add(adresseTextfieldVbox, 1, 3);

        // Opretter vboxes med labels og textfields til dimensionerne i lageret
        centralPane.add(new InfoLabel("Dimensioner:"), 0, 4);

        VBox dimensionLabelVbox = new VBox();
        dimensionLabelVbox.setSpacing(10);
        dimensionLabelVbox.getChildren().addAll(new InfoLabel("Antal reoler:"), new InfoLabel("Hylder pr. reol:"), new InfoLabel("Pladser pr. hylder:"));
        centralPane.add(dimensionLabelVbox, 0, 5);

        VBox dimensionTextfieldVbox = new VBox();
        dimensionTextfieldVbox.setSpacing(10);
        dimensionTextfieldVbox.getChildren().addAll(antalReolerTextfield, hyldePrReolTextfield, pladserPrHylde);
        centralPane.add(dimensionTextfieldVbox, 1, 5);

        // Opretter buttons annuler og opret, samt vbox der holder på de 2
        Button annullerButton = new Button("Annuller");
        annullerButton.setOnAction(e -> this.owner.drawDefault());
        Button opretButton = new Button("Opret");
        opretButton.setOnAction(e -> {
            //Hvis det lykkedes at oprette et lager, så tegner vi default billede for owner
            if (opretLager()) {
                this.owner.drawDefault();
            };
        });
        HBox buttonHbox = new HBox();
        buttonHbox.setSpacing(10);
        buttonHbox.getChildren().addAll(annullerButton, opretButton);
        centralPane.add(buttonHbox, 1, 6);

        //Tilføjer centralPane til det ydre pane
        this.add(centralPane, 0, 0);
    }

    // Tager værdierne fra texfields og opretter en lager med en adresse
    private boolean opretLager() {
        Boolean lykkedes = false;
        if (!erDerTommeFelter()) {
            //Opretter en adresse med den givne information
            Adresse adresse = new Adresse(gadeNrTextfield.getText(), gadeNavnTextfield.getText(), postnrTextfield.getText(), landTextfield.getText());
            //Gemmer de andre værdier
            String navn = lagerNavnTextfield.getText();
            int reolAntal = Integer.parseInt(antalReolerTextfield.getText());
            int højde = Integer.parseInt(hyldePrReolTextfield.getText());
            int placeringPrhylde = Integer.parseInt(pladserPrHylde.getText());
            //Kalder controller metoden
            Controller.opretLager(navn, adresse, reolAntal, højde, placeringPrhylde);

            lykkedes = true;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Udfyld venligst de tomme felter");
            alert.setContentText("Udfyld venlist de tomme felter");
            alert.showAndWait();
        }
        return lykkedes;
    }

    // Checker om der er et tomt textfield
    public boolean erDerTommeFelter() {
        if (lagerNavnTextfield.getText().isEmpty() || landTextfield.getText().isEmpty() || postnrTextfield.getText().isEmpty() ||
                gadeNavnTextfield.getText().isEmpty() || gadeNrTextfield.getText().isEmpty() || antalReolerTextfield.getText().isEmpty() ||
                hyldePrReolTextfield.getText().isEmpty() || pladserPrHylde.getText().isEmpty()) {
            return true;
        } else return false;
    }


}
