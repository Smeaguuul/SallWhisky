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
        boolean lykkedes = false;
        try {
            //Vi bruger den tilsvarende checkMetode i Controller
            Controller.opretDestillatCheck(commonClass.getStartDato(), commonClass.getSlutDato(), Integer.parseInt(commonClass.getAntalLiter()), Double.parseDouble(commonClass.getAlkoholProcent()), commonClass.getRygningsType());

            //Viser et alert vindue af typen Confirmation, som viser alt indtastede information
            Alert alert = new BekræftAlertMedInfo(commonClass.toString());
            alert.showAndWait();

            //Hvis der bliver bekræftet, så opretter vi faktisk et destillat
            boolean bekræftet = alert.getResult() == ButtonType.OK;
            if (bekræftet) {
                Controller.opretDestillat(commonClass.getStartDato(), commonClass.getSlutDato(), Integer.parseInt(commonClass.getAntalLiter()), Double.parseDouble(commonClass.getAlkoholProcent()), commonClass.getRygningsType(), commonClass.getKommentar(), commonClass.getMaltBatch(), commonClass.getMedarbejder());
            }

            //Returnere true, så det forrige pane ved at det lykkedes.
            lykkedes = bekræftet;
        } catch (NumberFormatException e) {
            Alert warning = new BekræftWarningMedFejlInfo(new Exception("Antal liter og alkoholprocent skal være tal."));
            warning.showAndWait();
        } catch (Exception e) {
            Alert warning = new BekræftWarningMedFejlInfo(e);
            warning.showAndWait();
        }
        return lykkedes;
    }

    private String getIndtastedeInformation() {
        String info = commonClass.toString();
        return info;
    }
}
