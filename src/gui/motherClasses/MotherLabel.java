package gui.motherClasses;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MotherLabel extends Label {
    public MotherLabel(String s) {
        super(s);
        this.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 18));
        this.setWrapText(true);
        //this.setBackground(new Background(new BackgroundFill(Paint.valueOf("Gray"), new CornerRadii(5),new Insets(-10) )));
        this.setBlendMode(BlendMode.DARKEN);
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderStroke.DEFAULT_WIDTHS, new Insets(-10))));
        //this.setUnderline(true);
        //this.setPadding(new Insets(0,0,10,0));
    }
}
