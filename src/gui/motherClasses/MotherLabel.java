package gui.motherClasses;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MotherLabel extends Label {
    public MotherLabel(String s) {
        super(s);
        this.setFont(Font.font("System", FontWeight.BOLD, 12));
        this.setWrapText(true);
        //this.setUnderline(true);
        this.setPadding(new Insets(0,0,10,0));
    }
}
