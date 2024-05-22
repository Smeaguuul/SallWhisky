package gui.tabs;

import gui.fyldPaafad.FyldPaaFad;
import gui.medarbejderCheck.CheckMedarbejderWindow;
import gui.motherClasses.*;
import gui.opretFad.OpretFadPane;
import gui.tildelLagerlokation.TildelLagerlokationPane;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class PaafyldTab extends MotherTab {
    public PaafyldTab(String text) {
        super(text);
        drawDefault();
    }

    @Override
    public void drawDefault() {
        MotherPane pane = new MotherPane();
        initContentPaafyld(pane);
        this.setContent(pane);
    }

    private void initContentPaafyld(MotherPane pane) {
        //Opretter knap og billede til opretDestillat og sætter det i en VBox
        VBox destillatVBox = new VBox();
        destillatVBox.setSpacing(10);
        VerticalImageStackPane fieldImage = new VerticalImageStackPane("/gui/images/markLogo.jpg");
        destillatVBox.getChildren().add(fieldImage);
        MotherButton opretDestillatButton = new MainMenuButton("Opret Destillat");
        opretDestillatButton.setOnAction(event -> openMedarbejderChecker());
        destillatVBox.getChildren().add(opretDestillatButton);
        destillatVBox.setAlignment(Pos.BOTTOM_CENTER);
        pane.add(destillatVBox,0,1);

        //Opretter knap og billede til opretFad og sætter det i en VBox
        VBox fadVBox = new VBox();
        fadVBox.setSpacing(10);
        fadVBox.setAlignment(Pos.BOTTOM_CENTER);
        VerticalImageStackPane fadImage = new VerticalImageStackPane("/gui/images/fad.png");
        fadVBox.getChildren().add(fadImage);
        MotherButton opretFadButton = new MainMenuButton("Opret Fad");
        opretFadButton.setOnAction(event -> openOpretFadWindow());
        fadVBox.getChildren().add(opretFadButton);
        pane.add(fadVBox,1,1);

        //Laver tab til opret og rediger
        VBox påfyldVBox = new VBox();
        påfyldVBox.setSpacing(10);
        påfyldVBox.setAlignment(Pos.BOTTOM_CENTER);
        VerticalImageStackPane påfyldImage = new VerticalImageStackPane("/gui/images/fad.png");
        påfyldVBox.getChildren().add(påfyldImage);
        MotherButton fyldpaaFadButton = new MainMenuButton("Fyld På Fad");
        fyldpaaFadButton.setOnAction(event -> fyldpaaFad());
        påfyldVBox.getChildren().add(fyldpaaFadButton);
        pane.add(påfyldVBox,2,1);

        //Opretter knap og billede til opretFad og sætter det i en VBox
        VBox lagerVBox = new VBox();
        lagerVBox.setSpacing(10);
        lagerVBox.setAlignment(Pos.BOTTOM_CENTER);
        VerticalImageStackPane fadImage2 = new VerticalImageStackPane("/gui/images/fad.png");
        lagerVBox.getChildren().add(fadImage2);
        MotherButton tildelLagerLokationButton = new MainMenuButton("Tildel lagerlokationer");
        tildelLagerLokationButton.setOnAction(event -> openGivFadLagerlokationWindow());
        lagerVBox.getChildren().add(tildelLagerLokationButton);
        pane.add(lagerVBox,3,1);
    }

    private void openGivFadLagerlokationWindow() {
        TildelLagerlokationPane tildelLagerlokationPane = new TildelLagerlokationPane("Tildel lagerlokation", this);
        this.setContent(tildelLagerlokationPane);
    }

    private void fyldpaaFad() {
        FyldPaaFad fyldpaaFad = new FyldPaaFad("Fyld På Fad",this);
        this.setContent(fyldpaaFad);
    }


    private void openOpretFadWindow() {
        OpretFadPane opretFadPane = new OpretFadPane("Opret Fad Vindue", this);
        this.setContent(opretFadPane);
    }

    private void openMedarbejderChecker() {
        MotherPane checkMedarbejderPane = new CheckMedarbejderWindow("Indtast medarbejder nummer", this);
        this.setContent(checkMedarbejderPane);
    }

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }
}
