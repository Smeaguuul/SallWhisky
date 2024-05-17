package gui.opretLagerPane;

import application.controller.Controller;
import application.model.Adresse;
import gui.motherClasses.InfoLabel;
import gui.motherClasses.MotherLabel;
import gui.motherClasses.MotherPane;
import gui.motherClasses.MotherTab;
import gui.tabs.OpretRedigerTab;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OpretLagerPane extends MotherPane {
    private MotherTab owner;
    private TextField lagerNavnTextfield = new TextField();
    private TextField landTextfield = new TextField();
    private TextField postnrTextfield = new TextField();
    private TextField gadeNavnTextfield = new TextField();
    private TextField gadeNrTextfield = new TextField();
    private TextField antalReolerTextfield = new TextField();
    private TextField hyldePrReolTextfield = new TextField();
    private TextField pladserPrHylde = new TextField();

    public OpretLagerPane(String s, OpretRedigerTab owner) {
        this.owner = owner;

        this.add(new MotherLabel("Opret Lager:"), 0, 0);
        this.add(new InfoLabel("Navn:"), 0, 1);
        this.add(lagerNavnTextfield, 1, 1);

        // Opretter vboxes med labels og texfields til adresse
        this.add(new InfoLabel("Adresse:"), 0, 2);
        VBox adresseLabelVbox = new VBox();
        adresseLabelVbox.setSpacing(10);
        adresseLabelVbox.getChildren().addAll(
                new InfoLabel("Land:"),
                new InfoLabel("Postnr:"),
                new InfoLabel("Gade navn:"),
                new InfoLabel("Gade nr:"));
        this.add(adresseLabelVbox, 0, 3);

        VBox adresseTextfieldVbox = new VBox();
        adresseTextfieldVbox.setSpacing(10);
        adresseTextfieldVbox.getChildren().addAll(
                landTextfield,
                postnrTextfield,
                gadeNavnTextfield,
                gadeNrTextfield);
        this.add(adresseTextfieldVbox, 1, 3);

        // Opretter vboxes med labels og textfields til dimensionerne i lageret
        this.add(new InfoLabel("Dimensioner:"), 0, 4);

        VBox dimensionLabelVbox = new VBox();
        dimensionLabelVbox.setSpacing(10);
        dimensionLabelVbox.getChildren().addAll(
                new InfoLabel("Antal reoler:"),
                new InfoLabel("Hylder pr. reol:"),
                new InfoLabel("Pladser pr. hylder:"));
        this.add(dimensionLabelVbox, 0, 5);

        VBox dimensionTextfieldVbox = new VBox();
        dimensionTextfieldVbox.setSpacing(10);
        dimensionTextfieldVbox.getChildren().addAll(
                antalReolerTextfield,
                hyldePrReolTextfield,
                pladserPrHylde);
        this.add(dimensionTextfieldVbox, 1, 5);

        // Opretter buttons annuler og opret, samt vbox der holder på de 2
        Button annullerButton = new Button("Annuller");
        annullerButton.setOnAction(e -> this.owner.drawDefault());
        Button opretButton = new Button("Opret");
        opretButton.setOnAction(e -> opretLager());
        HBox buttonHbox = new HBox();
        buttonHbox.setSpacing(10);
        buttonHbox.getChildren().addAll(annullerButton,opretButton);
        this.add(buttonHbox,1,6);

        // Sørger for at visse texfields kun kan have tal som input
        hyldePrReolTextfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    hyldePrReolTextfield.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        pladserPrHylde.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    pladserPrHylde.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        antalReolerTextfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    antalReolerTextfield.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        postnrTextfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    postnrTextfield.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    // Tager værdierne fra texfields og opretter en lager med en adresse
    private void opretLager() {
        if (!erDerTommeFelter()) {
            Adresse adresse = new Adresse(
                    gadeNrTextfield.getText(),
                    gadeNavnTextfield.getText(),
                    postnrTextfield.getText(),
                    landTextfield.getText()
            );
            String navn = lagerNavnTextfield.getText();
            int reolAntal = Integer.parseInt(antalReolerTextfield.getText());
            int højde = Integer.parseInt(hyldePrReolTextfield.getText());
            int placeringPrhylde = Integer.parseInt(pladserPrHylde.getText());
            Controller.opretLager(navn, adresse, reolAntal, højde, placeringPrhylde);

            lagerNavnTextfield.clear();
            landTextfield.clear();
            postnrTextfield.clear();
            gadeNavnTextfield.clear();
            gadeNrTextfield.clear();
            antalReolerTextfield.clear();
            hyldePrReolTextfield.clear();
            pladserPrHylde.clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Udfyld venligst de tomme felter");
            alert.setContentText("Udfyld venlist de tomme felter");
            alert.showAndWait();
        }
    }

    // Checker om der er et tomt textfield
    public boolean erDerTommeFelter(){
        if (lagerNavnTextfield.getText().isEmpty() ||
        landTextfield.getText().isEmpty() ||
        postnrTextfield.getText().isEmpty() ||
        gadeNavnTextfield.getText().isEmpty() ||
        gadeNrTextfield.getText().isEmpty() ||
        antalReolerTextfield.getText().isEmpty() ||
        hyldePrReolTextfield.getText().isEmpty() ||
        pladserPrHylde.getText().isEmpty()){
            return true;
        } else return false;
    }


}
