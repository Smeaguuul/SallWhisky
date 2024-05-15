package gui.opretDestillat;

import application.controller.Controller;
import application.model.Destillat;
import application.model.MaltBatch;
import application.model.RygningsType;
import gui.motherClasses.BekræftAlertMedInfo;
import gui.motherClasses.BekræftWarningMedFejlInfo;
import gui.motherClasses.MotherButton;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import storage.Storage;

public class BekræftDestillatButton extends MotherButton {
    private CommonClass commonClass;

    public BekræftDestillatButton(String s) {
        super(s);

    }

    public boolean bekræft(CommonClass commonClass) {
        //Gemmer commonClass
        this.commonClass = commonClass;
        try {
            //Vi prøver at oprette et destillat, og håndtere eventuelle errors, som controlleren kaster ved forkert input
            Destillat destillat = Controller.opretDestillat(commonClass.getStartDato(), commonClass.getSlutDato(), Integer.parseInt(commonClass.getAntalLiter()), Double.parseDouble(commonClass.getAlkoholProcent()), (RygningsType) commonClass.getRygningsType(), commonClass.getKommentar(), (MaltBatch) commonClass.getMaltBatch(), commonClass.getMedarbejder());
            //TODO Eventuelt lave et textfield som automatisk trimmer gettext, så vi ikke skal kalde det hele tiden
            //TODO smid det her ud i en Lambda Expressions så vi kan simplificere disse to "Bekræft klasser" ned i én

            //Viser et alert vindue af typen Confirmation, som viser alt indtastede information
            Alert alert = new BekræftAlertMedInfo(commonClass.toString());
            alert.showAndWait();

            //Lidt en baglens måde at gøre det på, at slette efter man allerede har oprettet, men det gør det markant mere simpelt.
            //Og eftersom vi kun har at gøre med en bruger af gangen, og ikke en delt database mellem flere brugere, så føler vi det er fin løsning.
            boolean bekræftet = alert.getResult() == ButtonType.OK;
            if (!bekræftet) {
                Storage.removeVæske(destillat);
            }

            //Returnere true, så det forrige pane ved at det lykkedes.
            return bekræftet;
        } catch (NumberFormatException e) {
            Alert warning = new BekræftWarningMedFejlInfo(new Exception("Antal liter og alkoholprocent skal kun bestå af tal."));
            warning.showAndWait();
        } catch (Exception e) {
            Alert warning = new BekræftWarningMedFejlInfo(e);
            warning.showAndWait();
        }
        return false;
    }

    private String getIndtastedeInformation() {
        String info = commonClass.toString();
        return info;
    }
}
