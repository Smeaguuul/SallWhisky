package gui.tabs;

import gui.medarbejderCheck.CheckMedarbejderWindow;
import gui.motherClasses.*;
import gui.opretAdminMedarbejder.AdminLoginPane;
import gui.opretFad.OpretFadPane;
import gui.opretForhandler.OpretForhandlerPane;
import gui.opretLagerPane.OpretLagerPane;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

public class OpretRedigerTab extends MotherTab {
    public OpretRedigerTab(String text) {
        super(text);
        //Laver tab til opret og rediger
        MotherPane paneRediger = new MotherPane();
        this.initContentRediger(paneRediger);
        this.setContent(paneRediger);
    }

    private void initContentRediger(MotherPane pane) {
        //Opretter knap og billede til opretDestillat og sætter det i en VBox
        VBox destillatVBox = new VBox();
        destillatVBox.setSpacing(10);
        VerticalImageStackPane fieldImage = new VerticalImageStackPane("/gui/images/markLogo.jpg");
        destillatVBox.getChildren().add(fieldImage);
        MotherButton opretDestillatButton = new MainMenuButton("Opret Destillat");
        opretDestillatButton.setOnAction(event -> openMedarbejderChecker());
        destillatVBox.getChildren().add(opretDestillatButton);
        destillatVBox.setAlignment(Pos.BOTTOM_CENTER);
        pane.add(destillatVBox,0,1);


        //Opretter knap og billede til opretFad og sætter det i en VBox
        VBox fadVBox = new VBox();
        fadVBox.setSpacing(10);
        fadVBox.setAlignment(Pos.BOTTOM_CENTER);
        VerticalImageStackPane fadImage = new VerticalImageStackPane("/gui/images/fad.png");
        fadVBox.getChildren().add(fadImage);
        MotherButton opretFadButton = new MainMenuButton("Opret Fad");
        opretFadButton.setOnAction(event -> openOpretFadWindow());
        fadVBox.getChildren().add(opretFadButton);
        pane.add(fadVBox,1,1);

        //Opretter knap og billede til Adminstrer Medarbejdere og sætter det i en VBox
        VBox medarbejderVBox = new VBox();
        medarbejderVBox.setSpacing(10);
        medarbejderVBox.setAlignment(Pos.BOTTOM_CENTER);
        VerticalImageStackPane medarbejderImage = new VerticalImageStackPane("/gui/images/medarbejder.png");
        medarbejderVBox.getChildren().add(medarbejderImage);
        MotherButton redigerMedarbejderButton = new MainMenuButton("Admin. Medarbejdere");
        redigerMedarbejderButton.setOnAction(event -> openAdminMedarbejder());
        medarbejderVBox.getChildren().add(redigerMedarbejderButton);
        pane.add(medarbejderVBox,2,1);

        //Opretter knap og billede til opretForhandler og sætter det i en VBox
        /*VBox forhandlerVBox = new VBox();
        forhandlerVBox.setSpacing(10);
        forhandlerVBox.setAlignment(Pos.BOTTOM_CENTER);
        VerticalImageStackPane forhandlerImage = new VerticalImageStackPane("/gui/images/forhandler.png");
        forhandlerVBox.getChildren().add(forhandlerImage);
        MotherButton opretForhandlerButton = new MainMenuButton("Opret Forhandler");
        opretForhandlerButton.setOnAction(event -> openOpretForhandlerPane());
        forhandlerVBox.getChildren().add(opretForhandlerButton);
        pane.add(forhandlerVBox,3,1); */

        //Opretter knap og billede til opretLager og sætter det i en VBox
        VBox lagerVbox = new VBox();
        lagerVbox.setSpacing(10);
        lagerVbox.setAlignment(Pos.BOTTOM_CENTER);
        VerticalImageStackPane lagerImage = new VerticalImageStackPane("/gui/images/lager.png");
        lagerVbox.getChildren().add(lagerImage);
        Button opretLagerButton = new MainMenuButton("Opret Lager");
        opretLagerButton.setOnAction(e -> openOpretLagerPane());
        lagerVbox.getChildren().add(opretLagerButton);
        pane.add(lagerVbox,3,1);

    }

    private void openOpretLagerPane() {
        MotherPane opretLagerPane = new OpretLagerPane("Opret Lager",this);
        this.setContent(opretLagerPane);
    }

    private void openAdminMedarbejder(){
        AdminLoginPane adminLoginPane = new AdminLoginPane("Admin login", this);
        this.setContent(adminLoginPane);
    }
    private void openOpretForhandlerPane() {
        MotherPane opretForhandlerPane = new OpretForhandlerPane("Opret Forhandler", this);
        this.setContent(opretForhandlerPane);
    }

    @Override
    public void drawDefault(){
        MotherPane paneRediger = new MotherPane();
        initContentRediger(paneRediger);
        this.setContent(paneRediger);
    }

    private void openMedarbejderChecker() {
        MotherPane checkMedarbejderPane = new CheckMedarbejderWindow("Indtast medarbejder nummer", this);
        this.setContent(checkMedarbejderPane);
    }

    private void openOpretFadWindow() {
        OpretFadPane opretFadPane = new OpretFadPane("Opret Fad Vindue", this);
        this.setContent(opretFadPane);
    }
}
