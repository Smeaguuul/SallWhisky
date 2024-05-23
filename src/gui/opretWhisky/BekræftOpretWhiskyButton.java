package gui.opretWhisky;

import application.controller.Controller;
import application.model.TapningsVæske;
import application.model.Whisky;
import gui.motherClasses.BekræftAlertMedInfo;
import gui.motherClasses.BekræftWarningMedFejlInfo;
import gui.motherClasses.MotherButton;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import storage.Storage;

import java.util.List;

public class BekræftOpretWhiskyButton extends MotherButton {
    public BekræftOpretWhiskyButton(String s) {
        super(s);
    }

    public boolean bekræft(List<TapningsVæske> valgteTapningsVæsker, int literVand, String kommentar) {
        boolean lykkedes = false;
        try {
            //Checker input
            Controller.opretWhiskyCheck(valgteTapningsVæsker, literVand);

            //Viser et alert vindue af typen Confirmation, som viser alt indtastede information
            Alert alert = new BekræftAlertMedInfo("Er du sikker på at du vil oprette en whisky?");
            alert.showAndWait();

            //Hvis brugeren bekræfter, så opretter vi faktisk en whisky
            boolean bekræftet = alert.getResult() == ButtonType.OK;
            if (bekræftet) {
                Controller.opretWhisky(valgteTapningsVæsker, literVand, kommentar);

            }

            //Returnere true, så det forrige pane ved at det lykkedes.
            lykkedes = bekræftet;
        } catch (Exception e) {
            Alert warning = new BekræftWarningMedFejlInfo(e);
            warning.showAndWait();
        }
        return lykkedes;
    }
}