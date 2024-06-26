package gui.fyldPaafad;

import application.controller.Controller;
import application.model.*;
import gui.motherClasses.*;
import gui.opretFad.CommonClass;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import storage.Storage;

import java.util.ArrayList;
import java.util.HashMap;


public class FyldPaaFad extends MotherPaneWithImageBackground {
    private ListView<Væske> destillatListview = new ListView<>();
    private ListView<Væske> makeListview = new ListView<>();
    private ListView<Fad> fadListView = new ListView<>();
    private TextArea fadInfoTextArea = new TextArea();
    private TextArea væskeInfoTextArea = new TextArea();
    private TextField literTextfield = new NumberTextField();
    private ListView<Væske> indholdListview = new ListView<>();
    private HashMap<Væske, Double> midlertidigHashMap = new HashMap<>();
    private ArrayList<Væske> arrayListTilHashMap = new ArrayList<>();
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public FyldPaaFad(String title, MotherTab owner) {
        super("/gui/images/mark.jpg", owner);

        //Overskrift
        MotherLabel instructionLabel = new MotherLabel("Udfyld nedenstående for at oprette et nyt fad: ");
        centralPane.add(instructionLabel, 0, 0, 5, 1);


        // Opdeler de to væsketyper "destillat og make" i to arraylister
        vaerdierTilVaeskeListviews();

        // Tilføjer labels med tilhørende listview
        VBox destillatListViewVBox = new VBox(new InfoLabel("Destillater:"), destillatListview);
        VBox makeListViewVBox = new VBox(new InfoLabel("Makes:"), makeListview);
        centralPane.addColumn(0, destillatListViewVBox, makeListViewVBox);
        VBox fadVBox = new VBox(new InfoLabel("Fade:"), fadListView);
        centralPane.add(fadVBox, 3, 1, 1, 2);

        // Listeners til væskeListViews, som sender info om valgte objekter til info feltene
        destillatListview.getSelectionModel().selectedIndexProperty().addListener(observable -> {
            //Hvis den nye valgte væske ikke er null
            if ((destillatListview.getSelectionModel().getSelectedItem() != null)) {
                //Så vises info om den ny-valgte væske
                makeListview.getSelectionModel().select(null);
                væskeInfoTextArea.setText(destillatListview.getSelectionModel().getSelectedItem().toString());
            }
        });
        makeListview.getSelectionModel().selectedIndexProperty().addListener(observable -> {
            if (!(makeListview.getSelectionModel().getSelectedItem() == null)) {
                destillatListview.getSelectionModel().select(null);
                væskeInfoTextArea.setText(makeListview.getSelectionModel().getSelectedItem().getOpbygning());
            }
        });

        //Listener til fadListView
        fadListView.getItems().addAll(Controller.getFadeMedPlads());
        fadListView.getSelectionModel().selectedIndexProperty().addListener(observable -> {
            fadInfoTextArea.setText(fadListView.getSelectionModel().getSelectedItem().allFadInfo());
        });

        //Hbox med fad og væske textarea med info
        væskeInfoTextArea.setMaxWidth(200);
        fadInfoTextArea.setMaxWidth(200);
        VBox fadOgVæskeInfoHbox = new VBox();
        fadOgVæskeInfoHbox.getChildren().addAll(new InfoLabel("Fad info:"), fadInfoTextArea, new InfoLabel("Væske info:"), væskeInfoTextArea);
        fadInfoTextArea.editableProperty().setValue(false);
        væskeInfoTextArea.editableProperty().setValue(false);
        centralPane.add(fadOgVæskeInfoHbox, 2, 1, 1, 2);

        // Label, textfield og knap til tilføjelse af væske med valgte litermængde
        Button tilføjVæskeButton = new MotherButton("Tilføj");
        tilføjVæskeButton.setOnAction(e -> tilfoejVaeske());
        HBox samleHBox = new HBox(literTextfield, tilføjVæskeButton);
        samleHBox.setSpacing(10);
        VBox tilføjVæskeVBox = new VBox();
        tilføjVæskeVBox.setSpacing(10);
        tilføjVæskeVBox.getChildren().addAll(new InfoLabel("Liter:"), samleHBox);
        centralPane.add(tilføjVæskeVBox, 2, 3);

        // Knapper til oprettelse og afbryd
        Button afbrydButton = new MotherButton("Afbryd");
        afbrydButton.setOnAction(e -> this.owner.drawDefault());
        BekræftFyldPaaFadButton opretButton = new BekræftFyldPaaFadButton("Opret Make");
        opretButton.setOnAction(event -> {
            boolean bekræftet = opretButton.bekræft(fadListView.getSelectionModel().getSelectedItem(), midlertidigHashMap);
            if (bekræftet) {
                this.owner.drawDefault();
            }
        });
        HBox afbrydOpretHbox = new HBox();
        afbrydOpretHbox.setSpacing(20);
        afbrydOpretHbox.getChildren().addAll(afbrydButton, opretButton);
        centralPane.add(afbrydOpretHbox, 3, 4);

        // Liste med væsker der er i det kommende batch, samt knap til fjernelse af væske
        Button fjernVaeskeButton = new MotherButton("Fjern");
        fjernVaeskeButton.setOnAction(e -> fjernVaeske());
        centralPane.add(new InfoLabel("Væsker i kommende make: "), 0, 3, 2, 1);
        centralPane.add(indholdListview, 0, 4, 3, 1);
        ;

        //Tilføjer centralpane til hovedpane
        this.add(centralPane, 0, 0);
    }

    private void vaerdierTilVaeskeListviews() {
        //Laver nogle lists til henholdsvis valgte destillater og makes
        ArrayList<Destillat> destlliatArraylist = Controller.getDestillater();
        ArrayList<Make> makeArrayList = Controller.getMakes();

        //Opdaterer værdierne i listViews
        this.destillatListview.getItems().clear();
        this.destillatListview.getItems().addAll(destlliatArraylist);
        this.makeListview.getItems().clear();
        this.makeListview.getItems().addAll(makeArrayList);
    }


    // Fjerner væske fra væskeindholdslisten
    private void fjernVaeske() {
        Væske væske = indholdListview.getSelectionModel().getSelectedItem();
        midlertidigHashMap.remove(væske);
        arrayListTilHashMap.remove(væske);
        indholdListview.getItems().remove(væske);
    }

    // Metode finder hvilke væsker man har valgt og tilføjer dem til et hashmap
    private void tilfoejVaeske() {
        if (literTextfield.getText().isEmpty()) {
            alert.setHeaderText("Mangler en valgt mængde");
            alert.setContentText("Mangler en valgt mængde");
            alert.showAndWait();
        } else {
            String literString = literTextfield.getText().trim();
            double liter = Double.parseDouble(literString);
            if (!destillatListview.getSelectionModel().isEmpty() && !indholderVæske(destillatListview)) {
                Destillat valgtDestillat = (Destillat) destillatListview.getSelectionModel().getSelectedItem();
                midlertidigHashMap.put(valgtDestillat, liter);
                arrayListTilHashMap.clear();
                arrayListTilHashMap.add(valgtDestillat);
                indholdListview.getItems().addAll(arrayListTilHashMap);
                literTextfield.clear();
            } else if (!makeListview.getSelectionModel().isEmpty() && !indholderVæske(makeListview)) {
                Make valgtMake = (Make) makeListview.getSelectionModel().getSelectedItem();
                midlertidigHashMap.put(valgtMake, liter);
                arrayListTilHashMap.clear();
                arrayListTilHashMap.add(valgtMake);
                indholdListview.getItems().addAll(arrayListTilHashMap);
                literTextfield.clear();
            } else {
                alert.setHeaderText("Vælg venligst en valid væske");
                alert.setContentText("Vælg venligst en valid væske");
                alert.showAndWait();
            }
        }
    }

    // Tjekker om hasmappet indeholder den valgte væske man vil indeholde.
    private Boolean indholderVæske(ListView<Væske> væskeListView) {
        boolean indeholderVæske = false;
        if (midlertidigHashMap.containsKey(væskeListView.getSelectionModel().getSelectedItem())) {
            indeholderVæske = true;
        }
        return indeholderVæske;
    }
}
