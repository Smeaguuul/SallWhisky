package gui.tabs;

import gui.fadUdtræk.FadUdtrækWindow;
import gui.motherClasses.*;
import gui.opretAdminMedarbejder.AdminLoginPane;
import gui.whiskyListe.WhiskyUdtrækWindow;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class SystemInformationTap extends MotherTab {
    public SystemInformationTap(String text) {
        super(text);
        drawDefault();
    }

    @Override
    public void drawDefault() {
        MotherPane pane = new MotherPane();
        initContentUdtræk(pane);
        this.setContent(pane);
    }

    private void initContentUdtræk(MotherPane pane) {
        //Laver tab til fad Udtræk
        VBox fadUdtrækVBox = new VBox();
        fadUdtrækVBox.setSpacing(10);
        fadUdtrækVBox.setAlignment(Pos.BOTTOM_CENTER);
        VerticalImageStackPane udtrækImage = new VerticalImageStackPane("/gui/images/lagerhal.png");
        fadUdtrækVBox.getChildren().add(udtrækImage);
        MotherButton hentUdtrækButton = new MainMenuButton("Fad Udtræk");
        hentUdtrækButton.setOnAction(event -> openUdtrækWindow());
        fadUdtrækVBox.getChildren().add(hentUdtrækButton);
        pane.add(fadUdtrækVBox,1,1);


        //Laver tab til opret og rediger
        VBox whiskyVBox = new VBox();
        whiskyVBox.setSpacing(10);
        whiskyVBox.setAlignment(Pos.BOTTOM_CENTER);
        VerticalImageStackPane whiskyImage = new VerticalImageStackPane("/gui/images/flaske.png");
        whiskyVBox.getChildren().add(whiskyImage);
        MotherButton whiskyButton = new MainMenuButton("Whisky Udtræk");
        whiskyButton.setOnAction(event -> openWhiskyWindow());
        whiskyVBox.getChildren().add(whiskyButton);
        pane.add(whiskyVBox,2,1);

        //Opretter knap og billede til Adminstrer Medarbejdere og sætter det i en VBox
        VBox medarbejderVBox = new VBox();
        medarbejderVBox.setSpacing(10);
        medarbejderVBox.setAlignment(Pos.BOTTOM_CENTER);
        VerticalImageStackPane medarbejderImage = new VerticalImageStackPane("/gui/images/medarbejder.png");
        medarbejderVBox.getChildren().add(medarbejderImage);
        MotherButton redigerMedarbejderButton = new MainMenuButton("Admin. Medarbejdere");
        redigerMedarbejderButton.setOnAction(event -> openAdminMedarbejder());
        medarbejderVBox.getChildren().add(redigerMedarbejderButton);
        pane.add(medarbejderVBox,0,1);
    }

    private void openWhiskyWindow() {
        WhiskyUdtrækWindow whiskyUdtrækWindow = new WhiskyUdtrækWindow(this);
        this.setContent(whiskyUdtrækWindow);
    }

    private void openAdminMedarbejder(){
        AdminLoginPane adminLoginPane = new AdminLoginPane("Admin login", this);
        this.setContent(adminLoginPane);
    }

    private void openUdtrækWindow() {
        FadUdtrækWindow fadUdtrækWindow = new FadUdtrækWindow(this);
        this.setContent(fadUdtrækWindow);
    }
}
