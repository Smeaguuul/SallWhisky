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
        boolean lykkedes = false;
        try {
            //Checker for fejl
            Controller.opretFadCheck(commonClass.getTræsort(), commonClass.getForhandler(), commonClass.getTidligereIndhold(), Integer.parseInt(commonClass.getLiterStørrelse()));

            //Viser et alert vindue af typen Confirmation, som viser alt indtastede information
            Alert alert = new BekræftAlertMedInfo(commonClass.toString());
            alert.showAndWait();

            //Hvis der bekræftes, så opretter vi faktisk fadet
            boolean bekræftet = alert.getResult() == ButtonType.OK;
            if (bekræftet) {
                Controller.opretFad(commonClass.getTræsort(), commonClass.getForhandler(), commonClass.getTidligereIndhold(), Integer.parseInt(commonClass.getLiterStørrelse()),commonClass.getKommentar());
            }

            //Returnere true, så det forrige pane ved at det lykkedes.
            lykkedes = bekræftet;
        } catch (NumberFormatException e) {
            Alert warning = new BekræftWarningMedFejlInfo(new Exception("Antal liter skal kun bestå af tal."));
            warning.showAndWait();
        } catch (Exception e) {
            Alert warning = new BekræftWarningMedFejlInfo(e);
            warning.showAndWait();
        }
        return lykkedes;
    }
}
