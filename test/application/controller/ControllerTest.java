package application.controller;

import application.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Storage;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Forhandler juan;
    private Medarbejder medarbejder;
    private MaltBatch maltBatch;

    @BeforeEach
    void setUp() {
        juan = new Forhandler("Juan iglesias", "Catalonien", "Spanien");
        medarbejder = new Medarbejder(1, "Mads Medarbejder", "010203-4555", "MAM");
        Adresse adresse = new Adresse("1", "Landmandvej", "6960", "Danmark");
        Mark mark = new Mark("En meget fin Økologisk mark, som dyrkes af Lars Landmand", "Langdahl", adresse);
        Malteri malteri = new Malteri("Thy Whisky Malteri", "Et stort malteri i Thy, som opereres af Thy Whisky.", adresse);
        maltBatch = new MaltBatch(Kornsort.EVERGREEN, 1, LocalDate.of(2024, 05, 03), malteri, mark);
    }

    @Test
    void getMedarbejder_TC1() {
        //Arrange
        Medarbejder expected = new Medarbejder(1, "Mads Medarbejder", "010203-4555", "NAM");
        Storage.addMedarbejder(expected);

        //Act
        Medarbejder actual = Controller.getMedarbejder(1);

        //Assert
        System.out.println("Medarbejder: ");
        System.out.println("Actual: " + actual);
        System.out.println("Expected: " + expected);

        assertEquals(expected, actual);
    }

    @Test
    void getMedarbejder_TC2() {
        //Arrange
        Medarbejder medarbejder = new Medarbejder(1, "Mads Medarbejder", "010203-4555", "NAM");
        Storage.addMedarbejder(medarbejder);
        String expected = "Medarbejder eksistere ikke";

        //Act
        Exception actual = assertThrows(NoSuchElementException.class, () -> {
            Controller.getMedarbejder(2);
        });

        //Assert
        System.out.println("Error besked:");
        System.out.println("Actual: " + actual.getMessage());
        System.out.println("Expected: " + expected);

        assertTrue(actual.getMessage().contains(expected));
    }

    @Test
    void getMedarbejder_Ugyldig_TC1() {
        //Arrange
        Medarbejder medarbejder = new Medarbejder(1, "Mads Medarbejder", "010203-4555", "NAM");
        Storage.addMedarbejder(medarbejder);
        String expected = "MedarbejderNummer skal være positivt";

        //Act
        Exception actual = assertThrows(IllegalArgumentException.class, () -> {
            Controller.getMedarbejder(-4);
        });

        //Assert
        System.out.println("Error besked:");
        System.out.println("Actual: " + actual.getMessage());
        System.out.println("Expected: " + expected);

        assertTrue(actual.getMessage().contains(expected));
    }

    @Test
    void getMedarbejder_Ugyldig_TC2() {
        //Arrange
        Medarbejder medarbejder = new Medarbejder(1, "Mads Medarbejder", "010203-4555", "NAM");
        Storage.addMedarbejder(medarbejder);
        String expected = "MedarbejderNummer skal være positivt";

        //Act
        Exception actual = assertThrows(IllegalArgumentException.class, () -> {
            Controller.getMedarbejder(0);
        });

        //Assert
        System.out.println("Error besked:");
        System.out.println("Actual: " + actual.getMessage());
        System.out.println("Expected: " + expected);

        assertTrue(actual.getMessage().contains(expected));
    }

    void opretFad_TC1() {
        // Arrange
        String expected = "Liter størrelse skal være over 0";

        // Act
        Exception actual = assertThrows(IllegalArgumentException.class, () -> {
            Controller.opretFad(Træsort.QUERCUSALBA, juan,
                    TidligereIndhold.BOURBON, 0, "");
        });

        // Assert
        System.out.println("opretfad: TC1");
        System.out.println("Actual: " + actual.getMessage());
        System.out.println("Expected: " + expected);
        assertTrue(actual.getMessage().contains(expected));
    }

    @Test
    void opretFad_TC2() {
        // Arrange
        Fad exptected = Controller.opretFad(Træsort.QUERCUSALBA, juan, TidligereIndhold.BOURBON, 2, "");
        Træsort expectedTraesort = Træsort.QUERCUSALBA;
        TidligereIndhold expectedTidligereIndhold = TidligereIndhold.BOURBON;
        int expectedLiter = 2;

        // Act
        Fad actual = Storage.getFade().get(0);
        Træsort actualTraesort = Storage.getFade().get(0).getTræsort();
        TidligereIndhold acutalTidligereIndhold = Storage.getFade().get(0).getTidligereIndhold();
        int actualLiter = Storage.getFade().get(0).getLiterStørrelse();

        // Assert
        System.out.println("opretfad: TC2");
        System.out.println("Actual: " + Storage.getFade().get(0));
        System.out.println("Expected: " + exptected);
        assertTrue(actual.equals(exptected));
        assertEquals(expectedTraesort, actualTraesort);
        assertEquals(expectedTidligereIndhold, acutalTidligereIndhold);
        assertEquals(expectedLiter, actualLiter);
    }

    @Test
    void opretFad_TC3() {
        // Arrange
        String expected = "Liter størrelse skal være over 0";

        // Act
        Exception actual = assertThrows(IllegalArgumentException.class, () -> {
            Controller.opretFad(Træsort.QUERCUSALBA, juan,
                    TidligereIndhold.BOURBON, -1, "");
        });

        // Assert
        System.out.println("opretfad: TC3");
        System.out.println("Actual: " + actual.getMessage());
        System.out.println("Expected: " + expected);
        assertTrue(actual.getMessage().contains(expected));
    }

    @Test
    void opretDestillat_TC1_Dato() {
        // Arrange
        LocalDate expectedStartdato = LocalDate.of(2024, 05, 01);
        LocalDate expectedSlutdato = LocalDate.of(2024, 05, 8);
        double expectedLiterVæske = 35;
        int expectedAlkoholProcent = 99;

        // Act
        Destillat actualDestilat = Controller.opretDestillat(expectedStartdato, expectedSlutdato, 35, 99, RygningsType.IKKERØGET, "Meget fint destillat", maltBatch, medarbejder);

        Medarbejder actualMedarbejder = actualDestilat.getMedarbejder();
        MaltBatch actualMaltbatch = actualDestilat.getMaltBatch();
        LocalDate actualStartDato = actualDestilat.getStartDato();
        LocalDate actualSlutDato = actualDestilat.getSlutDato();
        double actualLiterVæske = actualDestilat.getLiterVæske();
        double actualAlkoholProcent = actualDestilat.getAlkoholprocent();
        Destillat actualDestillat = actualDestilat;

        // Assert
        assertTrue(Storage.getDestillater().get(0).equals(actualDestillat));
        assertEquals(medarbejder, actualMedarbejder);
        assertEquals(maltBatch, actualMaltbatch);
        assertEquals(expectedStartdato, actualStartDato);
        assertEquals(expectedSlutdato, actualSlutDato);
        assertEquals(expectedLiterVæske, actualLiterVæske);
        assertEquals(expectedAlkoholProcent, actualAlkoholProcent);
    }

    @Test
    void opretDestillat_TC2_Dato() {
        // Arrange
        LocalDate expectedStartdato = LocalDate.of(2024, 05, 01);
        LocalDate expectedSlutdato = LocalDate.of(2024, 05, 01);
        String expected = "Startdato skal være før slutdato.";

        // Act
        Exception actual = assertThrows(IllegalArgumentException.class, () -> {
            Controller.opretDestillat(expectedStartdato, expectedSlutdato, 35, 99, RygningsType.IKKERØGET, "Meget fint destillat", maltBatch, medarbejder);
        });


        // Assert
        System.out.println("Opretdestillat: TC2");
        System.out.println("Actual: " + actual.getMessage());
        System.out.println("Expected: " + expected);
        assertTrue(actual.getMessage().contains(expected));
    }

    @Test
    void opretDestillat_TC3_Dato() {
        // Arrange
        LocalDate expectedStartdato = LocalDate.of(2024, 05, 8);
        LocalDate expectedSlutdato = LocalDate.of(2024, 05, 01);
        String expected = "Startdato skal være før slutdato.";


        // Act
        Exception actual = assertThrows(IllegalArgumentException.class, () -> {
            Controller.opretDestillat(expectedStartdato, expectedSlutdato, 35, 99, RygningsType.IKKERØGET, "Meget fint destillat", maltBatch, medarbejder);
        });


        // Assert
        System.out.println("Opretdestillat: TC3");
        System.out.println("Actual: " + actual.getMessage());
        System.out.println("Expected: " + expected);
        assertTrue(actual.getMessage().contains(expected));
    }

    @Test
    void opretDestillat_LiterOgAlkoholProcent_TC1() {
        //Arrange
        String expected = "Litermængde skal være over 0.";
        int expectedAntalLiter = -10;
        double expectedAlkoholprocent = 40;

        //Act
        Exception actual = assertThrows(IllegalArgumentException.class, () -> {
            Controller.opretDestillat(LocalDate.of(2024, 05, 01), LocalDate.of(2024, 05, 8), expectedAntalLiter, expectedAlkoholprocent, RygningsType.IKKERØGET, "", maltBatch, medarbejder);
        });

        //Assert
        System.out.println("Opretdestillat: TC3");
        System.out.println("Actual: " + actual.getMessage());
        System.out.println("Expected: " + expected);
        assertTrue(actual.getMessage().contains(expected));
    }

    @Test
    void opretDestillat_LiterOgAlkoholProcent_TC2() {
        //Arrange
        String expected = "Litermængde skal være over 0.";
        int expectedAntalLiter = 0;
        double expectedAlkoholprocent = 40;

        //Act
        Exception actual = assertThrows(IllegalArgumentException.class, () -> {
            Controller.opretDestillat(LocalDate.of(2024, 05, 01), LocalDate.of(2024, 05, 8), expectedAntalLiter, expectedAlkoholprocent, RygningsType.IKKERØGET, "", maltBatch, medarbejder);
        });

        //Assert
        System.out.println("Opretdestillat: TC3");
        System.out.println("Actual: " + actual.getMessage());
        System.out.println("Expected: " + expected);
        assertTrue(actual.getMessage().contains(expected));
    }

    @Test
    void opretDestillat_LiterOgAlkoholProcent_TC3() {
        //Arrange
        String expected = "Alkoholprocent skal være mellem 40 og 100.";
        int expectedAntalLiter = 35;
        double expectedAlkoholprocent = 39.9;

        //Act
        Exception actual = assertThrows(IllegalArgumentException.class, () -> {
            Controller.opretDestillat(LocalDate.of(2024, 05, 01), LocalDate.of(2024, 05, 8), expectedAntalLiter, expectedAlkoholprocent, RygningsType.IKKERØGET, "", maltBatch, medarbejder);
        });

        //Assert
        System.out.println("Opretdestillat: TC3");
        System.out.println("Actual: " + actual.getMessage());
        System.out.println("Expected: " + expected);
        assertTrue(actual.getMessage().contains(expected));
    }

    @Test
    void opretDestillat_LiterOgAlkoholProcent_TC4() {
        // Arrange
        int expectedAntalLiter = 35;
        int expectedAlkoholprocent = 100;
        LocalDate expectedStartdato = LocalDate.of(2024, 05, 01);
        LocalDate expectedSlutdato = LocalDate.of(2024, 05, 8);
        RygningsType expectedRygsningsType = RygningsType.IKKERØGET;

        Destillat expectedDestillat = Controller.opretDestillat(expectedStartdato,
                expectedSlutdato, expectedAntalLiter, expectedAlkoholprocent, expectedRygsningsType, "", maltBatch, medarbejder);


        // Act
        Destillat actualDestillat = Storage.getDestillater().get(0);
        double actualAlkohol = actualDestillat.getAlkoholprocent();
        double actualLiter = actualDestillat.getLiterVæske();

        // Assert
        System.out.println("Opretdestillat TC4");
        System.out.println("Actual: " + actualDestillat + ", Liter: " + actualLiter + ", Alkohol%: " + actualAlkohol + "%");
        System.out.println("Expected: " + expectedDestillat + ", Liter: " + expectedAntalLiter + ", Alkohol%: " + expectedAlkoholprocent + "%");
        assertEquals(expectedDestillat,actualDestillat);
        assertEquals(expectedAntalLiter,actualLiter);
        assertEquals(expectedAlkoholprocent,actualAlkohol);
    }
    @Test
    void opretDestillat_LiterOgAlkoholProcent_TC5() {
        //Arrange
        String expected = "Alkoholprocent skal være mellem 40 og 100.";
        int expectedAntalLiter = 35;
        double expectedAlkoholprocent = 100.1;

        //Act
        Exception actual = assertThrows(IllegalArgumentException.class, () -> {
            Controller.opretDestillat(LocalDate.of(2024, 05, 01), LocalDate.of(2024, 05, 8), expectedAntalLiter, expectedAlkoholprocent, RygningsType.IKKERØGET, "", maltBatch, medarbejder);
        });

        //Assert
        System.out.println("Opretdestillat: TC5");
        System.out.println("Actual: " + actual.getMessage());
        System.out.println("Expected: " + expected);
        assertTrue(actual.getMessage().contains(expected));
    }
}