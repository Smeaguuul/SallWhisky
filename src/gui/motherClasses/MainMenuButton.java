package gui.motherClasses;

import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;

public class MainMenuButton extends MotherMainMenuButton{
    public MainMenuButton(String s) {
        super(s);
        this.setStyle("-fx-background-color:  rgb(" + 44 + "," + 62 + ", " + 96 + "); -fx-background-radius: 10 10 10 10; -fx-font-size:24;");
        this.setEffect(new DropShadow(20,Color.BLACK));
        this.setTextFill(Color.WHITE);
        this.setMinWidth(320);
    }
}
