package gui.tabs;

import gui.motherClasses.*;
import gui.opretWhisky.OpretWhiskyPane;
import gui.tapFad.TapFadPane;
import gui.whiskyPaaFlaskePane.WhiskyPaaFlaskePane;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class FraFadTilWhiskyTab extends MotherTab {
    public FraFadTilWhiskyTab(String text) {
        super(text);
        //Laver tab til opret og rediger
        MotherPane paneRediger = new MotherPane();
        this.initContentRediger(paneRediger);
        this.setContent(paneRediger);
    }

    private void initContentRediger(MotherPane pane) {
        //Opretter knap og billede til TapFad og sætter det i en VBox
        VBox tapFadVBox = new VBox();
        tapFadVBox.setSpacing(10);
        VerticalImageStackPane tapningsImage = new VerticalImageStackPane("/gui/images/tapning.png");
        tapFadVBox.getChildren().add(tapningsImage);
        MotherButton tadFadButton = new MainMenuButton("Lav Tapning");
        tadFadButton.setOnAction(event -> drawTapFadPane());
        tapFadVBox.getChildren().add(tadFadButton);
        tapFadVBox.setAlignment(Pos.BOTTOM_CENTER);
        pane.add(tapFadVBox, 0, 1);

        //Opretter knap og billede til Opret Whiskey og sætter det i en VBox
        VBox opretWhiskyVBox = new VBox();
        opretWhiskyVBox.setSpacing(10);
        VerticalImageStackPane fieldImage2 = new VerticalImageStackPane("/gui/images/SallWhisky.png");
        opretWhiskyVBox.getChildren().add(fieldImage2);
        MotherButton opretWhiskyButton = new MainMenuButton("Opret Whisky");
        opretWhiskyButton.setOnAction(event -> drawOpretWhiskyPane());
        opretWhiskyVBox.getChildren().add(opretWhiskyButton);
        opretWhiskyVBox.setAlignment(Pos.BOTTOM_CENTER);
        pane.add(opretWhiskyVBox, 1, 1);

        //Opretter knap og billede til Whiskey på flaske og sætter det i en VBox
        VBox whiskyPaaFlaskeVbox = new VBox();
        whiskyPaaFlaskeVbox.setSpacing(10);
        VerticalImageStackPane flaskeImage = new VerticalImageStackPane("/gui/images/flaske.png");
        whiskyPaaFlaskeVbox.getChildren().add(flaskeImage);
        Button whiskyPaaFlaskeButton = new MainMenuButton("Whisky På Flaske");
        whiskyPaaFlaskeButton.setOnAction(e -> drawWhiskyPaaFlaskePane());
        whiskyPaaFlaskeVbox.getChildren().add(whiskyPaaFlaskeButton);
        whiskyPaaFlaskeVbox.setAlignment(Pos.BOTTOM_CENTER);
        pane.add(whiskyPaaFlaskeVbox,2,1);
    }

    private void drawWhiskyPaaFlaskePane() {
        MotherPane whiskyPaaFlaske = new WhiskyPaaFlaskePane("Whisky På Flaske",this);
        this.setContent(whiskyPaaFlaske);
    }

    private void drawOpretWhiskyPane() {
        MotherPane opretWhiskyPane = new OpretWhiskyPane("Opret Whisky", this, null);
        this.setContent(opretWhiskyPane);
    }

    private void drawTapFadPane() {
        MotherPane tapFadPane = new TapFadPane("Opret Whisky", this);
        this.setContent(tapFadPane);
    }

    @Override
    public void drawDefault() {
        MotherPane paneRediger = new MotherPane();
        initContentRediger(paneRediger);
        this.setContent(paneRediger);
    }
}
