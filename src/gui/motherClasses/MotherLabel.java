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
        this.setStyle("-fx-text-alignment: center; -fx-alignment: center; -fx-max-width:Infinity; -fx-padding: 5;");
        this.setTextFill(Color.BLACK);
        this.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 20));
        this.setWrapText(true);
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderStroke.DEFAULT_WIDTHS, new Insets(-10))));
    }
}
