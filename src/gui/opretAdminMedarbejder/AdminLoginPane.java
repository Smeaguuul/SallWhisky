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
    private TextField nummerTextField;
    private Medarbejder aktuelMedarbejder;
    private TextField kodeTextField;

    public AdminLoginPane(String title, MotherTab owner) {
        super("/gui/images/mark.jpg", owner);

        //Titel
        MotherLabel instructionLabel = new MotherLabel("Indtast venlist medarbejdernummer.");
        centralPane.add(instructionLabel, 0, 0, 2, 1);

        // Label til medarbejdernummer
        InfoLabel medarbejdernummerLabel = new InfoLabel("Medarbejdernummer: ");

        // Text Field til medarbejdernummer
        this.nummerTextField = new NumberTextField();
        nummerTextField.setMaxWidth(50);

        //HBox til nummer checkning
        HBox nummerHBox = new HBox();
        nummerHBox.setSpacing(20);
        nummerHBox.getChildren().addAll(medarbejdernummerLabel, nummerTextField);
        centralPane.add(nummerHBox, 0, 1);

        //Label og TextField til kodeord
        InfoLabel kodeLabel = new InfoLabel("Kodeord: ");
        kodeTextField  = new TextField();
        kodeTextField.setMaxWidth(50);

        //HBox til kodeord
        HBox kodeHBox = new HBox();
        kodeHBox.setSpacing(20);
        kodeHBox.getChildren().addAll(kodeLabel, kodeTextField);
        centralPane.add(kodeHBox, 0, 2);

        //Laver knapperne
        MotherButton annullerButton = new MotherButton("Annuller");
        annullerButton.setOnAction(e -> owner.drawDefault());
        MotherButton loginButton = new MotherButton("Log ind");
        loginButton.setOnAction(e -> checkLogin());

        //Laver en HBox til login og annuller knapper
        HBox buttonHBox = new HBox();
        buttonHBox.getChildren().addAll(annullerButton, loginButton);
        centralPane.add(buttonHBox, 0, 3);

        //Tilføjer centralPane til ydrepane
        this.add(centralPane, 0, 0);
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