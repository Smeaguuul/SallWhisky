package gui.motherClasses;

import javafx.scene.control.Alert;

public class BekræftAlertMedInfo extends Alert {
    public BekræftAlertMedInfo(String information) {
        super(AlertType.CONFIRMATION);
        this.setTitle("Bekræft Oprettelse");
        this.setHeaderText("Bekræft nedenstående information:");
        String infoTekst = information;
        this.setContentText(infoTekst);
    }
}
