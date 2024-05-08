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
        this.setMinSize(500, 500);

        this.setGridLinesVisible(false);
        this.setPadding(new Insets(20));
        this.setHgap(60);
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
                new Stop(0, Color.rgb(255,153,204)),
                new Stop(1, Color.rgb(255, 153, 0))
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
