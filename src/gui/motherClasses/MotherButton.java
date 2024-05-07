package gui.motherClasses;

import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MotherButton extends Button {
    public MotherButton(String s) {
        super(s);
        this.setFont(Font.font("System", FontWeight.BOLD, 12));
        GridPane.setHalignment(this, HPos.CENTER);
    }
}
