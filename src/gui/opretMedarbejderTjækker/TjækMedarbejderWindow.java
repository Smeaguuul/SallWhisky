package gui.opretMedarbejderTjækker;

import application.controller.Controller;
import application.model.Medarbejder;
import gui.motherClasses.InfoLabel;
import gui.motherClasses.MotherButton;
import gui.motherClasses.MotherLabel;
import gui.motherClasses.MotherPane;
import gui.opretDestillat.OpretDestillatWindow;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import storage.Storage;

import java.util.NoSuchElementException;

public class TjækMedarbejderWindow extends Stage {
    private Stage owner;
    public TjækMedarbejderWindow(String title, Stage owner) {
        MotherPane pane = new MotherPane();
        Scene scene = new Scene(pane);
        this.initOwner(owner);
        this.initContent(pane);
        this.setTitle(title);
        this.setScene(scene);
        this.owner = owner;
    }
    private void initContent(MotherPane pane) {
        // Label
        InfoLabel info = new InfoLabel("Ind nummer: ");
        pane.add(info, 1,0);

        // Text Field
        TextField textField = new TextField();
        pane.add(textField, 1, 1);

        // Listener der fjerner alle tegn der ikke er tal, fra textfeltet.
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        // Check Button
        MotherButton checkButton = new MotherButton("Check");
        pane.add(checkButton, 2,1);
        checkButton.setOnAction(e -> {
            try {
                textField.setText(Controller.getMedarbejder(Integer.parseInt(textField.getText())).toString());
            }
            catch (NoSuchElementException exception) {
                textField.setText(exception.getMessage());
            }
            if(Controller.getMedarbejder(Integer.parseInt(textField.getText())) != null) {
            }
            else {
                textField.setText("Mejderbejder med det nummer eksistere ikke");
            }
        });

        // Back Button
        MotherButton buttonTilbage = new MotherButton("Annuller");
        pane.add(buttonTilbage, 0, 3);
        buttonTilbage.setOnAction(e -> {
            this.close();
        });

        // Ok Button
        MotherButton buttonOk = new MotherButton("Ok");
        buttonOk.setDisable(true);
        buttonOk.setOnAction(e -> {
            openOpretDestillatWindow();
            this.close();
        });
        pane.add(buttonOk, 1, 3);

        // Text Area
        TextArea textArea = new TextArea();

        textArea.setEditable(false);
        pane.add(textArea, 1, 2);
    }
    private void openOpretDestillatWindow() {
        OpretDestillatWindow opretDestillatWindow = new OpretDestillatWindow("Opret Destillat Vindue", owner);
        opretDestillatWindow.showAndWait();
    }
}
