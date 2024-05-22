package gui.whiskyPaaFlaskePane;

import application.controller.Controller;
import application.model.Whisky;
import application.model.WhiskyProdukt;
import gui.motherClasses.*;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class WhiskyPaaFlaskePane extends MotherPaneWithImageBackground {
    private ListView<Whisky> whiskyListView = new ListView<>();
    private TextField flaskeStorrelseTextfield = new TextField();
    private TextField flaskeAntalTextfield = new TextField();


    public WhiskyPaaFlaskePane(String title, MotherTab owner) {
        super("/gui/images/mark.jpg",owner);

        //Laver central pane til alle centrale elemenet
        GridPane centralPane = new CentralPane();


        // Overskrift med listview med en listener
        centralPane.add(new MotherLabel("Vælg en whisky, samt størrelsen på flaskerne"), 0, 0, 2, 1);
        whiskyListView.getItems().addAll(Controller.getUbrugteWhiskyer());
        whiskyListView.getSelectionModel().selectedIndexProperty().addListener(observable -> {
            if (flaskeStorrelseTextfield.getText().isEmpty()) {
                flaskeAntalTextfield.setText("");
            } else {
                opdaterFlaskeAntal();
            }
        });
        centralPane.add(whiskyListView, 0, 1);

        // Tilføjer listener til
        flaskeStorrelseTextfield.textProperty().addListener(observable -> {
            if (flaskeStorrelseTextfield.getText().isEmpty() ||
                    whiskyListView.getSelectionModel().isEmpty()) {
            } else {
                opdaterFlaskeAntal();
            }
        });


        flaskeAntalTextfield.setEditable(false);


        // Opretter buttons, giver dem funktioner og tilføjer dem til en hBox
        Button paaFlaskeButton = new MotherButton("Hæld På Flaske");
        paaFlaskeButton.setOnAction(e -> haeldPaaFlaske());
        Button afbrydButton = new MotherButton("Afbryd");
        afbrydButton.setOnAction(e -> this.owner.drawDefault());
        HBox buttonHbox = new HBox();
        buttonHbox.setSpacing(10);
        buttonHbox.getChildren().addAll(afbrydButton, paaFlaskeButton);

        // Opretter vbox og tilføjer nodes til vBox
        VBox infoVbox = new VBox();
        infoVbox.setSpacing(10);
        infoVbox.getChildren().addAll(
                new InfoLabel("Flaske størrelse i cl:"),
                flaskeStorrelseTextfield,
                new InfoLabel("Antal flasker:"),
                flaskeAntalTextfield,
                buttonHbox);
        centralPane.add(infoVbox, 1, 1);


        this.add(centralPane, 0, 0);
    }

    // Udregne antallet af flasker der skal til for at man kan bruge alt whiskyen i en given flaske størrelse
    private void opdaterFlaskeAntal() {
        int flaskeStoerrelse = Integer.parseInt(flaskeStorrelseTextfield.getText());
        Whisky whisky = whiskyListView.getSelectionModel().getSelectedItem();
        WhiskyProdukt tempWhiskyProdukt = new WhiskyProdukt(whisky, flaskeStoerrelse);
        flaskeAntalTextfield.setText(String.valueOf(tempWhiskyProdukt.antalFlasker()));
    }

    // Opretter et whisky produkt til storage, sætter dens whiskys status til brugt
    // og fører brugeren tilbage til "main page"
    private void haeldPaaFlaske() {
        if (flaskeStorrelseTextfield.getText().isEmpty() ||
                whiskyListView.getSelectionModel().isEmpty()) {
        } else {
            Whisky whisky = whiskyListView.getSelectionModel().getSelectedItem();
            whisky.setBrugt(true);
            int flaskeStoerrelse = Integer.parseInt(flaskeStorrelseTextfield.getText());

            Controller.haeldPaaFlaske(whisky,flaskeStoerrelse);
            owner.drawDefault();
        }
    }
}
