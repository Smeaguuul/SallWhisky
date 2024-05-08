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

    @BeforeEach
    void setUp() {
        juan = new Forhandler("Juan iglesias", "Catalonien", "Spanien");
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
        Medarbejder expectedMedarbejder = new Medarbejder(1, "Mads Medarbejder", "010203-4555", "MAM");
        Adresse adresse = new Adresse("1", "Landmandvej", "6960", "Danmark");
        Mark mark = new Mark("En meget fin Økologisk mark, som dyrkes af Lars Landmand","Langdahl",adresse);
        Malteri malteri = new Malteri("Thy Whisky Malteri", "Et stort malteri i Thy, som opereres af Thy Whisky.",adresse);
        MaltBatch expectedMaltBatch = new MaltBatch(Kornsort.EVERGREEN, 1, LocalDate.of(2024,05,03), malteri, mark);
        LocalDate expectedStartdato = LocalDate.of(2024,05,01);
        LocalDate expectedSlutdato = LocalDate.of(2024,05,8);
        double expectedLiterVæske = 35;
        int expectedAlkoholProcent = 99;

        Destillat expectedDestillat = Controller.opretDestillat(expectedStartdato,expectedSlutdato, 35,99, RygningsType.IKKERØGET,
                "Meget fint destillat",expectedMaltBatch,expectedMedarbejder);

        // Act
        Medarbejder actualMedarbejder = Storage.getDestillater().get(0).getMedarbejder();
        MaltBatch actualMaltbatch = Storage.getDestillater().get(0).getMaltBatch();
        LocalDate actualStartDato = Storage.getDestillater().get(0).getStartDato();
        LocalDate actualSlutDato = Storage.getDestillater().get(0).getSlutDato();
        double actualLiterVæske = Storage.getDestillater().get(0).getLiterVæske();
        double actualAlkoholProcent = Storage.getDestillater().get(0).getAlkoholprocent();
        Destillat actualDestillat = Storage.getDestillater().get(0);

        // Assert
        assertTrue(expectedDestillat.equals(actualDestillat));
        assertEquals(expectedMedarbejder,actualMedarbejder);
        assertEquals(expectedMaltBatch,actualMaltbatch);
        assertEquals(expectedStartdato,actualStartDato);
        assertEquals(expectedSlutdato,actualSlutDato);
        assertEquals(expectedLiterVæske,actualLiterVæske);
        assertEquals(expectedAlkoholProcent,actualAlkoholProcent);

    }

    @Test
    void opretDestillat_LiterOgAlkoholProcent_TC3() {
        //Arrange
        int expectedAntalLiter = 35;
        double expectedAlkoholprocent = 40;
        Adresse adresse = new Adresse("1", "Landmandvej", "6960", "Danmark");
        Mark mark = new Mark("En meget fin Økologisk mark, som dyrkes af Lars Landmand", "Langdahl",  adresse);
        Malteri malteri = new Malteri("Thy Whisky Malteri", "Et stort malteri i Thy, som opereres af Thy Whisky.", adresse);
        MaltBatch expectedMaltBatch = new MaltBatch(Kornsort.EVERGREEN, 1, LocalDate.of(2024,05,03), malteri, mark);
        Medarbejder expectedMedarbejder = new Medarbejder(1, "Mads Medarbejder", "010203-4555", "NAM");


        //Act
        Destillat actual = Controller.opretDestillat(LocalDate.of(2024,05,01), LocalDate.of(2024,05,8),expectedAntalLiter,expectedAlkoholprocent, RygningsType.IKKERØGET,"",expectedMaltBatch, expectedMedarbejder);

        //Assert
        Destillat iStorage = Storage.getDestillater().get(0);
        assertEquals(actual, iStorage);

        System.out.println("Error besked:");
        System.out.println("Actual: " + iStorage);
        System.out.println("Expected: " + actual);


        //TODO Assert et eller andet
    }
}