package gui.opretFad;

import application.controller.Controller;
import application.model.Forhandler;
import application.model.TidligereIndhold;
import application.model.Træsort;
import gui.motherClasses.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.util.ArrayList;

public class OpretFadPane extends MotherPaneWithImageBackground {
    private MotherComboBox tidligereIndholdComboBox;
    private MotherComboBox træsortComboBox;
    private MotherComboBox forhandlerComboBox;
    private TextArea kommentarTextArea;
    private TextField størrelseTextField;

    public OpretFadPane(String title, MotherTab owner) {
        super("/gui/images/mark.jpg", owner);

        //Overskrift
        MotherLabel instructionLabel = new MotherLabel("Udfyld nedenstående for at oprette et nyt fad: ");
        centralPane.add(instructionLabel, 0, 0, 2, 1);

        //vælg træsort
        InfoLabel træsortLabel = new InfoLabel("Træsort:");
        //træsortLabel.setTextFill(Paint.valueOf("white"));
        træsortComboBox = new MotherComboBox();
        Træsort[] træsorter = Træsort.values();
        træsortComboBox.getItems().addAll(træsorter);
        centralPane.addRow(1, træsortLabel, træsortComboBox);

        //vælg Tidligere indhold
        InfoLabel tidligereIndholdLabel = new InfoLabel("Tidligere Indhold:");
        tidligereIndholdComboBox = new MotherComboBox();
        TidligereIndhold[] tidligereIndhold = TidligereIndhold.values();
        tidligereIndholdComboBox.getItems().addAll(tidligereIndhold);
        centralPane.addRow(2, tidligereIndholdLabel, tidligereIndholdComboBox);

        //vælg Forhandler
        InfoLabel forhandlerLabel = new InfoLabel("Købt hos:");
        forhandlerComboBox = new MotherComboBox();
        ArrayList<Forhandler> forhandlerer = Controller.getForhandlere();
        forhandlerComboBox.getItems().addAll(forhandlerer);
        centralPane.addRow(3, forhandlerLabel, forhandlerComboBox);

        //Skriver størrelse
        InfoLabel størrelseLabel = new InfoLabel("Fad Størrelse i Liter:");
        størrelseTextField = new NumberTextField();
        størrelseTextField.setEditable(true);
        centralPane.addRow(4, størrelseLabel, størrelseTextField);

        //Evt skrive kommentar
        InfoLabel kommentarLabel = new InfoLabel("Kommentar: ");
        kommentarTextArea = new TextArea();
        kommentarTextArea.setEditable(true);
        kommentarTextArea.setMaxSize(250, 125);
        centralPane.add(kommentarLabel, 0, 5);
        centralPane.add(kommentarTextArea, 1, 5, 1, 2);

        /*
        Opretter knapper og en HBox til at holde på dem.
        BekræftFadButton typen står for funktionalitet ift. at oprette
         */
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(Pos.BASELINE_RIGHT);
        MotherButton afbrydButton = new MotherButton("Afbryd");
        BekræftFadButton bekræftButton = new BekræftFadButton("Bekræft");

        //Afbryd lukker blot vinduet
        afbrydButton.setOnAction(event -> {
            this.owner.drawDefault();
        });

        //Bekræft tjekker først om det indtastede information er gyldigt. Hvis det er gyldigt, så vises et bekræftelses vindue ellers vises en advarsel
        bekræftButton.setOnAction(event -> {
            CommonClass commonClass = new CommonClass((Træsort) træsortComboBox.getValue(), (Forhandler) forhandlerComboBox.getValue(), (TidligereIndhold) tidligereIndholdComboBox.getValue(), størrelseTextField.getText(), kommentarTextArea.getText());
            boolean bekræftet = bekræftButton.bekræft(commonClass);
            if (bekræftet) {
                this.owner.drawDefault();
            }
        });

        //Tilføjer knapperne til pane
        buttonBox.getChildren().addAll(afbrydButton, bekræftButton);
        centralPane.add(buttonBox, 1, 7);

        //Tilføjer et fadbillede
        Image image = new Image(getClass().getResourceAsStream("/gui/images/WhiskyFade.png"));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(80);
        imageView.setRotate(20);
        centralPane.add(imageView, 0, 6);

        //Tilføjer det centrale pane til ydre pane
        this.add(centralPane, 0, 0);
    }
}
