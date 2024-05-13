package gui.motherClasses;

import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;

public class VerticalImageStackPane extends StackPane {
    public VerticalImageStackPane(String imagelocation) {
        Image image = new Image(imagelocation, 1600, 900, true, true);
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setPreserveRatio(false);
        imageView.setViewport(new Rectangle2D(600,0,360,720 ));
        this.setPadding(new Insets(5));
        this.setBackground(new Background(new BackgroundFill(Paint.valueOf("White"),new CornerRadii(5),null)));
        this.getChildren().add(imageView);
    }
}
