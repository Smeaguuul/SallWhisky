package gui.motherClasses;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class InfoLabel extends Label {
    public InfoLabel(String s) {
        super(s);
        this.setFont(Font.font("System",FontWeight.SEMI_BOLD, 16));
        this.setWrapText(false);
        this.setUnderline(true);
        this.setTextFill(Color.WHITE);
        //this.setPadding(new Insets(0,0,10,0));
    }
}
