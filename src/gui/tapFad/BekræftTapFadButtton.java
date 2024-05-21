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
        boolean lykkedes = false;
        try {
            //Checker input
            Controller.opretTapningsVæskeCheck(commonClass.getFad(), commonClass.getAlkoholprocent(), commonClass.getMængde());

            //Viser et alert vindue af typen Confirmation, som viser alt indtastede information
            Alert alert = new BekræftAlertMedInfo(commonClass.toString());
            alert.showAndWait();

            //Hvis der bekræftes, så opretter vi en tapningsvæske
            boolean bekræftet = alert.getResult() == ButtonType.OK;
            TapningsVæske tapningsVæske = null;
            if (!bekræftet) {
                tapningsVæske = Controller.opretTapningsVæske(commonClass.getFad(), commonClass.getAlkoholprocent(), commonClass.getMængde());
            }

            //Returnere true, så det forrige pane ved at det lykkedes.
            this.commonClass.setTapningsVæske(tapningsVæske);
            lykkedes = bekræftet;
        } catch (Exception e) {
            Alert warning = new BekræftWarningMedFejlInfo(e);
            warning.showAndWait();
        }
        return lykkedes;
    }
}
