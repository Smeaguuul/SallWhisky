package gui.tapFad;


import application.controller.Controller;
import application.model.Destillat;
import application.model.MaltBatch;
import application.model.RygningsType;
import application.model.TapningsVæske;
import gui.motherClasses.BekræftAlertMedInfo;
import gui.motherClasses.BekræftWarningMedFejlInfo;
import gui.motherClasses.MotherButton;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import storage.Storage;

public class BekræftTapFadButtton extends MotherButton {

    private CommonClass commonClass;

    public BekræftTapFadButtton(String s) {
        super(s);
    }

    public boolean bekræft(CommonClass commonClass) {
        //Gemmer commonClass
        this.commonClass = commonClass;
        try {
            //Vi prøver at oprette et destillat, og håndtere eventuelle errors, som controlleren kaster ved forkert input
            TapningsVæske tapningsVæske = Controller.opretTapningsVæske(commonClass.getFad(), commonClass.getAlkoholprocent(), commonClass.getMængde());

            //Viser et alert vindue af typen Confirmation, som viser alt indtastede information
            Alert alert = new BekræftAlertMedInfo(commonClass.toString());
            alert.showAndWait();

            //Lidt en baglens måde at gøre det på, at slette efter man allerede har oprettet, men det gør det markant mere simpelt.
            //Og eftersom vi kun har at gøre med en bruger af gangen, og ikke en delt database mellem flere brugere, så føler vi det er fin løsning.
            boolean bekræftet = alert.getResult() == ButtonType.OK;
            if (!bekræftet) {
                Storage.removeTapningsVæske(tapningsVæske);
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
