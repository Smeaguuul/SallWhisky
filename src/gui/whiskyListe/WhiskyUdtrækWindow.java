package gui.whiskyListe;

import application.controller.Controller;
import application.controller.UdtrækController;
import application.model.Whisky;
import gui.motherClasses.CentralPane;
import gui.motherClasses.InfoLabel;
import gui.motherClasses.MotherLabel;
import gui.motherClasses.MotherPaneWithImageBackground;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class WhiskyUdtrækWindow extends MotherPaneWithImageBackground {
    private final ListView<Whisky> whiskyListView;

    public WhiskyUdtrækWindow() {
        super("/gui/images/mark.jpg");

        //Laver central pane til alle centrale elemenet
        GridPane centralPane = new CentralPane();

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


        this.add(centralPane,0,0);
    }
}
