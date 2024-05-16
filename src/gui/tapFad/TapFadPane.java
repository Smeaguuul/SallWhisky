package gui.tapFad;

import application.controller.Controller;
import application.model.Fad;
import gui.motherClasses.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class TapFadPane extends MotherPaneWithImageBackground {
    private final MotherTab owner;

    public TapFadPane(String title, MotherTab owner) {
        super("/gui/images/mark.jpg");
        this.owner = owner;

        //Laver central pane til alle centrale elemenet
        GridPane centralPane = new CentralPane();

        //Overskrift
        MotherLabel instructionLabel = new MotherLabel("Vælg hvilket fad du vil tappe fra, og Hvor meget");
        centralPane.add(instructionLabel, 0, 0, 2, 1);

        //Laver et ListView til at vise alle modne fade
        ListView<Fad> modneFadListView = new ListView<>();
        modneFadListView.getItems().addAll(Controller.getModneFade());
        InfoLabel modneFadeLabel = new InfoLabel("Fade klar til tapning");
        centralPane.addColumn(0, modneFadeLabel);
        centralPane.add(modneFadListView, 0, 2, 1, 4);

        //Laver et textArea til ekstra info som det valgte fad
        TextArea fadInfoTextArea = new TextArea();
        fadInfoTextArea.setMaxSize(310,200);
        fadInfoTextArea.setEditable(false);
        InfoLabel fadInfoLabel = new InfoLabel("Ekstra info om dit valgte fad:");
        centralPane.addColumn(1, fadInfoLabel, fadInfoTextArea);

        //Laver et listener så textareaet automatisk opdateres
        modneFadListView.getSelectionModel().selectedIndexProperty().addListener(observable -> {
            fadInfoTextArea.setText(modneFadListView.getSelectionModel().getSelectedItem().allFadInfo());
        });

        //Laver et textfield til input at tapningsmængde
        TextField tapningsMængdeTextField = new TextField();
        InfoLabel tapningsMængdeLabel = new InfoLabel("Liter Whisky Tappet: ");
        HBox tapningsMængdeHBox = new HBox(tapningsMængdeLabel, tapningsMængdeTextField);
        tapningsMængdeHBox.setSpacing(10);
        centralPane.addColumn(1, tapningsMængdeHBox);

        //Laver et textfield til input at alkoholprocent
        TextField alkoholprocentTextField = new TextField();
        InfoLabel alkoholprocentLabel = new InfoLabel("Målt alkoholprocent: "); //TODO bruge textfield som kun tagerimod tal
        HBox alkoholprocentHBox = new HBox(alkoholprocentLabel, alkoholprocentTextField);
        alkoholprocentHBox.setSpacing(10);
        centralPane.addColumn(1, alkoholprocentHBox);

        //Laver en knap, så man kan vælge om man vælg lave en Single Cask whisky direkte efter det her
        CheckBox singleCaskToggle = new CheckBox();
        InfoLabel singleCaskLabel = new InfoLabel("Er Single Cask: ");
        HBox singleCaskHBox = new HBox(singleCaskLabel, singleCaskToggle);
        singleCaskHBox.setSpacing(10);
        centralPane.addColumn(1, singleCaskHBox);

        /*
        Opretter knapper og en HBox til at holde på dem.
         */
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(Pos.BASELINE_RIGHT);
        MotherButton afbrydButton = new MotherButton("Afbryd");
        BekræftTapFadButtton bekræftButton = new BekræftTapFadButtton("Tap Fad");

        //Afbryd lukker blot vinduet
        afbrydButton.setOnAction(event -> {
            this.owner.drawDefault();
        });

        //Bekræft tjekker først om det indtastede information er gyldigt. Hvis det er gyldigt, så vises et bekræftelses vindue ellers vises en advarsel
        bekræftButton.setOnAction(actionEvent -> {
            //Giver det valgte input til commonclass
            CommonClass commonClass = new CommonClass(modneFadListView.getSelectionModel().getSelectedItem(), Double.parseDouble(alkoholprocentTextField.getText()), Integer.parseInt(tapningsMængdeTextField.getText()));
            boolean bekræftet = bekræftButton.bekræft(commonClass);
            if (bekræftet) {
                owner.drawDefault();
            }
            //TODO skal åbne opretWhisky hvis man har valgt det
        });

        //Tilføjer knapperne til pane
        buttonBox.getChildren().addAll(afbrydButton, bekræftButton);
        centralPane.addColumn(1, buttonBox);

        this.add(centralPane,0,0);
    }
}
