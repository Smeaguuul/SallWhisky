package gui.medarbejderCheck;

import application.controller.Controller;
import application.model.Medarbejder;
import gui.motherClasses.*;
import gui.opretDestillat.OpretDestillatPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.control.TextField;

import java.util.NoSuchElementException;

public class CheckMedarbejderWindow extends MotherPane {
    private MotherTab owner;
    private TextField textField = new TextField();
    private TextArea textArea = new TextArea();
    private MotherButton buttonOk;
    private Medarbejder aktuelMedarbejder;

    public CheckMedarbejderWindow(String title, MotherTab owner) {
        this.owner = owner;
        this.setBackground(new Background(new BackgroundImage(new Image("/gui/images/mark.jpg"), null, null, null, new BackgroundSize(1600,900,false,false,true,true)), null, null));
        this.setAlignment(Pos.CENTER);

        //Opretter et pane til at holde på de centrale elementer
        GridPane centralPane = new CentralPane();

        //Titel
        MotherLabel instructionLabel = new MotherLabel("Indtast venlist medarbejdernummer.");
        centralPane.add(instructionLabel, 0, 0, 2, 1);

        //HBox til nummer checkning
        HBox hBox = new HBox();
        hBox.setSpacing(20);

        // Label
        InfoLabel info = new InfoLabel("Medarbejdernummer: ");
        hBox.getChildren().add(info);

        // Text Field
        textField.setMaxWidth(50);
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        hBox.getChildren().add(textField);

        // Check Button
        MotherButton checkButton = new MotherButton("Check");
        checkButton.setOnAction(e -> {
            checkMedarbejder();
        });
        checkButton.setDefaultButton(true);
        hBox.getChildren().add(checkButton);

        //Tilføjer HBox til vinduet
        centralPane.add(hBox, 0, 1, 2, 1);

        //HBox til knapper
        HBox buttonHBox = new HBox();
        buttonHBox.setSpacing(20);

        // Back Button
        MotherButton buttonTilbage = new MotherButton("Annuller");
        buttonTilbage.setOnAction(e -> {
            owner.drawDefault();
        });
        buttonHBox.getChildren().add(buttonTilbage);

        // Ok Button
        buttonOk = new MotherButton("Ok");
        buttonOk.setDisable(true);
        buttonOk.setOnAction(e -> {
            openOpretDestillatWindow();
        });
        buttonHBox.getChildren().add(buttonOk);
        buttonHBox.setAlignment(Pos.CENTER_RIGHT);

        //Tilføjer knapper til vinduet
        centralPane.add(buttonHBox, 1, 3);


        // Text Area
        textArea.setEditable(false);
        textArea.setMaxWidth(300);
        centralPane.add(textArea, 0, 2, 2, 1);

        this.add(centralPane,0,0);
    }

    private void checkMedarbejder() {
        try {
            aktuelMedarbejder = Controller.getMedarbejder(Integer.parseInt(textField.getText()));
            textArea.setText(aktuelMedarbejder.toString());
            //Hvis det lykkedes at finde en medarbejder, så tillader vi brugeren at klikke ok.
            buttonOk.setDisable(false);
        } catch (NoSuchElementException e) {
            textArea.setText(e.getMessage().toString());
            buttonOk.setDisable(true);
        } catch (NumberFormatException e) {
            textArea.setText("Indtast venligst et tal.");
            buttonOk.setDisable(true);
        } catch (IllegalArgumentException e) {
            textArea.setText(e.getMessage().toString());
            buttonOk.setDisable(true);
        }
    }

    private void openOpretDestillatWindow() {
        OpretDestillatPane opretDestillatPane = new OpretDestillatPane("Opret Destillat Vindue", this.owner, this.aktuelMedarbejder);
        owner.setContent(opretDestillatPane);
    }
}
