package gui.opretMaltbatch;

import application.controller.Controller;
import application.model.Kornsort;
import application.model.Malteri;
import application.model.Mark;
import gui.motherClasses.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import storage.Storage;

import java.time.LocalDate;

public class OpretMaltBatchPane extends MotherPaneWithImageBackground {

    private ListView<Mark> markListView = new ListView<>();
    private ListView<Malteri> malteriListView = new ListView<>();
    private ComboBox<Kornsort> kornsortComboBox = new ComboBox<>();
    private DatePicker ankomstDatePicker = new DatePicker();

    public OpretMaltBatchPane(String billedeAddresse, MotherTab owner) {
        super(billedeAddresse, owner);

        centralPane.add(new MotherLabel("Opret Maltbatch"), 0, 0, 3, 1);

        // Opretter vBox med label og listview der indeholder marker
        VBox vBoxMark = new VBox();
        vBoxMark.setSpacing(10);
        vBoxMark.getChildren().addAll(new InfoLabel("Mark:"), markListView);
        markListView.getItems().addAll(Storage.getMarker());
        centralPane.add(vBoxMark, 0, 1);

        // Opretter vBox med label og listview der indeholder malterier
        VBox vBoxMalteri = new VBox();
        vBoxMalteri.setSpacing(10);
        vBoxMalteri.getChildren().addAll(new InfoLabel("Malteri:"), malteriListView);
        malteriListView.getItems().addAll(Storage.getMalterier());
        centralPane.add(vBoxMalteri, 1, 1);

        // Opretter hbox med knapper
        Button afbrydButton = new MotherButton("Afbryd");
        afbrydButton.setOnAction(e -> this.owner.drawDefault());
        Button opretButton = new MotherButton("Opret");
        opretButton.setOnAction(e -> opretMaltBatch());
        HBox buttonHbox = new HBox();
        buttonHbox.setSpacing(10);
        buttonHbox.getChildren().addAll(afbrydButton, opretButton);

        // Opretter en vbox med labels, kornsortcombobox, ankomstdatpicker og en knappe hbox
        kornsortComboBox.getItems().addAll(Kornsort.values());
        VBox vBoxBlandet = new VBox();
        vBoxBlandet.setSpacing(10);
        vBoxBlandet.getChildren().addAll(
                new InfoLabel("Kornsort:"),
                kornsortComboBox,
                new InfoLabel("Ankomstdato:"),
                ankomstDatePicker,
                buttonHbox
        );
        centralPane.add(vBoxBlandet, 2, 1);

        this.add(centralPane,0,0);
    }

    private void opretMaltBatch() {
        if (kornsortComboBox.getSelectionModel().isEmpty() || ankomstDatePicker.getValue() == null ||
                markListView.getSelectionModel().isEmpty() || malteriListView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Mangler information!");
            alert.setContentText("Vælg venligst de uvalgte felter");
            alert.showAndWait();
        } else {
            Kornsort kornsort = kornsortComboBox.getValue();
            LocalDate ankomstDato = ankomstDatePicker.getValue();
            Malteri malteri = malteriListView.getSelectionModel().getSelectedItem();
            Mark mark = markListView.getSelectionModel().getSelectedItem();

            Controller.opretMaltBatch(kornsort,ankomstDato,malteri,mark);
            this.owner.drawDefault();
        }
    }
}
