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
    private final MotherTab owner;
    private MotherComboBox tidligereIndholdComboBox;
    private MotherComboBox træsortComboBox;
    private MotherComboBox forhandlerComboBox;
    private TextArea kommentarTextArea;
    private TextField størrelseTextField;

    public OpretFadPane(String title, MotherTab owner) {
        super("/gui/images/mark.jpg");
        this.owner = owner;

        CentralPane centralPane = new CentralPane();

        //Overskrift
        MotherLabel instructionLabel = new MotherLabel("Udfyld nedenstående for at oprette et nyt fad: ");
        centralPane.add(instructionLabel, 0, 0, 2, 1);

        //vælg træsort
        InfoLabel træsortLabel = new InfoLabel("Træsort:");
        //træsortLabel.setTextFill(Paint.valueOf("white"));
        træsortComboBox = new MotherComboBox();
        Træsort[] træsorter = Træsort.values();
        træsortComboBox.getItems().addAll(træsorter);
        centralPane.add(træsortLabel, 0, 1);
        centralPane.add(træsortComboBox, 1, 1);

        //vælg Tidligere indhold
        InfoLabel tidligereIndholdLabel = new InfoLabel("Tidligere Indhold:");
        tidligereIndholdComboBox = new MotherComboBox();
        TidligereIndhold[] tidligereIndhold = TidligereIndhold.values();
        tidligereIndholdComboBox.getItems().addAll(tidligereIndhold);
        centralPane.add(tidligereIndholdLabel, 0, 2);
        centralPane.add(tidligereIndholdComboBox, 1, 2);

        //vælg Forhandler
        InfoLabel forhandlerLabel = new InfoLabel("Købt hos:");
        forhandlerComboBox = new MotherComboBox();
        ArrayList<Forhandler> forhandlerer = Controller.getForhandlere();
        forhandlerComboBox.getItems().addAll(forhandlerer);
        centralPane.add(forhandlerLabel, 0, 3);
        centralPane.add(forhandlerComboBox, 1, 3);

        //Skriver størrelse
        InfoLabel størrelseLabel = new InfoLabel("Fad Størrelse i Liter:");
        størrelseTextField = new TextField();
        størrelseTextField.setEditable(true);
        centralPane.add(størrelseLabel,0,4);
        centralPane.add(størrelseTextField,1,4);
        størrelseTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    størrelseTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        //Evt skrive kommentar
        InfoLabel kommentarLabel = new InfoLabel("Kommentar: ");
        kommentarTextArea = new TextArea();
        kommentarTextArea.setEditable(true);
        kommentarTextArea.setMaxSize(250,125);
        centralPane.add(kommentarLabel,0,5);
        centralPane.add(kommentarTextArea,1,5, 1, 2);

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
        centralPane.add(imageView,0,6);

        this.add(centralPane,0,0);
    }
}
