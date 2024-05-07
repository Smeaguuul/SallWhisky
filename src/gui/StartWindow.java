package gui;


import gui.motherClasses.MotherButton;
import gui.motherClasses.MotherPane;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Collection;

public class StartWindow extends Application {
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Sall Whisky Destilleri");
        this.stage = stage;
        MotherPane pane = new MotherPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private void initContent(MotherPane pane) {
        pane.setMinSize(500,500);
        MotherButton opretDestillatButton = new MotherButton("Opret Destillat");
        pane.add(opretDestillatButton, 0, 1);


        Image image;
        image = new Image(getClass().getResourceAsStream("/gui/images/logo.png"));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(120);
        pane.add(imageView,0,0);
    }
}
