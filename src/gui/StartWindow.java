package gui;


import gui.motherClasses.MotherButton;
import gui.motherClasses.MotherMainMenuButton;
import gui.motherClasses.MotherPane;
import gui.opretDestillat.OpretDestillatWindow;
import gui.opretFad.OpretFadWindow;
import gui.opretMedarbejderTjækker.TjækMedarbejderWindow;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

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
        pane.setVgap(40);
        //Opretter knap til opret Destillat
        MotherButton opretDestillatButton = new MotherMainMenuButton("Opret Destillat");
        opretDestillatButton.setOnAction(event -> openMedarbejderTjækker());
        pane.add(opretDestillatButton, 0, 1);

        //Opretter knap til opretFad
        MotherButton opretFadButton = new MotherMainMenuButton("Opret Fad");
        opretFadButton.setOnAction(event -> openOpretFadWindow());
        pane.add(opretFadButton, 1, 1);


        //Tilføjer et logo
        Image image;
        image = new Image(getClass().getResourceAsStream("/gui/images/logo.png"));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(225);
        HBox imageHBox = new HBox(imageView);
        imageHBox.setBackground(new Background(new BackgroundFill(Paint.valueOf("Gray"), new CornerRadii(5), new Insets(-10))));
        imageHBox.setAlignment(Pos.TOP_CENTER);
        pane.add(imageHBox,0,0,2,1);

        //Label test
        //MotherLabel label = new MotherLabel("Test");
        //pane.add(label,1,1);
    }

    private void openOpretDestillatWindow() {
        OpretDestillatWindow opretDestillatWindow = new OpretDestillatWindow("Opret Destillat Vindue", this.stage);
        opretDestillatWindow.showAndWait();
    }

    private void openOpretFadWindow() {
        OpretFadWindow opretFadWindow = new OpretFadWindow("Opret Fad Vindue", this.stage);
        opretFadWindow.showAndWait();
    }
    private void openMedarbejderTjækker() {
    //    TjækMedarbejderWindow tjækMedarbejderWindow = new TjækMedarbejderWindow();
    //    tjækMedarbejderWindow.showAndWait();
    }
}
