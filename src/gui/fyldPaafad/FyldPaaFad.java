package gui.fyldPaafad;

import application.controller.Controller;
import application.model.Destillat;
import application.model.Fad;
import application.model.Make;
import application.model.Væske;
import gui.motherClasses.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import storage.Storage;

import java.util.ArrayList;
import java.util.HashMap;


public class FyldPaaFad extends MotherPaneWithImageBackground {
    private final MotherTab owner;
    private ListView<Væske> destillatListview = new ListView<>();
    private ListView<Væske> makeListview = new ListView<>();
    private ListView<Fad> fadListView = new ListView<>();
    private TextArea fadInfoTextArea = new TextArea();
    private TextArea væskeInfoTextArea = new TextArea();
    private TextField literTextfield = new TextField();
    private ListView<Væske> indholdListview = new ListView<>();
    private HashMap<Væske, Double> midlertidigHashMap = new HashMap<>();
    private ArrayList<Væske> arrayListTilHashMap = new ArrayList<>();

    private Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public FyldPaaFad(String title, MotherTab owner) {
        super("/gui/images/mark.jpg");
        this.owner = owner;

        //Laver et central pane
        CentralPane centralPane = new CentralPane();

        // Opdeler de to væsketyper "destillat og make" i to arraylister
        vaerdierTilVaeskeListviews();

        // Tilføjer labels med tilhørende listview
        centralPane.add(new InfoLabel("Destillater:"), 0, 0);
        centralPane.add(new InfoLabel("Makes:"), 1, 0);
        centralPane.add(new InfoLabel("Fade:"), 2, 0);
        centralPane.add(destillatListview, 0, 1);
        centralPane.add(makeListview, 1, 1);
        centralPane.add(fadListView, 2, 1);

        // Listeners til listview, som sender info om valgte objekter til info feltene
        destillatListview.getSelectionModel()
                .selectedIndexProperty()
                .addListener(observable -> {
                    if ((destillatListview.getSelectionModel().getSelectedItem() != null)) {
                        makeListview.getSelectionModel().select(null);
                        væskeInfoTextArea.setText(destillatListview.getSelectionModel().getSelectedItem().toString());
                    }
                });
        makeListview.getSelectionModel()
                .selectedIndexProperty()
                .addListener(observable -> {
                    if (!(makeListview.getSelectionModel().getSelectedItem() == null)) {
                        destillatListview.getSelectionModel().select(null);
                        væskeInfoTextArea.setText(makeListview.getSelectionModel().getSelectedItem().getOpbygning());
                    }
                });
        fadListView.getItems().addAll(Storage.getFade());
        fadListView.getSelectionModel()
                .selectedIndexProperty()
                .addListener(observable -> {
                    fadInfoTextArea.setText(fadListView.getSelectionModel().getSelectedItem().allFadInfo());
                });

        //Hbox med fad og væske info
        VBox fadOgVæskeInfoHbox = new VBox();
        fadOgVæskeInfoHbox.getChildren().addAll(
                new InfoLabel("Fad info:"),
                fadInfoTextArea,
                new InfoLabel("Væske info:"),
                væskeInfoTextArea
        );
        fadInfoTextArea.editableProperty().setValue(false);
        væskeInfoTextArea.editableProperty().setValue(false);
        centralPane.add(fadOgVæskeInfoHbox, 3, 0, 1, 2);

        // Label, textfield og knap til tilføjelse af væske med valgte litermængde
        Button tilføjVæskeButton = new MotherButton("Tilføj");
        tilføjVæskeButton.setOnAction(e -> tilfoejVaeske());
        HBox tilføjVæskeHbox = new HBox();
        tilføjVæskeHbox.setSpacing(20);
        tilføjVæskeHbox.getChildren().addAll(
                new InfoLabel("Liter:"),
                literTextfield,
                tilføjVæskeButton
        );
        centralPane.add(tilføjVæskeHbox, 3, 2);

        // Knapper til oprettelse og afbryd
        Button afbrydButton = new MotherButton("Afbryd");
        afbrydButton.setOnAction(e -> afbryd());
        Button opretButton = new MotherButton("Opret Make");
        opretButton.setOnAction(e -> opretMake());
        HBox afbrydOpretHbox = new HBox();
        afbrydOpretHbox.setSpacing(20);
        afbrydOpretHbox.getChildren().addAll(afbrydButton, opretButton);
        centralPane.add(afbrydOpretHbox, 3, 3);

        // Liste med væsker der er i det kommende batch, samt knap til fjernelse af væske
        Button fjernVaeskeButton = new MotherButton("Fjern");
        fjernVaeskeButton.setOnAction(e -> fjernVaeske());
        centralPane.add(new InfoLabel("Væsker i kommende make: "), 4, 0);
        centralPane.add(indholdListview, 4, 1);
        centralPane.add(fjernVaeskeButton, 4, 2);

        //Tilføjer centralpane til hovedpane
        this.add(centralPane,0,0);
    }

    private void vaerdierTilVaeskeListviews() {
        ArrayList<Destillat> destlliatArraylist = new ArrayList<>();
        ArrayList<Make> makeArrayList = new ArrayList<>();
        for (int i = 0; i < Storage.getVæsker().size(); i++) {
            if (Storage.getVæsker().get(i) instanceof Destillat && Storage.getVæsker().get(i).getNuværendeMængde() > 0) {
                Destillat destillat = (Destillat) Storage.getVæsker().get(i);
                destlliatArraylist.add(destillat);
            } else if (Storage.getVæsker().get(i).getNuværendeMængde() > 0){ //TODO smid det her ud i en fælles ydre if
                Make make = (Make) Storage.getVæsker().get(i);
                makeArrayList.add(make);
            }
        }
        this.makeListview.getItems().clear();
        this.destillatListview.getItems().clear();
        this.destillatListview.getItems().addAll(destlliatArraylist);
        this.makeListview.getItems().addAll(makeArrayList);
    }

    private void opretMake() {
        for (Væske væske : midlertidigHashMap.keySet()) {
            System.out.println(væske);
        }
        if (fadListView.getSelectionModel().isEmpty()) {
            alert.setHeaderText("Vælg venligst et fad");
            alert.setContentText("Vælg venligst et fad");
            alert.showAndWait();
        } else if (midlertidigHashMap.size() == 0) {
            alert.setHeaderText("Vælg venligst en væske");
            alert.setContentText("Vælg venligst en væske");
            alert.showAndWait();
        } else {
            Controller.opretMake(fadListView.getSelectionModel().getSelectedItem(), midlertidigHashMap);
            vaerdierTilVaeskeListviews();
        }
    }

    // Fjerner væske fra væskeindholdslisten
    private void fjernVaeske() {
        Væske væske = indholdListview.getSelectionModel().getSelectedItem();
        midlertidigHashMap.remove(væske);
        arrayListTilHashMap.remove(væske);
        indholdListview.getItems().remove(væske);
    }

    // Tilbage til forsiden, samt nulstiller det midlertidige hashmap
    private void afbryd() {
        midlertidigHashMap.clear();
        arrayListTilHashMap.clear();
        this.owner.drawDefault();
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

    // Tjekker om hasmappet indeholder den valgte væske man vil indeholder.
    private Boolean indholderVæske(ListView<Væske> væskeListView) {
        if (midlertidigHashMap.containsKey(væskeListView.getSelectionModel().getSelectedItem())) {
            return true;
        } else return false;
    }
}
