package gui.tabs;

import gui.fyldPaafad.FyldPaaFad;
import gui.motherClasses.MotherButton;
import gui.motherClasses.MotherPane;
import gui.motherClasses.MotherTab;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class PaafyldTab extends MotherTab {
    public PaafyldTab(String text) {
        super(text);
        this.setStyle(" -fx-background-color: rgb(" + 133 + "," + 144 + ", " + 162 + ");");
        //Laver tab til opret og rediger
        MotherPane panePaafyld = new MotherPane();
        this.initContentPaafyld(panePaafyld);
        this.setContent(panePaafyld);
    }

    @Override
    public void drawDefault() {
        MotherPane pane = new MotherPane();
        initContentPaafyld(pane);
        this.setContent(pane);
    }

    private void initContentPaafyld(MotherPane pane) {
        // Button til at fylde på et fad
        Button fyldPaaFadButton = new MotherButton("Fyld på fad");
        fyldPaaFadButton.setOnAction(e -> fyldpaaFad());
        pane.add(fyldPaaFadButton,0,0);
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
