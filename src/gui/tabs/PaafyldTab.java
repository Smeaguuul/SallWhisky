package gui.tabs;

import gui.fyldPaafad.FyldPaaFad;
import gui.motherClasses.*;
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
        //Laver tab til opret og rediger
        VBox fadVBox = new VBox();
        fadVBox.setSpacing(10);
        fadVBox.setAlignment(Pos.BOTTOM_CENTER);
        VerticalImageStackPane påfyldImage = new VerticalImageStackPane("/gui/images/tapning.png");
        fadVBox.getChildren().add(påfyldImage);
        MotherButton fyldpaaFadButton = new MainMenuButton("Fyld På Fad");
        fyldpaaFadButton.setOnAction(event -> fyldpaaFad());
        fadVBox.getChildren().add(fyldpaaFadButton);
        pane.add(fadVBox,1,1);
    }

    private void fyldpaaFad() {
        FyldPaaFad fyldpaaFad = new FyldPaaFad("Fyld På Fad",this);
        this.setContent(fyldpaaFad);
    }

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }
}
