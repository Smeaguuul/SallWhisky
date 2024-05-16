package gui.fyldPaafad;

import application.controller.Controller;
import application.model.Destillat;
import application.model.Fad;
import application.model.Make;
import application.model.Væske;
import gui.motherClasses.InfoLabel;
import gui.motherClasses.MotherButton;
import gui.motherClasses.MotherPane;
import gui.motherClasses.MotherTab;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import storage.Storage;

import java.util.ArrayList;
import java.util.HashMap;


public class FyldPaaFad extends MotherPane {
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
    private ArrayList<Destillat> destlliatArraylist = new ArrayList<>();
    private ArrayList<Make> makeArrayList = new ArrayList<>();
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public FyldPaaFad(String title, MotherTab owner) {
        this.owner = owner;

        // Opdeler de to væsketyper "destillat og make" i to arraylister
        vaerdierTilVaeskeListviews();

        // Tilføjer labels med tilhørende listview
        this.add(new InfoLabel("Destillater:"), 0, 0);
        this.add(new InfoLabel("Makes:"), 1, 0);
        this.add(new InfoLabel("Fade:"), 2, 0);
        this.add(destillatListview, 0, 1);
        this.add(makeListview, 1, 1);
        this.add(fadListView, 2, 1);

        // Listeners til listview, som sender info om valgte objekter til info feltene
        destillatListview.getItems().addAll(destlliatArraylist);
        destillatListview.getSelectionModel()
                .selectedIndexProperty()
                .addListener(observable -> {
                    if ((destillatListview.getSelectionModel().getSelectedItem() != null)) {
                        makeListview.getSelectionModel().select(null);
                        væskeInfoTextArea.setText(destillatListview.getSelectionModel().getSelectedItem().toString());
                    }
                });
        makeListview.getItems().addAll(makeArrayList);
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
        this.add(fadOgVæskeInfoHbox, 3, 0, 1, 2);

        // Sørger for at man kun kan inputte tal i liter texfieldet
        literTextfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, //TODO skal måske også bruges i opretFad og opretDestillat vinduerne, i stedet for bare at håndtere de fejl der nu engang kommer
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    literTextfield.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

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
        this.add(tilføjVæskeHbox, 3, 2);

        // Knapper til oprettelse og afbryd
        Button afbrydButton = new MotherButton("Afbryd");
        afbrydButton.setOnAction(e -> afbryd());
        Button opretButton = new MotherButton("Opret Make");
        opretButton.setOnAction(e -> opretMake());
        HBox afbrydOpretHbox = new HBox();
        afbrydOpretHbox.setSpacing(20);
        afbrydOpretHbox.getChildren().addAll(afbrydButton, opretButton);
        this.add(afbrydOpretHbox, 3, 3);

        // Liste med væsker der er i det kommende batch, samt knap til fjernelse af væske
        Button fjernVaeskeButton = new MotherButton("Fjern");
        fjernVaeskeButton.setOnAction(e -> fjernVaeske());
        this.add(new InfoLabel("Væsker i kommende make: "), 4, 0);
        this.add(indholdListview, 4, 1);
        this.add(fjernVaeskeButton, 4, 2);
    }

    private void vaerdierTilVaeskeListviews() {
        for (int i = 0; i < Storage.getVæsker().size(); i++) {
            if (Storage.getVæsker().get(i) instanceof Destillat) {
                Destillat destillat = (Destillat) Storage.getVæsker().get(i);
                this.destlliatArraylist.add(destillat);
            } else {
                Make make = (Make) Storage.getVæsker().get(i);
                this.makeArrayList.add(make);
            }
        }
    }

    private void opretMake() {
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
