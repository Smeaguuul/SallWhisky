package gui.motherClasses;

import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;

public class MainMenuButton extends MotherMainMenuButton{
    public MainMenuButton(String s) {
        super(s);
        this.setStyle("-fx-background-radius: 10 10 10 10; -fx-font-size:28; -fx-background-color:  linear-gradient(#44546F, #091E427D);");
        this.setEffect(new DropShadow(20,Color.BLACK));
        this.setTextFill(Color.WHITE);
        this.setMinWidth(360);
    }
}
