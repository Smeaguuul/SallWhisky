package gui.tabs;

import gui.medarbejderCheck.CheckMedarbejderWindow;
import gui.motherClasses.*;
import gui.opretFad.OpretFadPane;
import gui.opretForhandler.OpretForhandlerPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class OpretRedigerTab extends gui.motherClasses.MotherTab {
    public OpretRedigerTab(String text) {
        super(text);
        //Laver tab til opret og rediger
        MotherPane paneRediger = new MotherPane();
        this.initContentRediger(paneRediger);
        this.setContent(paneRediger);
    }

    private void initContentRediger(MotherPane pane) {
        //Opretter knap og billede til opret Destillat
        VBox destillatVBox = new VBox();
        destillatVBox.setSpacing(10);
        VerticalImageStackPane fieldImage = new VerticalImageStackPane("/gui/images/mark.jpg");
        destillatVBox.getChildren().add(fieldImage);
        MotherButton opretDestillatButton = new MainMenuButton("Opret Destillat");
        opretDestillatButton.setOnAction(event -> openMedarbejderChecker());
        destillatVBox.getChildren().add(opretDestillatButton);
        destillatVBox.setAlignment(Pos.BOTTOM_CENTER);
        pane.add(destillatVBox,0,1);


        //Opretter knap til opretFad
        VBox fadVBox = new VBox();
        fadVBox.setSpacing(10);
        fadVBox.setAlignment(Pos.BOTTOM_CENTER);
        VerticalImageStackPane fadImage = new VerticalImageStackPane("/gui/images/fad.jpg");
        fadVBox.getChildren().add(fadImage);
        MotherButton opretFadButton = new MainMenuButton("Opret Fad");
        opretFadButton.setOnAction(event -> openOpretFadWindow());
        fadVBox.getChildren().add(opretFadButton);
        pane.add(fadVBox,1,1);

        //Opretter knap til opretning af medarbejder
        VBox medarbejderVBox = new VBox();
        medarbejderVBox.setSpacing(10);
        medarbejderVBox.setAlignment(Pos.BOTTOM_CENTER);
        VerticalImageStackPane medarbejderImage = new VerticalImageStackPane("/gui/images/fad.jpg");
        medarbejderVBox.getChildren().add(medarbejderImage);
        MotherButton redigerMedarbejderButton = new MainMenuButton("Adminstrer Medarbejdere");
        redigerMedarbejderButton.setOnAction(event -> openOpretFadWindow());
        medarbejderVBox.getChildren().add(redigerMedarbejderButton);
        pane.add(medarbejderVBox,2,1);

        //Opret forhandler
        MotherButton opretForhandlerButton = new MotherMainMenuButton("Opret Forhandler");
        opretForhandlerButton.setOnAction(event -> openOpretForhandlerPane());
        //pane.add(opretForhandlerButton,1,2);

    }

    private void openOpretForhandlerPane() {
        MotherPane opretForhandlerPane = new OpretForhandlerPane("Opret Forhandler", this);
        this.setContent(opretForhandlerPane);
    }

    @Override
    public void drawDefault(){
        MotherPane paneRediger = new MotherPane();
        initContentRediger(paneRediger);
        this.setContent(paneRediger);
    }

    private void openMedarbejderChecker() {
        MotherPane checkMedarbejderPane = new CheckMedarbejderWindow("Indtast medarbejder nummer", this);
        this.setContent(checkMedarbejderPane);
    }

    private void openOpretFadWindow() {
        OpretFadPane opretFadPane = new OpretFadPane("Opret Fad Vindue", this);
        this.setContent(opretFadPane);
    }
}
