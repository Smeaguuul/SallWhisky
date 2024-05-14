package gui.opretAdminMedarbejder;

import application.controller.Controller;
import application.model.Medarbejder;
import gui.motherClasses.*;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class AdminLoginPane extends MotherPane {
    private final MotherTab owner;
    private TextField nummerTextField;
    private Medarbejder aktuelMedarbejder;
    private TextField kodeTextField;

    public AdminLoginPane(String title, MotherTab owner) {
        this.owner = owner;
        this.setBackground(new Background(new BackgroundImage(new Image("/gui/images/mark.jpg"), null, null, null, new BackgroundSize(1600, 900, false, false, true, true)), null, null));
        this.setAlignment(Pos.CENTER);

        //Opretter et pane til at holde på de centrale elementer
        GridPane centralPane = new CentralPane();

        //Titel
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
        nummerTextField = new TextField();
        nummerTextField.setMaxWidth(50);
        nummerHBox.getChildren().add(nummerTextField);

        //HBox til kode checkning
        HBox kodeHBox = new HBox();
        kodeHBox.setSpacing(20);

        // Label
        InfoLabel kodeLabel = new InfoLabel("Kodeord: ");
        kodeHBox.getChildren().add(kodeLabel);

        // Text Field
        kodeTextField = new TextField();
        kodeTextField = new TextField();
        kodeTextField.setMaxWidth(50);
        kodeHBox.getChildren().add(kodeTextField);

        centralPane.add(nummerHBox, 0, 1);
        centralPane.add(kodeHBox, 0, 2);


        //Laver en HBox til login og annuller knapper
        HBox buttonHBox = new HBox();

        //Laver knapperne
        MotherButton annullerButton = new MotherButton("Annuller");
        annullerButton.setOnAction(e -> {
            owner.drawDefault();
        });
        buttonHBox.getChildren().add(annullerButton);

        MotherButton loginButton = new MotherButton("Log ind");
        loginButton.setOnAction(e -> {
            checkLogin();
        });

        buttonHBox.getChildren().add(loginButton);

        centralPane.add(buttonHBox, 0, 3);


    }

    private void checkLogin() {
        try {
            aktuelMedarbejder = Controller.forsøgLogin(Integer.parseInt(nummerTextField.getText()), kodeTextField.getText());
            openMedarbejderAdminPane();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Nej");
            alert.setContentText("Nej * 2");
            alert.showAndWait(); //TODO Skal cleanes op og have clear textfields osv
        }
    }

    private void openMedarbejderAdminPane() {
        MotherPane adminMedarbejderPane = new AdminMedarbejderPane("Adminstrer Medarbejder", this.owner);
        owner.setContent(adminMedarbejderPane);
    }
}
