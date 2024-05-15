package gui.opretFad;

import application.controller.Controller;
import application.model.Fad;
import application.model.Forhandler;
import application.model.TidligereIndhold;
import application.model.Træsort;
import gui.motherClasses.BekræftAlertMedInfo;
import gui.motherClasses.BekræftWarningMedFejlInfo;
import gui.motherClasses.MotherButton;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import storage.Storage;

import java.util.NoSuchElementException;

public class BekræftFadButton extends MotherButton {
    CommonClass commonClass;

    public BekræftFadButton(String s) {
        super(s);
    }

    public boolean bekræft(CommonClass commonClass) {
        //Gemmer commonClass
        this.commonClass = commonClass;

        try {
            Fad fad = Controller.opretFad(commonClass.getTræsort(), commonClass.getForhandler(), commonClass.getTidligereIndhold(), Integer.parseInt(commonClass.getLiterStørrelse()), commonClass.getKommentar());

            //Viser et alert vindue af typen Confirmation, som viser alt indtastede information
            Alert alert = new BekræftAlertMedInfo(commonClass.toString());
            alert.showAndWait();

            //Lidt en baglens måde at gøre det på, at slette efter man allerede har oprettet, men det gør det markant mere simpelt.
            //Og eftersom vi kun har at gøre med en bruger af gangen, og ikke en delt database mellem flere brugere, så føler vi det er fin løsning.
            boolean bekræftet = alert.getResult() == ButtonType.OK;
            if (!bekræftet) {
                Storage.removeFad(fad);
            }

            //Returnere true, så det forrige pane ved at det lykkedes.
            return bekræftet;
        } catch (NumberFormatException e) {
            Alert warning = new BekræftWarningMedFejlInfo(new Exception("Antal liter skal kun bestå af tal."));
            warning.showAndWait();
        } catch (Exception e) {
            Alert warning = new BekræftWarningMedFejlInfo(e);
            warning.showAndWait();
            ;
        }
        return false;
    }
}
