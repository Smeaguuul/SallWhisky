package gui.motherClasses;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class MotherPane extends GridPane {

    public MotherPane() {
        Background background = getBackgroundTheme();
        this.setBackground(background);
        this.setMinSize(1600, 900);

        this.setGridLinesVisible(false);
        this.setPadding(new Insets(20));
        this.setHgap(30);
        this.setVgap(15);
    }

    private static Background getBackgroundTheme() {
        // Create a linear gradient background
        LinearGradient linearGradient = new LinearGradient(
                0,      // startX
                0.25,      // startY
                0,      // endX
                1,      // endY
                true,   // proportional
                javafx.scene.paint.CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(44,51,58)),
                new Stop(1, Color.rgb(69,79,89))
        );

        BackgroundFill linearGradientFill = new BackgroundFill(
                linearGradient,
                CornerRadii.EMPTY,
                null
        );
        Background background = new Background(linearGradientFill);

        // Set the background
        return background;
    }
}
