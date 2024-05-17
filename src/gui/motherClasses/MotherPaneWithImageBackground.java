package gui.motherClasses;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;

public class MotherPaneWithImageBackground extends MotherPane{
    public MotherPaneWithImageBackground(String billedeAddresse) {
        super();
        this.setBackground(new Background(new BackgroundImage(new Image(billedeAddresse), null, null, null, new BackgroundSize(1080,480,false,false,true,true)), null, null));
    }
}
