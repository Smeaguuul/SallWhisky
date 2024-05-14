package gui.motherClasses;

import javafx.scene.control.Alert;

public class BekræftWarningMedFejlInfo extends Alert {
    public BekræftWarningMedFejlInfo(Exception exception) {
        super(AlertType.WARNING);
        this.setHeaderText("Der er sket en fejl. Se nedenstående for ydeligere information.");
        this.setContentText(exception.getMessage());

    }
}
