package gui.opretDestillat;

import application.controller.Controller;
import application.model.*;
import gui.motherClasses.*;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.time.LocalDate;
import java.util.ArrayList;


public class OpretDestillatPane extends MotherPane {
    private Medarbejder medarbejder;

    public OpretDestillatPane(String title, MotherTab owner, Medarbejder medarbejder) {
        this.medarbejder = medarbejder;
        this.setBackground(new Background(new BackgroundImage(new Image("/gui/images/mark.jpg"), null, null, null, new BackgroundSize(1600,900,false,false,true,true)), null, null));
        this.setAlignment(Pos.CENTER);

        //Laver et gridpane til at holde de centrale elementer
        GridPane centralPane = new CentralPane();

        MotherLabel instructionLabel = new MotherLabel("Udfyld venligst nedenstående for at oprette et nyt destillat: ");
        centralPane.add(instructionLabel, 0, 0, 2, 1);

        //vælg startdato
        InfoLabel startDatoLabel = new InfoLabel("Start dato:");
        DatePicker startDatoDatePicker = new DatePicker(LocalDate.now().minusDays(2));

        //vælg slutdato
        InfoLabel slutDatoLabel = new InfoLabel("Slut dato:");
        DatePicker slutDatoDatePicker = new DatePicker(LocalDate.now());

        //Tilføjer en listener til begge, så man ikke kan vælge forkerte datoer
        startDatoDatePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue.isAfter(slutDatoDatePicker.getValue())){
                slutDatoDatePicker.setValue(newValue);
            }
        });
        slutDatoDatePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue.isBefore(startDatoDatePicker.getValue())){
                startDatoDatePicker.setValue(newValue);
            }
        });

        //Vælger liter væske
        InfoLabel literVæskeLabel = new InfoLabel("Liter væske produceret:");
        TextField størrelseTextField = new TextField();
        størrelseTextField.setEditable(true);

        //Vælger alkoholprocent
        InfoLabel alkoholProcentLabel = new InfoLabel("Endelige alkoholprocent:");
        TextField alkoholProcentTextField = new TextField();
        alkoholProcentTextField.setEditable(true);

        //Vælger rygningstype
        InfoLabel rygningstypeLabel = new InfoLabel("Rygningstype:");
        MotherComboBox rygningstypeComboBox = new MotherComboBox();
        RygningsType[] rygningsTyper = RygningsType.values();
        rygningstypeComboBox.getItems().addAll(rygningsTyper);

        //Vælger maltbatch
        InfoLabel maltBatchLabel = new InfoLabel("Maltbatch: ");
        MotherComboBox maltBatchComboBox = new MotherComboBox();
        ArrayList<MaltBatch> MaltBatches = Controller.getMaltBatches();
        maltBatchComboBox.getItems().addAll(MaltBatches);

        //Evt. en kommentar
        InfoLabel kommentarLabel = new InfoLabel("Kommentar: ");
        TextArea kommentarTextArea = new TextArea();
        kommentarTextArea.setEditable(true);
        kommentarTextArea.setMaxWidth(250);
        kommentarLabel.setAlignment(Pos.TOP_CENTER);

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

        //Tilføjer alle noder til det centrale pane, og sætter det på det ydre pane
        centralPane.addColumn(0, startDatoLabel, slutDatoLabel, literVæskeLabel, alkoholProcentLabel, rygningstypeLabel, maltBatchLabel, kommentarLabel);
        centralPane.addColumn(1, startDatoDatePicker, slutDatoDatePicker, størrelseTextField, alkoholProcentTextField, rygningstypeComboBox, maltBatchComboBox);
        centralPane.add(kommentarTextArea,1,7, 1, 2);
        centralPane.add(buttonBox, 1, 9);

        this.add(centralPane,0,0);

    }
}
