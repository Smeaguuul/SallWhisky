package gui.tabs;

import gui.fadUdtræk.FadUdtrækWindow;
import gui.motherClasses.*;
import gui.whiskyListe.WhiskyUdtrækWindow;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class UdtrækFadTap extends MotherTab {
    public UdtrækFadTap(String text) {
        super(text);
        drawDefault();
    }

    @Override
    public void drawDefault() {
        MotherPane pane = new MotherPane();
        initContentUdtræk(pane);
        this.setContent(pane);
    }

    private void initContentUdtræk(MotherPane pane) {
        //Laver tab til fad Udtræk
        VBox fadUdtrækVBox = new VBox();
        fadUdtrækVBox.setSpacing(10);
        fadUdtrækVBox.setAlignment(Pos.BOTTOM_CENTER);
        VerticalImageStackPane udtrækImage = new VerticalImageStackPane("/gui/images/markLogo.jpg");
        fadUdtrækVBox.getChildren().add(udtrækImage);
        MotherButton hentUdtrækButton = new MainMenuButton("Fad Udtræk");
        hentUdtrækButton.setOnAction(event -> openUdtrækWindow());
        fadUdtrækVBox.getChildren().add(hentUdtrækButton);
        pane.add(fadUdtrækVBox,1,1);


        //Laver tab til opret og rediger
        VBox whiskyVBox = new VBox();
        whiskyVBox.setSpacing(10);
        whiskyVBox.setAlignment(Pos.BOTTOM_CENTER);
        VerticalImageStackPane whiskyImage = new VerticalImageStackPane("/gui/images/markLogo.jpg");
        whiskyVBox.getChildren().add(whiskyImage);
        MotherButton whiskyButton = new MainMenuButton("Whisky Udtræk");
        whiskyButton.setOnAction(event -> openWhiskyWindow());
        whiskyVBox.getChildren().add(whiskyButton);
        pane.add(whiskyVBox,2,1);
    }

    private void openWhiskyWindow() {
        WhiskyUdtrækWindow whiskyUdtrækWindow = new WhiskyUdtrækWindow();
        this.setContent(whiskyUdtrækWindow);
    }

    private void openUdtrækWindow() {
        FadUdtrækWindow fadUdtrækWindow = new FadUdtrækWindow();
        this.setContent(fadUdtrækWindow);
    }
}
