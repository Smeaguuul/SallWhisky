package gui;


import gui.motherClasses.MotherPane;
import gui.tabs.OpretRedigerTab;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.util.ArrayList;

public class StartWindow extends Application {
    private Stage stage;
    Tab tabRediger = new Tab("Opret og Registrer");


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Sall Whisky Destilleri");
        this.stage = stage;

        MotherPane motherPane = new MotherPane();
        motherPane.setPadding(new Insets(10, 0, 10, 0));
        motherPane.setHgap(0);
        motherPane.setVgap(15);

        //Tilføjer et logo
        Image image;
        image = new Image(getClass().getResourceAsStream("/gui/images/logo.png"));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(300);
        HBox imageHBox = new HBox(imageView);
        imageHBox.setBackground(new Background(new BackgroundFill(Color.rgb(98,111,134), new CornerRadii(5), new Insets(-10))));
        imageHBox.setAlignment(Pos.TOP_CENTER);
        motherPane.add(imageHBox, 0, 0, 2, 1);

        //Laver en tabpane
        TabPane tabPane = new TabPane();
        tabPane.setTabMinWidth(120);
        tabPane.setTabMinHeight(30);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setBackground(new Background(new BackgroundFill(null, new CornerRadii(0), new Insets(10))));

        //Tab til CRUD
        OpretRedigerTab opretRedigerTab = new OpretRedigerTab("Opret og Rediger");
        tabPane.getTabs().add(opretRedigerTab);

        //Laver tab til udtræk
        Tab tabUdtræk = new Tab("Udtræk");
        MotherPane paneUdtræk = new MotherPane();
        this.initContentUdtryk(paneUdtræk);
        tabUdtræk.setContent(paneUdtræk);
        tabPane.getTabs().add(tabUdtræk);


        motherPane.add(tabPane, 0, 1);
        Scene scene = new Scene(motherPane);
        stage.setScene(scene);
        stage.show();
    }

    private void initContentUdtryk(MotherPane paneUdtræk) {

    }
}
