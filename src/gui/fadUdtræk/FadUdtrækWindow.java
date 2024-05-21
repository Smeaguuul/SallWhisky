package gui.fadUdtræk;

import application.controller.Controller;
import application.controller.UdtrækController;
import application.model.Fad;
import application.model.Lager;
import application.model.TidligereIndhold;
import application.model.Træsort;
import gui.motherClasses.*;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class FadUdtrækWindow extends MotherPaneWithImageBackground {
    private final ComboBox<TidligereIndhold> tidligereIndholdComboBox;
    private final DatePicker senestePåfyldningsDatoDatePicker;
    private final ComboBox<Træsort> træsortCombobox;
    private final ComboBox<Lager> lagerComboBox;
    private final ListView<Fad> fadListView;
    private final MotherTab owner;

    public FadUdtrækWindow(MotherTab owner) {
        super("/gui/images/mark.jpg");

        this.owner = owner;
        //Laver central pane til alle centrale elemenet
        GridPane centralPane = new CentralPane();

        //Overskrift
        MotherLabel instructionLabel = new MotherLabel("Liste Over Fad");
        centralPane.add(instructionLabel, 0, 0, 4, 1);

        //Laver et listView til alle fad.
        fadListView = new ListView<>();
        fadListView.getItems().addAll(UdtrækController.getFad());
        centralPane.add(fadListView, 0, 4,2,1);

        //Laver et textArea til ekstra info om fadet
        //Laver et textArea til ekstra info som det valgte fad
        TextArea fadInfoTextArea = new TextArea();
        fadInfoTextArea.setEditable(false);
        fadListView.setMinWidth(300);
        InfoLabel fadInfoLabel = new InfoLabel("Fad info:");
        VBox fadInfoVBox = new VBox(fadInfoLabel, fadInfoTextArea);
        centralPane.add(fadInfoVBox, 2, 4,2,1);

        //Laver et listener så textareaet automatisk opdateres
        fadListView.getSelectionModel().selectedIndexProperty().addListener(observable -> {
            if (fadListView.getSelectionModel().getSelectedItem() != null) {
                fadInfoTextArea.setText(fadListView.getSelectionModel().getSelectedItem().allFadInfo());
            }
        });

        /*
        Laver forskellige filtrerings muligheder.
        Herunder; Påfyldningsdato, tidligere indhold, træsort og lager
         */

        //Påfyldningsdato
        senestePåfyldningsDatoDatePicker = new DatePicker();
        InfoLabel senestePåfyldingsDatoLabel = new InfoLabel("Påfyldningsdato før: ");
        centralPane.addRow(1, senestePåfyldingsDatoLabel, senestePåfyldningsDatoDatePicker);

        //Tidligere indhold
        tidligereIndholdComboBox = new ComboBox<>();
        tidligereIndholdComboBox.getItems().addAll(TidligereIndhold.values());
        InfoLabel tidligereIndholdLabel = new InfoLabel("Tidligere indhold: ");
        centralPane.addRow(2, tidligereIndholdLabel, tidligereIndholdComboBox);

        //Træsort
        træsortCombobox = new ComboBox<>();
        træsortCombobox.getItems().addAll(Træsort.values());
        InfoLabel træsortLabel = new InfoLabel("Træsort: ");
        centralPane.addRow(1, træsortLabel, træsortCombobox);

        //Lager
        lagerComboBox = new ComboBox<>();
        lagerComboBox.getItems().addAll(Controller.getLagrer());
        InfoLabel lagerLabel = new InfoLabel("Lager: ");
        centralPane.addRow(2, lagerLabel, lagerComboBox);


        /*
        Laver knapper til at vælge hvornår man vil bruge sine filtrer, og til at clear alle valgte ting.
        Den laver et predicate, som sortere efter de valgte filtrer.
         */

        //Laver en knap til clear
        MotherButton clearButton = new MotherButton("Clear");
        clearButton.setOnAction(event -> {
            clearSelections();
        });
        centralPane.add(clearButton, 0, 3);

        //Laver en knap til filter
        MotherButton søgButton = new MotherButton("Søg");
        søgButton.setOnAction(event -> {
            filterList();
        });
        centralPane.add(søgButton, 1, 3);

        // Opretter buttons annuler og opret, samt vbox der holder på de 2
        Button annullerButton = new Button("Annuller");
        annullerButton.setOnAction(e -> this.owner.drawDefault());
        centralPane.add(annullerButton, 1,5);


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

        this.fadListView.getItems().clear();
        this.fadListView.getItems().addAll(fadEfterFilter);
    }


    private void clearSelections() {
        this.tidligereIndholdComboBox.getSelectionModel().select(null);
        this.træsortCombobox.getSelectionModel().select(null);
        this.lagerComboBox.getSelectionModel().select(null);
        this.senestePåfyldningsDatoDatePicker.setValue(null);
    }
}
