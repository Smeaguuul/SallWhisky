package application.controller;

import application.model.*;
import org.junit.jupiter.api.Test;
import storage.Storage;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

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

    @Test
    void opretFad() {
    }

    @Test
    void opretDestillat_LiterOgAlkoholProcent_TC3() {
        //Arrange
        int expectedAntalLiter = 35;
        double expectedAlkoholprocent = 40;
        Addresse adresse = new Addresse("1", "Landmandvej", "6960", "Danmark");
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