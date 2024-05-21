package gui.fyldPaafad;

import application.controller.Controller;
import application.model.Fad;
import application.model.Make;
import application.model.Væske;
import gui.motherClasses.BekræftAlertMedInfo;
import gui.motherClasses.BekræftWarningMedFejlInfo;
import gui.motherClasses.MotherButton;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import storage.Storage;

import java.util.HashMap;

public class BekræftFyldPaaFadButton extends MotherButton {
    public BekræftFyldPaaFadButton(String s) {
        super(s);
    }

    public boolean bekræft(Fad fad, HashMap<Væske, Double> væskeOgLiter) {
        boolean lykkedes = false;
        try {
            Controller.opretMakeCheck(fad, væskeOgLiter);

            //Viser et alert vindue af typen Confirmation, som viser alt indtastede information
            Alert alert = new BekræftAlertMedInfo("Fad " + fad.getFadNr() + " med " + væskeOgLiter.size() + " væsker.");
            alert.showAndWait();

            //Hvis der bekræftes, så opretter vi faktisk maket
            boolean bekræftet = alert.getResult() == ButtonType.OK;
            if (bekræftet) {
                Controller.opretMake(fad, væskeOgLiter);
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
