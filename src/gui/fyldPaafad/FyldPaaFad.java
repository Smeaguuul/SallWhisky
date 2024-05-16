package gui.fyldPaafad;

import application.model.Destillat;
import application.model.Fad;
import application.model.Make;
import application.model.Væske;
import gui.motherClasses.*;
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
    private TextArea kommendeMakeTextArea = new TextArea();
    private TextArea fadInfoTextArea = new TextArea();
    private TextArea væskeInfoTextArea = new TextArea();
    private TextField literTextfield = new TextField();
    private ListView<Væske> indholdListview = new ListView<>();
    private HashMap<Væske, Double> midlertidigHashMap = new HashMap<>();
    private ArrayList<Væske> arrayListTilHashMap = new ArrayList<>();
    private ArrayList<Destillat> destlliatArraylist = new ArrayList<>();
    private ArrayList<Make> makeArrayList = new ArrayList<>();

    public FyldPaaFad(String title, MotherTab owner) {
        this.owner = owner;

        // Opdeler de to væsketyper "destillat og make" i to arraylister
        for (int i = 0; i < Storage.getVæsker().size(); i++) {
            if (Storage.getVæsker().get(i) instanceof Destillat) {
                Destillat destillat = (Destillat) Storage.getVæsker().get(i);
                this.destlliatArraylist.add(destillat);
            } else {
                Make make = (Make) Storage.getVæsker().get(i);
                this.makeArrayList.add(make);
            }
        }

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
                    makeListview.getSelectionModel().select(-1);
                    væskeInfoTextArea.setText(destillatListview.getSelectionModel().getSelectedItem().toString());
                });
        makeListview.getItems().addAll(makeArrayList);
        makeListview.getSelectionModel()
                .selectedIndexProperty()
                .addListener(observable -> {
                    destillatListview.getSelectionModel().select(-1);
                    væskeInfoTextArea.setText(makeListview.getSelectionModel().getSelectedItem().getOpbygning());
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

        // Textarea til kommende make
        this.add(new InfoLabel("Potentielt kommende make:"), 0, 2, 3, 1);
        this.add(kommendeMakeTextArea, 0, 3, 3, 1);
        kommendeMakeTextArea.editableProperty().setValue(false);

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
        Button opretButton = new MotherButton("Opret fad");
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
        String literString = literTextfield.getText().trim();
        double liter = Double.parseDouble(literString);
        if (!destillatListview.getSelectionModel().isEmpty()) {
            midlertidigHashMap.put(destillatListview.getSelectionModel().getSelectedItem(), liter);
            arrayListTilHashMap.clear();
            arrayListTilHashMap.addAll(midlertidigHashMap.keySet());
            System.out.println("midlertidigHashMap.size() = " + midlertidigHashMap.size());
            indholdListview.getItems().addAll(arrayListTilHashMap);
        } else if (!makeListview.getSelectionModel().isEmpty()) {
            midlertidigHashMap.put(makeListview.getSelectionModel().getSelectedItem(), liter);
            arrayListTilHashMap.clear();
            arrayListTilHashMap.addAll(midlertidigHashMap.keySet());
            System.out.println("midlertidigHashMap.size() = " + midlertidigHashMap.size());
            indholdListview.getItems().addAll(arrayListTilHashMap);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Vælg venligst en væske");
            alert.setContentText("Vælg venligst en væske");
            alert.showAndWait();
        }
    }
    //private Boolean checkDobbeltIndhold(){
        //det skal være i metoden oven over. for at sikre der ikke kommer to af den samme væske
    //}
}
