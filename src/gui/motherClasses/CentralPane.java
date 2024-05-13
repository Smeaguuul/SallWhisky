package gui.motherClasses;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class CentralPane extends GridPane {
    public CentralPane() {
        this.setMinSize(100, 100);
        this.setPadding(new Insets(25));
        this.setStyle("-fx-background-color: linear-gradient(rgba(133, 144, 162, 0.9), rgba(155, 180 ,202 ,0.85)); -fx-background-radius: 10;");
        this.setHgap(30);
        this.setVgap(15);
    }
}
