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
        try {
            //Vi prøver at oprette et destillat, og håndtere eventuelle errors, som controlleren kaster ved forkert input
            Whisky whisky = Controller.opretWhisky(valgteTapningsVæsker, literVand, kommentar);

            //Viser et alert vindue af typen Confirmation, som viser alt indtastede information
            Alert alert = new BekræftAlertMedInfo("Er du sikker på at du vil oprette en whisky?"); //TODO lav en beskrivelse af de valgte elementer og inputtede værdier
            alert.showAndWait();

            //Lidt en baglens måde at gøre det på, at slette efter man allerede har oprettet, men det gør det markant mere simpelt.
            //Og eftersom vi kun har at gøre med en bruger af gangen, og ikke en delt database mellem flere brugere, så føler vi det er fin løsning.
            boolean bekræftet = alert.getResult() == ButtonType.OK;
            if (!bekræftet) {
                Storage.removeWhisky(whisky);
            }

            //Returnere true, så det forrige pane ved at det lykkedes.
            return bekræftet;
        } catch (Exception e) {
            Alert warning = new BekræftWarningMedFejlInfo(e);
            warning.showAndWait();
        }
        return false;
    }
}
