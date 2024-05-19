package gui.opretWhisky;

import application.controller.Controller;
import application.model.Fad;
import application.model.TapningsVæske;
import gui.motherClasses.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class OpretWhiskyPane extends MotherPaneWithImageBackground {
    private final MotherTab owner;
    private final TextField alkoholprocentTextField;
    private final ListView<TapningsVæske> valgteTapningsVæsker;
    private final TextField literVandTilFortyndingTextField;

    public OpretWhiskyPane(String title, MotherTab owner) {
        super("/gui/images/mark.jpg");
        this.owner = owner;

        //Pane til noderne
        GridPane centralPane = new CentralPane();

        //Overskrift
        MotherLabel instructionLabel = new MotherLabel("Udfyld venligst nedenstående for at oprette en ny whisky: ");
        centralPane.add(instructionLabel, 0, 0, 4, 1);

        //Laver et ListView til at vise alle tapningsvæsker
        ListView<TapningsVæske> tapningsVæsker = new ListView<>();
        tapningsVæsker.setMaxHeight(200);
        tapningsVæsker.getItems().addAll(Controller.getTapningsVæskerTilWhisky());
        InfoLabel tapningsVæskerLabel = new InfoLabel("Tapningsvæsker: ");
        centralPane.add(tapningsVæskerLabel, 0, 1, 2, 1);
        centralPane.add(tapningsVæsker, 0, 2, 2, 2);

        //Laver et ListView til de valgte tapningsVæsker
        valgteTapningsVæsker = new ListView<>();
        valgteTapningsVæsker.setMaxHeight(200);
        InfoLabel valgteTapningsVæskerLabel = new InfoLabel("Valgte tapningsvæsker: ");

        //Laver knapper til at rykke tapningsvæsker imellem de to
        MotherButton fjernButton = new MotherButton("Fjern Tapningsvæske");
        MotherButton tilføjButton = new MotherButton("Tilføj Tapningsvæske");

        //Tilføjer funktionalitet til tilføj knappen
        tilføjButton.setOnAction(event -> {
            //Finder det valgte element i listen til venstre
            TapningsVæske tapningsVæske = tapningsVæsker.getSelectionModel().getSelectedItem();
            if (tapningsVæske == null) {
                Alert warning = new BekræftWarningMedFejlInfo(new Exception("Du skal vælge et element at tilføje."));
                warning.showAndWait();
            } else {
                //Fjerner den fra den til venstre og tilføjer den til højre
                tapningsVæsker.getItems().remove(tapningsVæske);
                valgteTapningsVæsker.getItems().add(tapningsVæske);
            }
            opdaterEstimeretAlkoholProcent();
        });

        //Tilføjer funktionalitet til fjern knappen
        fjernButton.setOnAction(event -> {
            //Finder det valgte element i listen til venstre
            TapningsVæske tapningsVæske = valgteTapningsVæsker.getSelectionModel().getSelectedItem();
            if (tapningsVæske == null) {
                Alert warning = new BekræftWarningMedFejlInfo(new Exception("Du skal vælge et element at fjerne."));
                warning.showAndWait();
            } else {
                //Fjerner den fra den til venstre og tilføjer den til højre
                valgteTapningsVæsker.getItems().remove(tapningsVæske);
                tapningsVæsker.getItems().add(tapningsVæske);
            }
            opdaterEstimeretAlkoholProcent();
        });

        //Laver et text felt som viser estimeret alkoholprocent
        alkoholprocentTextField = new TextField();
        alkoholprocentTextField.setEditable(false);
        InfoLabel alkoholprocentLabel = new InfoLabel("Estimeret alkoholprocent:");

        //Laver et textfelt til input fortynding
        literVandTilFortyndingTextField = new TextField();
        InfoLabel literVandTilFortyndingLabel = new InfoLabel("Liter vand at fortynde med:"); //TODO bruge textfield som kun tagerimod tal

        //Laver et textarea til en evt. kommentar
        TextArea kommentarTextArea = new TextArea();
        kommentarTextArea.setMaxSize(150, 100);
        InfoLabel kommentarLabel = new InfoLabel("Skriv evt. kommentar her:");


        //Tilføjer alt til panet
        tilføjButton.setAlignment(Pos.CENTER_RIGHT);
        HBox hboxButtons = new HBox(fjernButton, tilføjButton);
        hboxButtons.setSpacing(65);
        centralPane.add(hboxButtons, 0, 4, 2, 1);

        centralPane.add(valgteTapningsVæskerLabel, 0, 5, 2, 1);
        centralPane.add(valgteTapningsVæsker, 0, 6, 2, 2);
        centralPane.addColumn(2, new Label(), literVandTilFortyndingLabel, alkoholprocentLabel, kommentarLabel);
        centralPane.addColumn(3, new Label(), literVandTilFortyndingTextField, alkoholprocentTextField);
        centralPane.add(kommentarTextArea, 3, 4, 1, 3);

         /*
        Opretter knapper og en HBox til at holde på dem.
         */
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(Pos.BASELINE_RIGHT);
        MotherButton afbrydButton = new MotherButton("Afbryd");
        BekræftOpretWhiskyButton bekræftButton = new BekræftOpretWhiskyButton("Opret Whisky");

        //Afbryd lukker blot vinduet
        afbrydButton.setOnAction(event -> {
            this.owner.drawDefault();
        });

        //Bekræft tjekker først om det indtastede information er gyldigt. Hvis det er gyldigt, så vises et bekræftelses vindue ellers vises en advarsel
        bekræftButton.setOnAction(actionEvent -> {
            //Giver det valgte input til commonclass
            List<TapningsVæske> valgteTapningsVæsker = this.valgteTapningsVæsker.getItems();
            String fortyndingsFaktorTekst = literVandTilFortyndingTextField.getText();
            int fortyndingsFaktor = 0;
            if (!fortyndingsFaktorTekst.isEmpty()) {
                fortyndingsFaktor = Integer.parseInt(fortyndingsFaktorTekst);
            }
            String kommentar = kommentarTextArea.getText();
            boolean bekræftet = bekræftButton.bekræft(valgteTapningsVæsker, fortyndingsFaktor, kommentar);
            if (bekræftet) {
                owner.drawDefault();
            }
            //TODO skal åbne opretWhisky hvis man har valgt det
        });

        //Tilføjer knapperne til pane
        buttonBox.getChildren().addAll(afbrydButton, bekræftButton);
        centralPane.add(buttonBox,2, 7,2,1);

        this.add(centralPane,0,0);
    }

    private void opdaterEstimeretAlkoholProcent() {
        //Finder værdierner
        ArrayList<TapningsVæske> valgteTapningsVæsker = new ArrayList<>(this.valgteTapningsVæsker.getItems());
        String literVandTekst = literVandTilFortyndingTextField.getText();
        double literVand = 0;
        if (!literVandTekst.isEmpty()) {
            literVand = Double.parseDouble(literVandTekst);
        }

        //Kalder Controller metoden
        double estimeretAlkoholProcent = Controller.udregnAlkoholProcent(valgteTapningsVæsker, literVand);

        //Printer svaret
        this.alkoholprocentTextField.setText(Math.round(100 * estimeretAlkoholProcent) / 100.00 + "%");

    }
}
