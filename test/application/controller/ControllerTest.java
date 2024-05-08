package application.controller;

import application.model.Medarbejder;
import org.junit.jupiter.api.Test;
import storage.Storage;

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
    void opretDestillat() {
    }
}