package gui.fadUdtræk;

import application.controller.Controller;
import application.controller.UdtrækController;
import application.model.Fad;
import application.model.Lager;
import application.model.TidligereIndhold;
import application.model.Træsort;
import gui.motherClasses.*;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class FadUdtrækWindow extends MotherPaneWithImageBackground {
    private final ComboBox<TidligereIndhold> tidligereIndholdComboBox;
    private final DatePicker senestePåfyldningsDatoDatePicker;
    private final ComboBox<Træsort> træsortCombobox;
    private final ComboBox<Lager> lagerComboBox;
    private final ListView<Fad> fadListView;

    public FadUdtrækWindow(MotherTab owner) {
        super("/gui/images/mark.jpg", owner);

        //Overskrift
        MotherLabel instructionLabel = new MotherLabel("Liste Over Fad");
        this.centralPane.add(instructionLabel, 0, 0, 4, 1);


        //Laver et listView til alle fad.
        fadListView = new ListView<>();
        fadListView.getItems().addAll(UdtrækController.getFad());

        //Laver et textArea til ekstra info som det valgte fad
        TextArea fadInfoTextArea = new TextArea();
        fadInfoTextArea.setEditable(false);
        fadInfoTextArea.setMaxWidth(325);
        InfoLabel fadInfoLabel = new InfoLabel("Fad info:");
        VBox fadInfoVBox = new VBox(fadInfoLabel, fadInfoTextArea);

        //Laver et listener så textareaet automatisk opdateres
        fadListView.getSelectionModel().selectedIndexProperty().addListener(observable -> {
            if (fadListView.getSelectionModel().getSelectedItem() != null) {
                fadInfoTextArea.setText(fadListView.getSelectionModel().getSelectedItem().allFadInfo());
            }
        });


        //Laver et pane til at holde på begge søgeresultater
        GridPane resultPane = new GridPane();
        resultPane.setHgap(30);
        resultPane.add(fadListView, 0, 0,2,1);
        resultPane.add(fadInfoVBox, 2, 0,2,1);
        centralPane.add(resultPane,0,5,4,1);



        /*
        Laver forskellige filtrerings muligheder.
        Herunder; Påfyldningsdato, tidligere indhold, træsort og lager
         */

        //Påfyldningsdato
        senestePåfyldningsDatoDatePicker = new DatePicker();
        InfoLabel senestePåfyldingsDatoLabel = new InfoLabel("Påfyldningsdato før: ");
        //Tidligere indhold
        tidligereIndholdComboBox = new ComboBox<>();
        tidligereIndholdComboBox.getItems().addAll(TidligereIndhold.values());
        InfoLabel tidligereIndholdLabel = new InfoLabel("Tidligere indhold: ");
        //Træsort
        træsortCombobox = new ComboBox<>();
        træsortCombobox.getItems().addAll(Træsort.values());
        InfoLabel træsortLabel = new InfoLabel("Træsort: ");
        //Lager
        lagerComboBox = new ComboBox<>();
        lagerComboBox.getItems().addAll(Controller.getLagrer());
        InfoLabel lagerLabel = new InfoLabel("Lager: ");
        //Tilføjer alt til pane
        centralPane.addRow(1, senestePåfyldingsDatoLabel, senestePåfyldningsDatoDatePicker, træsortLabel, træsortCombobox);
        centralPane.addRow(2, tidligereIndholdLabel, tidligereIndholdComboBox, lagerLabel, lagerComboBox);


        /*
        Laver knapper til at vælge hvornår man vil bruge sine filtrer, og til at clear alle valgte ting.
        Den laver et predicate, som sortere efter de valgte filtrer.
         */
        //Laver en knap til clear
        MotherButton clearButton = new MotherButton("Clear");
        clearButton.setOnAction(event -> {
            clearSelections();
        });
        //Laver en knap til filter
        MotherButton søgButton = new MotherButton("Søg");
        søgButton.setOnAction(event -> {
            filterList();
        });
        // Opretter buttons annuler og opret, samt vbox der holder på de 2
        Button annullerButton = new MotherButton("Annuller");
        annullerButton.setOnAction(e -> this.owner.drawDefault());
        //Tilføjer knapperne til pane
        centralPane.addRow(3, clearButton, søgButton, annullerButton);


        this.add(centralPane, 0, 0);
    }

    private void filterList() {
        //Opretter filter værdier
        LocalDate senestePåfyldningsDato = this.senestePåfyldningsDatoDatePicker.getValue();
        TidligereIndhold tidligereIndhold = this.tidligereIndholdComboBox.getValue();
        Træsort træsort = this.træsortCombobox.getValue();
        Lager lager = this.lagerComboBox.getValue();

        //Kalder controller, som bruger ikke-null værdier til at filtrere efter
        ArrayList<Fad> fadEfterFilter = UdtrækController.getFilterFad(senestePåfyldningsDato, tidligereIndhold, træsort, lager);

        //Viser den filtreret liste
        this.fadListView.getItems().clear();
        this.fadListView.getItems().addAll(fadEfterFilter);
    }

    //TODO vi bruger fades tidligere makes, til at filtere efter påfyldningsdato. Vi skal helt undgå at bruge fade uden makes, ift filtering ift. påfyldningsdato

    private void clearSelections() {
        this.tidligereIndholdComboBox.getSelectionModel().select(null);
        this.træsortCombobox.getSelectionModel().select(null);
        this.lagerComboBox.getSelectionModel().select(null);
        this.senestePåfyldningsDatoDatePicker.setValue(null);
    }
}
