package gui.opretDestillat;

import application.controller.Controller;
import application.model.MaltBatch;
import application.model.RygningsType;
import gui.motherClasses.MotherButton;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class BekræftDestillatButton extends MotherButton {
    private CommonClass commonClass;

    public BekræftDestillatButton(String s) {
        super(s);

    }

    public boolean bekræft(CommonClass commonClass) {
        //Gemmer commonClass
        this.commonClass = commonClass;

        try {
            validerInput(); //TODO smid det her ud i en Lambda Expressions så vi kan simplificere disse to "Bekræft klasser" ned i én
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
                //Vi tjekker om tidligere om commonclass' literstørrelse og alkoholprocent kun består af tal. Så vi kan med ro parse den til en int.
                Controller.opretDestillat(commonClass.getStartDato(), commonClass.getSlutDato(), Integer.parseInt(commonClass.getAntalLiter()),Double.parseDouble(commonClass.getAlkoholProcent()), (RygningsType) commonClass.getRygningsType(), commonClass.getKommentar(), (MaltBatch) commonClass.getMaltBatch(), commonClass.getMedarbejder());
                bekræftet = true;
            }
            return bekræftet;
        } catch (Exception e) {
            visAdvarsel(e);
        }
        return false;
    }

    private void visAdvarsel(Exception e) { //TODO Duplikeret kode med anden bekræft button
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Der er sket en fejl. Se nedenstående for ydeligere information.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    private String getIndtastedeInformation() {
        return "En masse information";
    }

    private void validerInput() {

    }
}
