package gui.opretFad;

import application.controller.Controller;
import application.model.Forhandler;
import application.model.TidligereIndhold;
import application.model.Træsort;
import gui.motherClasses.MotherButton;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

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
            validerInput();
            //Viser et alert vindue af typen Confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Bekræft Oprettelse");
            alert.setHeaderText("Bekræft nedenstående information:");
            String fadBekræftTekst = getIndtastedeInformation();
            alert.setContentText(fadBekræftTekst);
            alert.showAndWait();

            //Hvis der bliver trykket ok
            boolean bekræftet = false;
            if (alert.getResult() == ButtonType.OK) {
                //Vi tjekker om tidligere om commonclass' literstørrelse kun består af tal. Så vi kan med ro parse den til en int.
                Controller.opretFad(commonClass.getTræsort(), commonClass.getForhandler(), commonClass.getTidligereIndhold(), Integer.parseInt(commonClass.getLiterStørrelse()), commonClass.getKommentar());
                bekræftet = true;
            }
            return bekræftet;
        } catch (Exception e) {
            visAdvarsel(e);
        }
        return false;
    }

    private String getIndtastedeInformation() {
        String info = "";
        info += "Træsort : " + commonClass.getTræsort();
        info += "\nForhandler: " + commonClass.getForhandler();
        info += "\nTidligere indhold: " + commonClass.getTidligereIndhold();
        info += "\nAntal liter i fad: " + commonClass.getLiterStørrelse();
        if (commonClass.getKommentar().isEmpty()) {
            info += "\nIngen ydeligere kommentarer.";
        } else {
            info += "\nKommentar: " + commonClass.getKommentar();
        }
        return info;
    }

    private static void visAdvarsel(Exception e) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Der er sket en fejl. Se nedenstående for ydeligere information.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
    private void validerInput() { //TODO skal nok håndtere errors fra controlleren, i stedet for checker herude i GUI
        if (commonClass.getTræsort() == null) {
            throw new NoSuchElementException("Ingen Træsort Valgt!");
        }
        if (commonClass.getForhandler() == null) {
            throw new NoSuchElementException("Intet Tidligere Indhold Valgt!");
        }
        if (commonClass.getTidligereIndhold() == null) {
            throw new NoSuchElementException("Ingen Forhandler Valgt!");
        }
        if (commonClass.getLiterStørrelse() == null || commonClass.getLiterStørrelse().matches(".*[^0-9].*")) {
            throw new NoSuchElementException("Ikke En Gyldig Størrelse!");
        }
    }
}
