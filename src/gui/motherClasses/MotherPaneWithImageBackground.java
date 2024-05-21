package gui.motherClasses;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;

public class MotherPaneWithImageBackground extends MotherPane{

    protected final GridPane centralPane;
    protected final MotherTab owner;

    public MotherPaneWithImageBackground(String billedeAddresse, MotherTab owner) {
        super();
        this.owner = owner;
        centralPane = new CentralPane();
        this.setBackground(new Background(new BackgroundImage(new Image(billedeAddresse), null, null, null, new BackgroundSize(1080,480,false,false,true,true)), null, null));
    }
}
