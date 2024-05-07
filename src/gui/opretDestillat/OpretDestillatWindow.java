package gui.opretDestillat;

import application.controller.Controller;
import application.model.*;
import com.sun.javafx.scene.control.behavior.DatePickerBehavior;
import gui.motherClasses.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;


public class OpretDestillatWindow extends Stage {
    private Medarbejder medarbejder;

    public OpretDestillatWindow(String title, Stage owner) {
        this.initOwner(owner);
        this.setTitle(title);
        MotherPane pane = new MotherPane();
        this.initContent(pane);
        Scene scene = new Scene(pane);
        this.setScene(scene);
    }


    private void initContent(MotherPane pane) {
        MotherLabel instructionLabel = new MotherLabel("Udfyld venligst nedenstående for at oprette et nyt destillat: ");
        pane.add(instructionLabel, 0, 0, 2, 1);

        //vælg startdato //TODO Kan ikke være efter slutdato eller vice versa.
        InfoLabel startDatoLabel = new InfoLabel("Start dato:");
        DatePicker startDatoDatePicker = new DatePicker(LocalDate.now().minusDays(2));
        pane.add(startDatoLabel, 0, 1);
        pane.add(startDatoDatePicker, 1, 1);

        //vælg slutdato
        InfoLabel slutDatoLabel = new InfoLabel("Slut dato:");
        DatePicker slutDatoDatePicker = new DatePicker(LocalDate.now());
        pane.add(slutDatoLabel, 0, 2);
        pane.add(slutDatoDatePicker, 1, 2);

        //Vælger liter væske
        InfoLabel literVæskeLabel = new InfoLabel("Liter væske produceret:");
        TextField størrelseTextField = new TextField();
        størrelseTextField.setEditable(true);
        pane.add(literVæskeLabel, 0, 3);
        pane.add(størrelseTextField, 1, 3);

        //Vælger alkoholprocent
        InfoLabel alkoholProcentLabel = new InfoLabel("Endelige alkoholprocent:");
        TextField alkoholProcentTextField = new TextField();
        alkoholProcentTextField.setEditable(true);
        pane.add(alkoholProcentLabel, 0, 4);
        pane.add(alkoholProcentTextField, 1, 4);

        //Vælger rygningstype
        InfoLabel rygningstypeLabel = new InfoLabel("Rygningstype:");
        MotherComboBox rygningstypeComboBox = new MotherComboBox();
        RygningsType[] rygningsTyper = RygningsType.values();
        rygningstypeComboBox.getItems().addAll(rygningsTyper);
        pane.add(rygningstypeLabel, 0, 5);
        pane.add(rygningstypeComboBox, 1, 5);

        //Vælger maltbatch
        InfoLabel maltBatchLabel = new InfoLabel("Maltbatch: ");
        MotherComboBox maltBatchComboBox = new MotherComboBox();
        ArrayList<MaltBatch> MaltBatches = Controller.getMaltBatches();
        maltBatchComboBox.getItems().addAll(MaltBatches);
        pane.add(maltBatchLabel, 0, 6);
        pane.add(maltBatchComboBox, 1, 6);

        //Evt. en kommentar
        InfoLabel kommentarLabel = new InfoLabel("Kommentar: ");
        TextArea kommentarTextArea = new TextArea();
        kommentarTextArea.setEditable(true);
        kommentarTextArea.setMaxWidth(250);
        kommentarLabel.setAlignment(Pos.TOP_CENTER);
        pane.add(kommentarLabel,0,7);
        pane.add(kommentarTextArea,1,7, 1, 2);


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
            this.close();
        });

        //Bekræft tjekker først om det indtastede information er gyldigt. Hvis det er gyldigt, så vises et bekræftelses vindue ellers vises en advarsel
        bekræftButton.setOnAction(event -> {
            CommonClass commonClass = new CommonClass(startDatoDatePicker.getValue(), slutDatoDatePicker.getValue(), størrelseTextField.getText(), alkoholProcentTextField.getText(),rygningstypeComboBox.getValue(), maltBatchComboBox.getValue(),kommentarTextArea.getText(), medarbejder);
            boolean bekræftet = bekræftButton.bekræft(commonClass);
            if (bekræftet) {
                this.close();
            }
        });

        //Tilføjer knapperne til pane
        buttonBox.getChildren().addAll(afbrydButton, bekræftButton);
        pane.add(buttonBox, 1, 9);

    }
}
