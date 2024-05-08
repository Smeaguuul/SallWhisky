package gui.motherClasses;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class MotherMainMenuButton extends MotherButton{
    public MotherMainMenuButton(String s) {
        super(s);
        this.setStyle("-fx-font-size:28; " + "-fx-background-radius: 10;");
        this.setMinSize(225, 75);
        this.setMaxSize(225, 75);
    }
}
