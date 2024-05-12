package gui.medarbejderCheck;

import application.controller.Controller;
import application.model.Medarbejder;
import gui.motherClasses.InfoLabel;
import gui.motherClasses.MotherButton;
import gui.motherClasses.MotherLabel;
import gui.motherClasses.MotherPane;
import gui.opretDestillat.OpretDestillatWindow;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.util.NoSuchElementException;

public class CheckMedarbejderWindow extends Stage {
    private Stage owner;
    private TextField textField = new TextField();
    private TextArea textArea = new TextArea();
    private MotherButton buttonOk;
    private Medarbejder aktuelMedarbejder;

    public CheckMedarbejderWindow(String title, Stage owner) {
        MotherPane pane = new MotherPane();
        Scene scene = new Scene(pane);
        this.initOwner(owner);
        this.initContent(pane);
        this.setTitle(title);
        this.setScene(scene);
        this.owner = owner;
    }

    private void initContent(MotherPane pane) {
        //Titel
        MotherLabel instructionLabel = new MotherLabel("Indtast venlist medarbejdernummer.");
        pane.add(instructionLabel, 0, 0,2,1);

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
        hBox.getChildren().add(checkButton);

        //Tilføjer HBox til vinduet
        pane.add(hBox,0,1,2,1);

        //HBox til knapper
        HBox buttonHBox = new HBox();
        buttonHBox.setSpacing(20);

        // Back Button
        MotherButton buttonTilbage = new MotherButton("Annuller");
        buttonTilbage.setOnAction(e -> {
            this.close();
        });
        buttonHBox.getChildren().add(buttonTilbage);

        // Ok Button
        buttonOk = new MotherButton("Ok");
        buttonOk.setDisable(true);
        buttonOk.setOnAction(e -> {
            openOpretDestillatWindow();
            this.close();
        });
        buttonHBox.getChildren().add(buttonOk);
        buttonHBox.setAlignment(Pos.CENTER_RIGHT);

        //Tilføjer knapper til vinduet
        pane.add(buttonHBox,1,3);


        // Text Area
        textArea.setEditable(false);
        textArea.setMaxWidth(300);
        pane.add(textArea, 0, 2,2,1);

        pane.setMinSize(100,100);
    }

    private void checkMedarbejder() {
        try {
            aktuelMedarbejder = Controller.getMedarbejder(Integer.parseInt(textField.getText()));
            textArea.setText(aktuelMedarbejder.toString());
            //Hvis det lykkedes at finde en medarbejder, så tillader vi brugeren at klikke ok.
            buttonOk.setDisable(false);
        } catch (NoSuchElementException e) {
            textArea.setText(e.getMessage().toString());
        } catch (NumberFormatException e) {
            textArea.setText("Indtast venligst et tal.");
        }
    }

    private void openOpretDestillatWindow() {
        OpretDestillatWindow opretDestillatWindow = new OpretDestillatWindow("Opret Destillat Vindue", owner, aktuelMedarbejder);
        opretDestillatWindow.showAndWait();
    }
}
