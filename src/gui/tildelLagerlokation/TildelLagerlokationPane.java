package gui.tildelLagerlokation;

import application.controller.Controller;
import application.model.Fad;
import application.model.Lager;
import gui.motherClasses.*;
import gui.tapFad.BekræftTapFadButtton;
import gui.tapFad.CommonClass;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import storage.Storage;

import java.util.List;

public class TildelLagerlokationPane extends MotherPaneWithImageBackground {
    public TildelLagerlokationPane(String title, MotherTab owner) {
        super("/gui/images/mark.jpg", owner);

        //Overskrift
        MotherLabel instructionLabel = new MotherLabel("Vælg et fad, og tildel det en lokation");
        centralPane.add(instructionLabel, 0, 0, 6, 1);

        //Laver et listview til fade med lokation
        ListView<Fad> fadMedLokationListView = new ListView<Fad>();
        fadMedLokationListView.setMaxHeight(100);
        fadMedLokationListView.getItems().addAll(Controller.getFadMedLokation());
        InfoLabel fadMedLokationLabel = new InfoLabel("Fad med lokation: ");
        centralPane.add(fadMedLokationLabel,0,1);
        centralPane.add(fadMedLokationListView,0,2,1,2);

        //Laver et listview til fade med lokation
        ListView<Fad> fadUdenLokationListView = new ListView<Fad>();
        fadUdenLokationListView.setMaxHeight(150);
        fadUdenLokationListView.getItems().addAll(Controller.getFadUdenLokation());
        InfoLabel fadUdenLokationLabel = new InfoLabel("Fad uden lokation: ");
        centralPane.add(fadUdenLokationLabel,0,4);
        centralPane.add(fadUdenLokationListView,0,5,1,2);

        //Laver et textArea til ekstra info som det valgte fad
        TextArea fadInfoTextArea = new TextArea();
        fadInfoTextArea.setMaxSize(100,100);
        fadInfoTextArea.setEditable(false);
        InfoLabel fadInfoLabel = new InfoLabel("Fad info:");
        centralPane.add(fadInfoLabel, 1,2);
        centralPane.add(fadInfoTextArea,2,2,1,2);

        //Laver et listener så textareaet automatisk opdateres
        fadUdenLokationListView.getSelectionModel().selectedIndexProperty().addListener(observable -> {
            if (fadUdenLokationListView.getSelectionModel().getSelectedItem() != null) {
                fadInfoTextArea.setText(fadUdenLokationListView.getSelectionModel().getSelectedItem().allFadInfo());
                fadMedLokationListView.getSelectionModel().select(null);
            }
        });

        fadMedLokationListView.getSelectionModel().selectedIndexProperty().addListener(observable -> {
            if (fadMedLokationListView.getSelectionModel().getSelectedItem() != null){
                fadInfoTextArea.setText(fadMedLokationListView.getSelectionModel().getSelectedItem().allFadInfo());
                fadUdenLokationListView.getSelectionModel().select(null);
            }
        });

        //Laver et listview til Lagrer
        ListView<Lager> lagrerListview = new ListView<Lager>();
        lagrerListview.setMaxWidth(150);
        lagrerListview.getItems().addAll(Controller.getLagrer());
        InfoLabel lagrerLabel = new InfoLabel("Lagrer");
        centralPane.add(lagrerLabel,5,1);
        centralPane.add(lagrerListview,5,2,1,5);

        //Laver et textArea til ekstra info som det valgte lager
        TextArea lagerInfoTextArea = new TextArea();
        lagerInfoTextArea.setMaxSize(100,100);
        lagerInfoTextArea.setEditable(false);
        InfoLabel lagerInfoLabel = new InfoLabel("Lager info:");
        centralPane.add(lagerInfoLabel, 3,2);
        centralPane.add(lagerInfoTextArea,4,2,1,2);

        //Laver et listener så textareaet automatisk opdateres
        lagrerListview.getSelectionModel().selectedIndexProperty().addListener(observable -> {
            lagerInfoTextArea.setText(lagrerListview.getSelectionModel().getSelectedItem().lagerInformation());
        });

        //Laver et label og textfield til ReolNr.
        InfoLabel reolNrLabel = new InfoLabel("Reol Nr:");
        TextField reolNrTextField = new TextField();
        reolNrTextField.setMaxWidth(30);
        centralPane.add(reolNrLabel,1,4);
        centralPane.add(reolNrTextField,2,4);

        //Laver et label og textfield til HyldeNr.
        InfoLabel hyldeNrLabel = new InfoLabel("Hylde Nummer:");
        TextField hyldeNrTextField = new TextField();
        hyldeNrTextField.setMaxWidth(30);
        centralPane.add(hyldeNrLabel,1,5);
        centralPane.add(hyldeNrTextField,2,5);

        //Laver et label og textfield til HyldePlacering.
        InfoLabel hyldePlaceringLabel = new InfoLabel("Hylde placering:");
        TextField hyldePlaceringNrTextField = new TextField();
        hyldePlaceringNrTextField.setMaxWidth(30);
        centralPane.add(hyldePlaceringLabel,1,6);
        centralPane.add(hyldePlaceringNrTextField,2,6);

        //Opretter knapper og en HBox til at holde på dem.
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(Pos.BASELINE_RIGHT);
        MotherButton afbrydButton = new MotherButton("Afbryd");
        BekræftTapFadButtton bekræftButton = new BekræftTapFadButtton("Tap Fad");

        //Afbryd lukker blot vinduet
        afbrydButton.setOnAction(event -> {
            owner.drawDefault();
        });

        //Bekræft tjekker først om det indtastede information er gyldigt. Hvis det er gyldigt, så vises et bekræftelses vindue ellers vises en advarsel
        bekræftButton.setOnAction(actionEvent -> {
            Fad fad = null;
            if (fadMedLokationListView.getSelectionModel().getSelectedItem() != null) {
                fad = fadMedLokationListView.getSelectionModel().getSelectedItem();
            }
            if (fadUdenLokationListView.getSelectionModel().getSelectedItem() != null) {
                fad = fadUdenLokationListView.getSelectionModel().getSelectedItem();
            }
            Lager lager = lagrerListview.getSelectionModel().getSelectedItem();

            int reolNummer = Integer.parseInt(reolNrTextField.getText());
            int højdeNummer = Integer.parseInt(hyldeNrTextField.getText());
            int placeringsnummer = Integer.parseInt(hyldePlaceringNrTextField.getText());

            Controller.setLagerLokation(fad, lager, reolNummer, højdeNummer, placeringsnummer);
        });

        //Tilføjer knapperne til pane
        buttonBox.getChildren().addAll(afbrydButton, bekræftButton);
        centralPane.addColumn(1, buttonBox);

        //Tilføjer centralPane til ydre pane
        this.add(centralPane,0,0);
    }
}
