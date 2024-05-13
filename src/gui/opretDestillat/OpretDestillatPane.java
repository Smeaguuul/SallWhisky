package gui.opretDestillat;

import application.controller.Controller;
import application.model.*;
import gui.motherClasses.*;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.util.ArrayList;


public class OpretDestillatPane extends MotherPane {
    private Medarbejder medarbejder;

    public OpretDestillatPane(String title, MotherTab owner, Medarbejder medarbejder) {
        this.medarbejder = medarbejder;
        this.setBackground(new Background(new BackgroundImage(new Image("/gui/images/mark.jpg"), null, null, null, new BackgroundSize(1600,900,false,false,true,true)), null, null));
        this.setAlignment(Pos.CENTER);

        MotherLabel instructionLabel = new MotherLabel("Udfyld venligst nedenstående for at oprette et nyt destillat: ");
        this.add(instructionLabel, 0, 0, 2, 1);

        //vælg startdato //TODO Kan ikke være efter slutdato eller vice versa.
        InfoLabel startDatoLabel = new InfoLabel("Start dato:");
        DatePicker startDatoDatePicker = new DatePicker(LocalDate.now().minusDays(2));
        this.add(startDatoLabel, 0, 1);
        this.add(startDatoDatePicker, 1, 1);

        //vælg slutdato
        InfoLabel slutDatoLabel = new InfoLabel("Slut dato:");
        DatePicker slutDatoDatePicker = new DatePicker(LocalDate.now());
        this.add(slutDatoLabel, 0, 2);
        this.add(slutDatoDatePicker, 1, 2);

        //Vælger liter væske
        InfoLabel literVæskeLabel = new InfoLabel("Liter væske produceret:");
        TextField størrelseTextField = new TextField();
        størrelseTextField.setEditable(true);
        this.add(literVæskeLabel, 0, 3);
        this.add(størrelseTextField, 1, 3);

        //Vælger alkoholprocent
        InfoLabel alkoholProcentLabel = new InfoLabel("Endelige alkoholprocent:");
        TextField alkoholProcentTextField = new TextField();
        alkoholProcentTextField.setEditable(true);
        this.add(alkoholProcentLabel, 0, 4);
        this.add(alkoholProcentTextField, 1, 4);

        //Vælger rygningstype
        InfoLabel rygningstypeLabel = new InfoLabel("Rygningstype:");
        MotherComboBox rygningstypeComboBox = new MotherComboBox();
        RygningsType[] rygningsTyper = RygningsType.values();
        rygningstypeComboBox.getItems().addAll(rygningsTyper);
        this.add(rygningstypeLabel, 0, 5);
        this.add(rygningstypeComboBox, 1, 5);

        //Vælger maltbatch
        InfoLabel maltBatchLabel = new InfoLabel("Maltbatch: ");
        MotherComboBox maltBatchComboBox = new MotherComboBox();
        ArrayList<MaltBatch> MaltBatches = Controller.getMaltBatches();
        maltBatchComboBox.getItems().addAll(MaltBatches);
        this.add(maltBatchLabel, 0, 6);
        this.add(maltBatchComboBox, 1, 6);

        //Evt. en kommentar
        InfoLabel kommentarLabel = new InfoLabel("Kommentar: ");
        TextArea kommentarTextArea = new TextArea();
        kommentarTextArea.setEditable(true);
        kommentarTextArea.setMaxWidth(250);
        kommentarLabel.setAlignment(Pos.TOP_CENTER);
        this.add(kommentarLabel,0,7);
        this.add(kommentarTextArea,1,7, 1, 2);


        /*
        Opretter knapper og en HBox til at holde på dem.
        BekræftFadButton typen står for funktionalitet ift. at oprette
         */
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(Pos.BASELINE_RIGHT);
        MotherButton afbrydButton = new MotherButton("Afbryd");
        BekræftDestillatButton bekræftButton = new BekræftDestillatButton("Bekræft");

        //Afbryd lukker blot vinduet
        afbrydButton.setOnAction(event -> {
            owner.drawDefault();
        });

        //Bekræft tjekker først om det indtastede information er gyldigt. Hvis det er gyldigt, så vises et bekræftelses vindue ellers vises en advarsel
        bekræftButton.setOnAction(event -> {
            CommonClass commonClass = new CommonClass(startDatoDatePicker.getValue(), slutDatoDatePicker.getValue(), størrelseTextField.getText(), alkoholProcentTextField.getText(),rygningstypeComboBox.getValue(), maltBatchComboBox.getValue(),kommentarTextArea.getText(), medarbejder);
            boolean bekræftet = bekræftButton.bekræft(commonClass);
            if (bekræftet) {
                owner.drawDefault();
            }
        });

        //Tilføjer knapperne til pane
        buttonBox.getChildren().addAll(afbrydButton, bekræftButton);
        this.add(buttonBox, 1, 9);

    }
}
