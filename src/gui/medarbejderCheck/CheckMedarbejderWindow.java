package gui.medarbejderCheck;

import application.controller.Controller;
import application.model.Medarbejder;
import gui.motherClasses.*;
import gui.opretDestillat.OpretDestillatPane;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.control.TextField;

public class CheckMedarbejderWindow extends MotherPane {
    private MotherTab owner;
    private TextField medarbejderNummerTextField;
    private TextArea textArea;
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

        // Label
        InfoLabel medarbejderNummerLabel = new InfoLabel("Medarbejdernummer: ");

        // Text Field
        medarbejderNummerTextField = new NumberTextField();
        medarbejderNummerTextField.setMaxWidth(50);

        // Check Button
        MotherButton checkMedarbejderNummerButton = new MotherButton("Check");
        checkMedarbejderNummerButton.setOnAction(e -> {
            checkMedarbejder();
        });

        //HBox til nummer checkning
        HBox medarbejderNummerHBox = new HBox();
        medarbejderNummerHBox.setSpacing(20);
        medarbejderNummerHBox.getChildren().addAll(medarbejderNummerLabel, medarbejderNummerTextField, checkMedarbejderNummerButton);

        //Tilføjer HBox til vinduet
        centralPane.add(medarbejderNummerHBox, 0, 1, 2, 1);

        // Back Button
        MotherButton buttonTilbage = new MotherButton("Annuller");
        buttonTilbage.setOnAction(e -> {
            owner.drawDefault();
        });

        // Ok Button
        buttonOk = new MotherButton("Ok");
        buttonOk.setDisable(true); //Skal disables, indtil et validt medarbejderNummer skrives ind
        buttonOk.setOnAction(e -> {
            openOpretDestillatWindow();
        });

        //HBox til knapper
        HBox buttonHBox = new HBox();
        buttonHBox.setSpacing(20);
        buttonHBox.setAlignment(Pos.CENTER_RIGHT);
        buttonHBox.getChildren().addAll(buttonTilbage, buttonOk);

        //Tilføjer knapper til vinduet
        centralPane.add(buttonHBox, 1, 3);

        // Text Area
        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setMaxWidth(300);
        centralPane.add(textArea, 0, 2, 2, 1);

        this.add(centralPane,0,0);
    }

    private void checkMedarbejder() {
        try {
            //getMedarbejder smider en exception hvis der ikke findes en medarbejder. parseInt kan kaste en NumberFormatException
            aktuelMedarbejder = Controller.getMedarbejder(Integer.parseInt(medarbejderNummerTextField.getText()));
            textArea.setText(aktuelMedarbejder.toString());
            //Hvis det lykkedes at finde en medarbejder, så tillader vi brugeren at klikke ok.
            buttonOk.setDisable(false);
        } catch (NumberFormatException e) {
            textArea.setText("Indtast venligst et tal.");
            buttonOk.setDisable(true);
        } catch (Exception e) {
            textArea.setText(e.getMessage().toString());
            buttonOk.setDisable(true);
        }
    }

    private void openOpretDestillatWindow() {
        OpretDestillatPane opretDestillatPane = new OpretDestillatPane("Opret Destillat Vindue", this.owner, this.aktuelMedarbejder);
        owner.setContent(opretDestillatPane);
    }
}
