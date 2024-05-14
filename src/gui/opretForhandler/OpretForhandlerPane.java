package gui.opretForhandler;

import application.controller.Controller;
import application.model.Forhandler;
import gui.motherClasses.*;
import gui.tabs.OpretRedigerTab;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import storage.Storage;

public class OpretForhandlerPane extends MotherPane {
    private final TextField navnTextField;
    private final TextField regionTextField;
    private final TextField landTextField;
    private MotherTab owner;
    ListView<Forhandler> forhandlerListview;

    public OpretForhandlerPane(String title, OpretRedigerTab owner) {
        this.owner = owner;

        MotherLabel instructionLabel = new MotherLabel("Udfyld venligst nedenstående for at oprette en ny forhandler: ");
        this.add(instructionLabel, 0, 0, 2, 1);

        // Listview med forhandlere
        forhandlerListview = new ListView<>();
        this.add(new MotherLabel("Forhandlere:"), 2, 0);
        this.add(forhandlerListview, 2, 1, 1, 4);
        forhandlerListview.getItems().setAll(Storage.getForhandlere());

        //Indtaster navn
        InfoLabel navnLabel = new InfoLabel("Navn på forhandler:");
        navnTextField = new TextField();
        this.add(navnLabel, 0, 1);
        this.add(navnTextField, 1, 1);

        //Indtaster region
        InfoLabel regionLabel = new InfoLabel("Forhandlerens region:");
        regionTextField = new TextField();
        this.add(regionLabel, 0, 2);
        this.add(regionTextField, 1, 2);

        //Indtaster land
        InfoLabel landLabel = new InfoLabel("Forhandlerens land:");
        landTextField = new TextField();
        this.add(landLabel, 0, 3);
        this.add(landTextField, 1, 3);

         /*
        Opretter knapper og en HBox til at holde på dem.
        BekræftFadButton typen står for funktionalitet ift. at oprette
         */
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(Pos.BASELINE_RIGHT);
        MotherButton afbrydButton = new MotherButton("Afbryd");
        BekræftForhandlerButton bekræftButton = new BekræftForhandlerButton("Bekræft");

        //Afbryd lukker blot vinduet
        afbrydButton.setOnAction(event -> {
            this.owner.drawDefault();
        });

        //Bekræft tjekker først om det indtastede information er gyldigt. Hvis det er gyldigt, så vises et bekræftelses vindue ellers vises en advarsel
        bekræftButton.setOnAction(event -> opretForhandler());

        //Tilføjer knapperne til pane
        buttonBox.getChildren().addAll(afbrydButton, bekræftButton);
        this.add(buttonBox, 1, 4);
    }

    private void opretForhandler() {
        if (navnTextField.getText().isEmpty() || regionTextField.getText().isEmpty() || landTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Udfyld venligst tekst felterne");
            alert.setContentText("Udfyld de tomme felter");
            alert.showAndWait();
        } else {
            Controller.opretForhandler(navnTextField.getText(), regionTextField.getText(), landTextField.getText());
            forhandlerListview.getItems().setAll(Storage.getForhandlere());
            navnTextField.clear();
            regionTextField.clear();
            landTextField.clear();
        }
    }
}
