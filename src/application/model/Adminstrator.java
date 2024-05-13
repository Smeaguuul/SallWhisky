package application.model;

public class Adminstrator extends Medarbejder {
    private String kode;

    public Adminstrator(String navn, String cpr, String signatur, String kode) {
        super(navn, cpr, signatur);
        this.kode = kode;
    }

    public boolean verficerKode(String indtastKode) {
        if (indtastKode.equals(kode)) {
            return true;
        } else return false;
    }
}
