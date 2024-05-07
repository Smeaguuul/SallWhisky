package gui;


import gui.motherClasses.MotherButton;
import gui.motherClasses.MotherPane;
import gui.opretDestillat.OpretDestillatWindow;
import gui.opretFad.OpretFadWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        //Opretter knap til opret Destillat
        MotherButton opretDestillatButton = new MotherButton("Opret Destillat");
        opretDestillatButton.setOnAction(event -> openOpretDestillatWindow());
        pane.add(opretDestillatButton, 0, 1);

        //Opretter knap til opretFad
        MotherButton opretFadButton = new MotherButton("Opret Fad");
        opretFadButton.setOnAction(event -> openOpretFadWindow());

        pane.add(opretFadButton, 1, 1);


        //Tilføjer et logo
        Image image;
        image = new Image(getClass().getResourceAsStream("/gui/images/logo.png"));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(120);
        pane.add(imageView,0,0);

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
}
