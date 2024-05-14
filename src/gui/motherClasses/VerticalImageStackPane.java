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
        //Indlæser billede i den givne oplysninger, hvor vi bibeholder forholdet mellem de vertikale- og horisontale pixels på billedet
        Image image = new Image(imagelocation, 1280, 720, true, true);

        //Laver et billedeview
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setPreserveRatio(false);

        //Sætter et viewport, så vi blot ser en del af billedet. Viewport starter fra 600, 0 og har en bredde på 360 og en højde på 720
        imageView.setViewport(new Rectangle2D(600,0,240,300 ));
        this.setPadding(new Insets(5));

        //Laver en border omkring billedet
        this.setBackground(new Background(new BackgroundFill(Paint.valueOf("White"),new CornerRadii(5),null)));
        this.getChildren().add(imageView);
    }
}
