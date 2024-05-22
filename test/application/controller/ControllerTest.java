package application.controller;

import application.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private static Medarbejder medarbejder;
    private static MaltBatch maltbatch;
    private static Forhandler juan;

    @BeforeAll
    static void beforeAll() {
        Adresse adresse = new Adresse("1", "Gyllevej", "6969", "DK");
        Malteri malteri = new Malteri("Thy", "Malteri i Thy.", adresse);
        medarbejder = new Medarbejder("Mads Medarbejder", "010203-4555", "MAM");
        Mark mark = new Mark("Øko mark", "Langdahl", adresse);
        maltbatch = new MaltBatch(Kornsort.IRINA, 2, LocalDate.of(2024, 04, 17), malteri, mark);
        juan = new Forhandler("Juan Igleasas", "Catalonien", "Spanien");
    }

    @Test
    void udregnAlkoholProcentTest01() {
        // Arrange
        Destillat destillat = new Destillat(LocalDate.now(),LocalDate.now().plusYears(3), 82, 60, RygningsType.TØRVRØGET, "", medarbejder, maltbatch);
        Forhandler forhandler = new Forhandler("Juan Igleasas", "Catalonien", "Spanien");

        Fad gammeltFad = new Fad(Træsort.QUERCUSALBA,"", TidligereIndhold.SHERRY, 100, forhandler);
        HashMap<Væske, Double> hashMap = new HashMap<Væske, Double>();
        hashMap.put(destillat, 36.00);
        Make expectedMake = new Make(gammeltFad, hashMap, LocalDate.now());
        TapningsVæske væske01 = new TapningsVæske(60, 32, expectedMake);

        Fad gammeltFad02 = new Fad(Træsort.QUERCUSALBA,"", TidligereIndhold.SHERRY, 100, forhandler);
        HashMap<Væske, Double> hashMap02 = new HashMap<Væske, Double>();
        hashMap02.put(destillat, 36.00);
        Make expectedMake02 = new Make(gammeltFad, hashMap02, LocalDate.now());
        TapningsVæske væske02 = new TapningsVæske(60, 32, expectedMake02);

        double literVand = 10;
        double literVand02 = 15;
        double forvæntedeStyrke = 37.16;
        ArrayList<TapningsVæske> tapningsVæsker = new ArrayList<>();
        tapningsVæsker.add(væske01);
        tapningsVæsker.add(væske02);

        // Act
        Double actualProcent = Controller.udregnAlkoholProcent(tapningsVæsker, literVand);

        // Assert
        System.out.println("UdregnAlkoholprocent: TC 1");
        System.out.println("\tActual:\t\t" + actualProcent);
        System.out.println("\tExpected:\t" + forvæntedeStyrke);

    }
    void udregnAlkoholProcentTest02() {

    }
    @Test
    void opretMakeTC1_Gyldig() throws Exception {
        //Arrange
        Destillat destillat0 = new Destillat(LocalDate.now().minusDays(2), LocalDate.now(), 35, 75, RygningsType.IKKERØGET, "", medarbejder, maltbatch);
        Destillat destillat1 = new Destillat(LocalDate.now().minusDays(2), LocalDate.now(), 35, 90, RygningsType.TØRVRØGET, "", medarbejder, maltbatch);
        Fad expectedFad = new Fad(Træsort.QUERCUSALBA,"",TidligereIndhold.SHERRY, 70, juan);
        HashMap<Væske,Double> expectedVæskeOgLiter = new HashMap<>();
        expectedVæskeOgLiter.put(destillat0, 35.00);
        expectedVæskeOgLiter.put(destillat1, 35.00);
        int expectedNuværendeMængdeDestillat0 = 0;
        int expectedNuværendeMængdeDestillat1 = 0;
        double expectedNuværendeMængdeMake = 70.0;

        //Act
        Make actualMake = Controller.opretMake(expectedFad, expectedVæskeOgLiter);
        Make storageMake = (Make) Storage.getVæsker().get(Storage.getVæsker().size() - 1);

        //Assert
        System.out.println("opretMake: TC1");
        System.out.println("\tActual:\t\t" + actualMake.getNuværendeMængde());
        System.out.println("\tExpected:\t" + expectedNuværendeMængdeMake);

        //Tester om den er landet i storage
        assertEquals(actualMake, storageMake);

        //Tester værdierne i destillaterne
        assertEquals(expectedNuværendeMængdeDestillat0, destillat0.getNuværendeMængde());
        assertEquals(expectedNuværendeMængdeDestillat1, destillat1.getNuværendeMængde());

        //Tester værdierne i Make
        assertEquals(expectedVæskeOgLiter, actualMake.getVæskeOgLiter());
        assertEquals(expectedNuværendeMængdeMake, actualMake.getNuværendeMængde());
    }

    @Test
    void opretMakeTC2_Gyldig() throws Exception {
        //Arrange
        Destillat destillat0 = new Destillat(LocalDate.now().minusDays(2), LocalDate.now(), 35, 75, RygningsType.IKKERØGET, "", medarbejder, maltbatch);
        Destillat destillat1 = new Destillat(LocalDate.now().minusDays(2), LocalDate.now(), 35, 90, RygningsType.TØRVRØGET, "", medarbejder, maltbatch);
        Fad expectedFad = new Fad(Træsort.QUERCUSALBA,"",TidligereIndhold.SHERRY, 70, juan);
        HashMap<Væske,Double> expectedVæskeOgLiter = new HashMap<>();
        expectedVæskeOgLiter.put(destillat0, 20.0);
        expectedVæskeOgLiter.put(destillat1, 10.0);
        int expectedNuværendeMængdeDestillat0 = 15;
        int expectedNuværendeMængdeDestillat1 = 25;
        double expectedNuværendeMængdeMake = 30.0;

        //Act
        Make actualMake = Controller.opretMake(expectedFad, expectedVæskeOgLiter);
        Make storageMake = (Make) Storage.getVæsker().get(Storage.getVæsker().size() - 1);

        //Assert
        System.out.println("opretMake: TC2");
        System.out.println("\tActual:\t\t" + actualMake.getNuværendeMængde());
        System.out.println("\tExpected:\t" + expectedNuværendeMængdeMake);

        //Tester værdierne i destillaterne
        assertEquals(expectedNuværendeMængdeDestillat0, destillat0.getNuværendeMængde());
        assertEquals(expectedNuværendeMængdeDestillat1, destillat1.getNuværendeMængde());

        //Tester værdierne i Make
        assertEquals(expectedVæskeOgLiter, actualMake.getVæskeOgLiter());
        assertEquals(expectedNuværendeMængdeMake, actualMake.getNuværendeMængde());
    }

    @Test
    void opretMakeTC1_Ugyldig() {
        //Arrange
        Destillat destillat0 = new Destillat(LocalDate.now().minusDays(2), LocalDate.now(), 35, 75, RygningsType.IKKERØGET, "", medarbejder, maltbatch);
        Destillat destillat1 = new Destillat(LocalDate.now().minusDays(2), LocalDate.now(), 35, 90, RygningsType.TØRVRØGET, "", medarbejder, maltbatch);
        Destillat destillat2 = new Destillat(LocalDate.now().minusDays(2), LocalDate.now(), 35, 90, RygningsType.TØRVRØGET, "", medarbejder, maltbatch);
        Fad expectedFad = new Fad(Træsort.QUERCUSALBA,"",TidligereIndhold.SHERRY, 70, juan);
        HashMap<Væske,Double> expectedVæskeOgLiter = new HashMap<>();
        expectedVæskeOgLiter.put(destillat0, 35.00);
        expectedVæskeOgLiter.put(destillat1, 35.00);
        expectedVæskeOgLiter.put(destillat2, 35.00);
        String expected = "Der er ikke  plads i fadet.";

        //Act
        Exception actual = assertThrows(IllegalArgumentException.class, () -> {
            Controller.opretMake(expectedFad, expectedVæskeOgLiter);
        });

        //Assert
        System.out.println("opretMake: Ugyldig TC1");
        System.out.println("\tActual:\t\t" + actual.getMessage());
        System.out.println("\tExpected:\t" + expected);

        assertTrue(actual.getMessage().contains(expected));
    }

    @Test
    void opretMakeTC2_Ugyldig() {
        //Arrange
        Destillat destillat0 = new Destillat(LocalDate.now().minusDays(2), LocalDate.now(), 35, 75, RygningsType.IKKERØGET, "", medarbejder, maltbatch);
        Destillat destillat1 = new Destillat(LocalDate.now().minusDays(2), LocalDate.now(), 35, 90, RygningsType.TØRVRØGET, "", medarbejder, maltbatch);
        Destillat destillat2 = new Destillat(LocalDate.now().minusDays(2), LocalDate.now(), 35, 90, RygningsType.TØRVRØGET, "", medarbejder, maltbatch);
        Fad expectedFad = new Fad(Træsort.QUERCUSALBA,"",TidligereIndhold.SHERRY, 70, juan);
        HashMap<Væske,Double> expectedVæskeOgLiter = new HashMap<>();
        expectedVæskeOgLiter.put(destillat0, 45.0);
        expectedVæskeOgLiter.put(destillat1, 10.0);
        expectedVæskeOgLiter.put(destillat2, 10.0);
        String expected = "Der er ikke nok væske i de valgte væsker.";

        //Act
        Exception actual = assertThrows(IllegalArgumentException.class, () -> {
            Controller.opretMake(expectedFad, expectedVæskeOgLiter);
        });

        //Assert
        System.out.println("opretMake: Ugyldig TC2");
        System.out.println("\tActual:\t\t" + actual.getMessage());
        System.out.println("\tExpected:\t" + expected);

        assertTrue(actual.getMessage().contains(expected));
    }

    @Test
    void opretMakeTC3_Ugyldig() {
        //Arrange
        Fad expectedFad = new Fad(Træsort.QUERCUSALBA,"",TidligereIndhold.SHERRY, 70, juan);
        HashMap<Væske,Double> expectedVæskeOgLiter = new HashMap<>();
        String expected = "Du skal medgive mindst en væske";

        //Act
        Exception actual = assertThrows(IllegalArgumentException.class, () -> {
            Controller.opretMake(expectedFad, expectedVæskeOgLiter);
        });

        //Assert
        System.out.println("opretMake: Ugyldig TC3");
        System.out.println("\tActual:\t\t" + actual.getMessage());
        System.out.println("\tExpected:\t" + expected);

        assertTrue(actual.getMessage().contains(expected));
    }
}