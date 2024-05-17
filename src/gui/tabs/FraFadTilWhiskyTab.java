package gui.tabs;

import gui.motherClasses.*;
import gui.opretWhisky.OpretWhiskyPane;
import gui.tapFad.TapFadPane;
import javafx.geometry.Pos;
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
        VerticalImageStackPane fieldImage2 = new VerticalImageStackPane("/gui/images/flaske.png");
        opretWhiskyVBox.getChildren().add(fieldImage2);
        MotherButton opretWhiskyButton = new MainMenuButton("Opret Whisky");
        opretWhiskyButton.setOnAction(event -> drawOpretWhiskyPane());
        opretWhiskyVBox.getChildren().add(opretWhiskyButton);
        opretWhiskyVBox.setAlignment(Pos.BOTTOM_CENTER);
        pane.add(opretWhiskyVBox, 1, 1);
    }

    private void drawOpretWhiskyPane() {
        MotherPane opretWhiskyPane = new OpretWhiskyPane("Opret Whisky", this);
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
