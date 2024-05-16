package gui.opretAdminMedarbejder;

import application.controller.Controller;
import application.model.Medarbejder;
import gui.motherClasses.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class AdminLoginPane extends MotherPaneWithImageBackground {
    private final MotherTab owner;
    private TextField nummerTextField = new TextField();
    private Medarbejder aktuelMedarbejder;
    private TextField kodeTextField = new TextField();

    public AdminLoginPane(String title, MotherTab owner) {
        super("/gui/images/mark.jpg");
        this.owner = owner;

        //Titel
        GridPane centralPane = new CentralPane();
        MotherLabel instructionLabel = new MotherLabel("Indtast venlist medarbejdernummer.");
        centralPane.add(instructionLabel, 0, 0, 2, 1);
        this.add(centralPane, 0, 0);

        //HBox til nummer checkning
        HBox nummerHBox = new HBox();
        nummerHBox.setSpacing(20);

        // Label
        InfoLabel medarbejdernummerLabel = new InfoLabel("Medarbejdernummer: ");
        nummerHBox.getChildren().add(medarbejdernummerLabel);

        // Text Field
        nummerTextField.setMaxWidth(50);
        nummerTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*"))
                    nummerTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        nummerHBox.getChildren().add(nummerTextField);

        //HBox til kode checkning
        HBox kodeHBox = new HBox();
        kodeHBox.setSpacing(20);

        InfoLabel kodeLabel = new InfoLabel("Kodeord: ");
        kodeHBox.getChildren().add(kodeLabel);

        kodeTextField.setMaxWidth(50);
        kodeHBox.getChildren().add(kodeTextField);
        centralPane.add(nummerHBox, 0, 1);
        centralPane.add(kodeHBox, 0, 2);

        //Laver en HBox til login og annuller knapper
        HBox buttonHBox = new HBox();

        //Laver knapperne
        MotherButton annullerButton = new MotherButton("Annuller");
        annullerButton.setOnAction(e -> owner.drawDefault());
        buttonHBox.getChildren().add(annullerButton);

        MotherButton loginButton = new MotherButton("Log ind");
        loginButton.setOnAction(e -> checkLogin());
        buttonHBox.getChildren().add(loginButton);
        centralPane.add(buttonHBox, 0, 3);
    }
    private void checkLogin() {
        try {
            //Kaster en error hvis informationerne ikke passe
            aktuelMedarbejder = Controller.forsøgLogin(Integer.parseInt(nummerTextField.getText()), kodeTextField.getText());
            openMedarbejderAdminPane();
        } catch (Exception e) {
            showLoginAlert(e);
            clearTextFields();
        }
    }
    private void clearTextFields() {
        this.kodeTextField.clear();
        this.nummerTextField.clear();
    }
    private void openMedarbejderAdminPane() {
        MotherPane adminMedarbejderPane = new AdminMedarbejderPane("Adminstrer Medarbejder", this.owner);
        owner.setContent(adminMedarbejderPane);
    }
    private void showLoginAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Ops! Ugyldig kode eller id.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}