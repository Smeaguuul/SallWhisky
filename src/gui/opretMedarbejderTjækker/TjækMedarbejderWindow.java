package gui.opretMedarbejderTjækker;

import gui.motherClasses.MotherPane;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TjækMedarbejderWindow extends Stage {
    public TjækMedarbejderWindow(String title, Stage owner) {
        MotherPane pane = new MotherPane();
        Scene scene = new Scene(pane);
        this.initOwner(owner);
        this.initContent(pane);
        this.setTitle(title);
        this.setScene(scene);
    }
    private void initContent(MotherPane pane) {

    }
}
