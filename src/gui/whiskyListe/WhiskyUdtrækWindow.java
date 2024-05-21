package gui.whiskyListe;

import application.controller.Controller;
import application.model.Whisky;
import gui.motherClasses.*;
import gui.tabs.UdtrækFadTap;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class WhiskyUdtrækWindow extends MotherPaneWithImageBackground {
    private final ListView<Whisky> whiskyListView;

    public WhiskyUdtrækWindow(MotherTab owner) {
        super("/gui/images/mark.jpg", owner);

        //Overskrift
        MotherLabel instructionLabel = new MotherLabel("Liste Over Whiskyer");
        centralPane.add(instructionLabel, 0, 0, 4, 1);

        //Laver et listView til alle fad.
        whiskyListView = new ListView<>();
        whiskyListView.getItems().addAll(Controller.getWhisky());
        InfoLabel whiskyLabel = new InfoLabel("Whiskyer: ");
        centralPane.add(whiskyLabel,0,1);
        centralPane.add(whiskyListView, 0, 2,1,2);

        //Laver et textArea til ekstra info om fadet
        //Laver et textArea til ekstra info som det valgte fad
        TextArea whiskyTextArea = new TextArea();
        whiskyTextArea.setEditable(false);
        whiskyListView.setMinWidth(300);
        InfoLabel whiskyInfoLabel = new InfoLabel("Whisky info:");
        centralPane.add(whiskyInfoLabel, 1,1);
        centralPane.add(whiskyTextArea, 1,2,1,2);

        //Laver et listener så textareaet automatisk opdateres
        whiskyListView.getSelectionModel().selectedIndexProperty().addListener(observable -> {
            if (whiskyListView.getSelectionModel().getSelectedItem() != null) {
                whiskyTextArea.setText(whiskyListView.getSelectionModel().getSelectedItem().getHistorie());
            }
        });

        // Opretter buttons annuler og opret, samt vbox der holder på de 2
        Button annullerButton = new Button("Annuller");
        annullerButton.setOnAction(e -> this.owner.drawDefault());
        centralPane.add(annullerButton, 1,4);

        this.add(centralPane,0,0);
    }
}
