package gui.tabs;

import gui.medarbejderCheck.CheckMedarbejderWindow;
import gui.motherClasses.MotherButton;
import gui.motherClasses.MotherMainMenuButton;
import gui.motherClasses.MotherPane;
import gui.opretFad.OpretFadPane;

public class OpretRedigerTab extends gui.motherClasses.MotherTab {
    public OpretRedigerTab(String text) {
        super(text);
        //Laver tab til opret og rediger
        MotherPane paneRediger = new MotherPane();
        this.initContentRediger(paneRediger);
        this.setContent(paneRediger);
    }

    private void initContentRediger(MotherPane pane) {
        //Opretter knap til opret Destillat
        MotherButton opretDestillatButton = new MotherMainMenuButton("Opret Destillat");
        opretDestillatButton.setOnAction(event -> openMedarbejderChecker());
        pane.add(opretDestillatButton, 0, 1);

        //Opretter knap til opretFad
        MotherButton opretFadButton = new MotherMainMenuButton("Opret Fad");
        opretFadButton.setOnAction(event -> openOpretFadWindow());
        pane.add(opretFadButton, 1, 1);
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
