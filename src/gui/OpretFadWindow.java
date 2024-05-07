package gui;

import application.controller.Controller;
import application.model.Forhandler;
import application.model.TidligereIndhold;
import application.model.Træsort;
import gui.motherClasses.*;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class OpretFadWindow extends Stage {
    private MotherComboBox tidligereIndholdComboBox;
    private MotherComboBox træsortComboBox;
    private MotherComboBox forhandlerComboBox;
    private TextArea kommentarTextArea;
    private TextField størrelseTextField;

    public OpretFadWindow(String title, Stage owner) {
        this.initOwner(owner);
        this.setTitle(title);
        MotherPane pane = new MotherPane();
        this.initContent(pane);
        Scene scene = new Scene(pane);
        this.setScene(scene);
    }


    private void initContent(MotherPane pane) {
        MotherLabel instructionLabel = new MotherLabel("Udfyld venligst nedenstående for at oprette et nyt fad: ");
        pane.add(instructionLabel, 0, 0, 2, 1);

        //vælg træsort
        InfoLabel træsortLabel = new InfoLabel("Træsort:");
        træsortComboBox = new MotherComboBox();
        Træsort[] træsorter = Træsort.values();
        træsortComboBox.getItems().addAll(træsorter);
        pane.add(træsortLabel, 0, 1);
        pane.add(træsortComboBox, 1, 1);

        //vælg Tidligere indhold
        InfoLabel tidligereIndholdLabel = new InfoLabel("Tidligere Indhold:");
        tidligereIndholdComboBox = new MotherComboBox();
        TidligereIndhold[] tidligereIndhold = TidligereIndhold.values();
        tidligereIndholdComboBox.getItems().addAll(tidligereIndhold);
        pane.add(tidligereIndholdLabel, 0, 2);
        pane.add(tidligereIndholdComboBox, 1, 2);

        //vælg Forhandler
        InfoLabel forhandlerLabel = new InfoLabel("Købt hos:");
        forhandlerComboBox = new MotherComboBox();
        ArrayList<Forhandler> forhandlerer = Controller.getForhandlere();
        forhandlerComboBox.getItems().addAll(forhandlerer);
        pane.add(forhandlerLabel, 0, 3);
        pane.add(forhandlerComboBox, 1, 3);

        //Skriver størrelse
        InfoLabel størrelseLabel = new InfoLabel("Fad Størrelse i Liter:");
        størrelseTextField = new TextField();
        størrelseTextField.setEditable(true);
        pane.add(størrelseLabel,0,4);
        pane.add(størrelseTextField,1,4);

        //Evt skrive kommentar
        InfoLabel kommentarLabel = new InfoLabel("Kommentar: ");
        kommentarTextArea = new TextArea();
        kommentarTextArea.setEditable(true);
        kommentarTextArea.setMaxWidth(250);
        pane.add(kommentarLabel,0,5);
        pane.add(kommentarTextArea,1,5);

        //Tilføjer knapper
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.BASELINE_RIGHT);
        MotherButton afbrydButton = new MotherButton("Afbryd");
        MotherButton bekræftButton = new MotherButton("Bekræft");
        afbrydButton.setOnAction(event -> {
            this.close();
        });

        bekræftButton.setOnAction(event -> {
            try {
                validereInput();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Bekræft Oprettelse");
                alert.setHeaderText("Bekræft nedenstående information:");

                alert.setContentText("Test Content Text");
                alert.showAndWait();
            } catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("KATASTROFAL FEJL 40!!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });

        //Tilføjer knapper til pane
        buttonBox.getChildren().addAll(afbrydButton,bekræftButton);
        pane.add(buttonBox,1,6);

    }

    private void validereInput() {
        if (this.træsortComboBox.getSelectionModel().isEmpty()){
            throw new NoSuchElementException("Ingen Træsort Valgt!");
        }
        if (this.tidligereIndholdComboBox.getSelectionModel().isEmpty()){
            throw new NoSuchElementException("Intet Tidligere Indhold Valgt!");
        }
        if (this.forhandlerComboBox.getSelectionModel().isEmpty()){
            throw new NoSuchElementException("Ingen Forhandler Valgt!");
        }
        if (this.størrelseTextField.getText().isEmpty() || this.størrelseTextField.getText().matches(".*[^0-9].*")){
            throw new NoSuchElementException("Ikke En Gyldig Størrelse!");
        }
    }
}
