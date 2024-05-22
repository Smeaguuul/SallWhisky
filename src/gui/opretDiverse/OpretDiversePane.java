package gui.opretDiverse;

import application.controller.Controller;
import gui.motherClasses.*;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;

public class OpretDiversePane extends MotherPaneWithImageBackground {
    private TextField landTextfield = new TextField();
    private TextField postnrTextfield = new TextField();
    private TextField gadeNavnTextfield = new TextField();
    private TextField gadeNrTextfield = new TextField();
    private TextField markNavnTextField = new TextField();
    private TextField markBeskrivelseTextField = new TextField();
    private TextField malteriNavnTextField = new TextField();
    private TextField malteriBeskrivelseTextField = new TextField();

    public OpretDiversePane(MotherTab owner) {
        super("/gui/images/mark.jpg", owner);
        this.setVgap(70);
        centralPane.setMaxWidth(600);

        //Titel
        MotherLabel instructionLabel = new MotherLabel("Udfyld venligst en adresse, og enten en mark eller et malteri");
        centralPane.add(instructionLabel, 0, 0, 5, 1);

        class SegmentPane extends GridPane {
            public SegmentPane() {
                this.setBackground(new Background(new BackgroundFill(Paint.valueOf("White"), new CornerRadii(5), new Insets(-5))));
                this.setVgap(10);
                this.setHgap(10);
            }
        }

        //Laver en kasse til indtastning af adresse
        GridPane adressePane = new SegmentPane();
        adressePane.add(new InfoLabel("Adresse:"), 0, 0, 5, 1);
        InfoLabel landLabel = new InfoLabel("Land:");
        InfoLabel postNrLabel = new InfoLabel("Postnr:");
        InfoLabel gadeNavnLabel = new InfoLabel("Gade navn:");
        InfoLabel gadeNrLabel = new InfoLabel("Gade nr:");
        adressePane.addColumn(1, landLabel, postNrLabel);
        adressePane.addColumn(2, landTextfield, postnrTextfield);
        adressePane.addColumn(3, gadeNavnLabel, gadeNrLabel);
        adressePane.addColumn(4, gadeNavnTextfield, gadeNrTextfield);
        centralPane.add(adressePane, 0, 1, 5, 2);

        //Laver en kasse til mark
        GridPane markPane = new SegmentPane();
        markPane.add(new InfoLabel("Mark:"), 0, 0, 2, 1);
        markPane.addColumn(0, new InfoLabel("Navn:"), new InfoLabel("Beskrivelse:"));
        markPane.addColumn(1, markNavnTextField, markBeskrivelseTextField);
        centralPane.add(markPane, 0, 3, 2, 1);

        //Laver en kasse til malteri
        GridPane malteriPane = new SegmentPane();
        malteriPane.add(new InfoLabel("Malteri:"), 0, 0, 2, 1);
        malteriPane.addColumn(0, new InfoLabel("Navn:"), new InfoLabel("Beskrivelse:"));
        malteriPane.addColumn(1, malteriNavnTextField, malteriBeskrivelseTextField);
        centralPane.add(malteriPane, 3, 3, 2, 1);

        //Laver knap til opret mark
        MotherButton opretMarkButton = new MotherButton("Opret  Mark");
        centralPane.add(opretMarkButton, 1, 4);
        opretMarkButton.setOnAction(event -> {
            boolean lykkedes = opretMark();
            if (lykkedes) {
                owner.drawDefault();
            }
        });

        //Laver knap til opret malteri
        MotherButton opretMalteriButton = new MotherButton("Opret  Malteri");
        centralPane.add(opretMalteriButton, 4, 4);
        opretMalteriButton.setOnAction(event -> {
            boolean lykkedes = opretMalteri();
            if (lykkedes) {
                owner.drawDefault();
            }
        });

        //Laver en annuller knap
        MotherButton annullerButton = new MotherButton("Annuller");
        centralPane.add(annullerButton, 3, 4);
        annullerButton.setOnAction(event -> {
            this.owner.drawDefault();
        });

        //Tilføjer centralPane til det ydre pane
        this.add(centralPane, 0, 0);
    }

    private boolean opretMalteri() {
        boolean lykkedes = false;
        try {
            //Gemmer alt til adresse
            String land = this.landTextfield.getText();
            String postNr = this.postnrTextfield.getText();
            String gadeNavn = this.gadeNavnTextfield.getText();
            String gadeNr = this.gadeNrTextfield.getText();
            String malteriNavn = this.malteriNavnTextField.getText();
            String malteriBeskrivelse = this.malteriBeskrivelseTextField.getText();
            Controller.opretMalteri(land, postNr, gadeNavn, gadeNr, malteriNavn, malteriBeskrivelse);
            lykkedes = true;
        } catch (Exception e) {
            BekræftAlertMedInfo bekræftAlertMedInfo = new BekræftAlertMedInfo(e.getMessage());
            bekræftAlertMedInfo.showAndWait();
            lykkedes = false;
        }

        return lykkedes;
    }

    private boolean opretMark() {
        boolean lykkedes = false;

        try {
            //Gemmer alt til adresse
            String land = this.landTextfield.getText();
            String postNr = this.postnrTextfield.getText();
            String gadeNavn = this.gadeNavnTextfield.getText();
            String gadeNr = this.gadeNrTextfield.getText();
            String markNavn = this.markNavnTextField.getText();
            String markBeskrivelse = this.markBeskrivelseTextField.getText();
            Controller.opretMark(land, postNr, gadeNavn, gadeNr, markNavn, markBeskrivelse);
            lykkedes = true;
        } catch (Exception e) {
            BekræftAlertMedInfo bekræftAlertMedInfo = new BekræftAlertMedInfo(e.getMessage());
            bekræftAlertMedInfo.showAndWait();
            lykkedes = false;
        }

        return lykkedes;
    }
}
