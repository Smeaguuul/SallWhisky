package gui.opretFad;

import application.controller.Controller;
import application.model.Forhandler;
import application.model.TidligereIndhold;
import application.model.Træsort;
import gui.motherClasses.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class OpretFadPane extends MotherPane {
    private final MotherTab owner;
    private MotherComboBox tidligereIndholdComboBox;
    private MotherComboBox træsortComboBox;
    private MotherComboBox forhandlerComboBox;
    private TextArea kommentarTextArea;
    private TextField størrelseTextField;

    public OpretFadPane(String title, MotherTab owner) {
        this.owner = owner;

        MotherLabel instructionLabel = new MotherLabel("Udfyld venligst nedenstående for at oprette et nyt fad: ");
        this.add(instructionLabel, 0, 0, 2, 1);

        //vælg træsort
        InfoLabel træsortLabel = new InfoLabel("Træsort:");
        træsortComboBox = new MotherComboBox();
        Træsort[] træsorter = Træsort.values();
        træsortComboBox.getItems().addAll(træsorter);
        this.add(træsortLabel, 0, 1);
        this.add(træsortComboBox, 1, 1);

        //vælg Tidligere indhold
        InfoLabel tidligereIndholdLabel = new InfoLabel("Tidligere Indhold:");
        tidligereIndholdComboBox = new MotherComboBox();
        TidligereIndhold[] tidligereIndhold = TidligereIndhold.values();
        tidligereIndholdComboBox.getItems().addAll(tidligereIndhold);
        this.add(tidligereIndholdLabel, 0, 2);
        this.add(tidligereIndholdComboBox, 1, 2);

        //vælg Forhandler
        InfoLabel forhandlerLabel = new InfoLabel("Købt hos:");
        forhandlerComboBox = new MotherComboBox();
        ArrayList<Forhandler> forhandlerer = Controller.getForhandlere();
        forhandlerComboBox.getItems().addAll(forhandlerer);
        this.add(forhandlerLabel, 0, 3);
        this.add(forhandlerComboBox, 1, 3);

        //Skriver størrelse
        InfoLabel størrelseLabel = new InfoLabel("Fad Størrelse i Liter:");
        størrelseTextField = new TextField();
        størrelseTextField.setEditable(true);
        this.add(størrelseLabel,0,4);
        this.add(størrelseTextField,1,4);

        //Evt skrive kommentar
        InfoLabel kommentarLabel = new InfoLabel("Kommentar: ");
        kommentarTextArea = new TextArea();
        kommentarTextArea.setEditable(true);
        kommentarTextArea.setMaxWidth(250);
        kommentarLabel.setAlignment(Pos.TOP_CENTER);
        this.add(kommentarLabel,0,5);
        this.add(kommentarTextArea,1,5, 1, 2);

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
        this.add(buttonBox, 1, 7);

        //Tilføjer et fadbillede
        Image image = new Image(getClass().getResourceAsStream("/gui/images/WhiskyFade.png"));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(120);
        imageView.setRotate(20);
        this.add(imageView,0,6);
    }
}
