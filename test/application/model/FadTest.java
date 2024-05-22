package application.model;

import application.controller.Controller;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class FadTest {

    @Test
    void addMake_TC1() {
        //Arrange
        Forhandler forhandler = new Forhandler("Juan Igleasas", "Catalonien", "Spanien");
        Fad gammeltFad = new Fad(Træsort.QUERCUSALBA, "", TidligereIndhold.SHERRY, 100, forhandler);
        Fad nytFad = new Fad(Træsort.QUERCUSALBA, "", TidligereIndhold.SHERRY, 200, forhandler);
        Adresse adresse = new Adresse("1", "Gyllevej", "6969", "DK");
        Malteri malteri = new Malteri("Thy", "Malteri i Thy.", adresse);
        Medarbejder medarbejder = new Medarbejder("Mads Medarbejder", "010203-4555", "MAM");
        Mark mark = new Mark("Øko mark", "Langdahl", adresse);
        MaltBatch maltbatch = new MaltBatch(Kornsort.IRINA, 2, LocalDate.of(2024, 04, 17), malteri, mark);
        Destillat destillat = new Destillat(LocalDate.now().minusDays(4), LocalDate.now(), 50, 45, RygningsType.TØRVRØGET, "", medarbejder, maltbatch);
        HashMap<Væske, Double> hashMap = new HashMap<>();
        hashMap.put(destillat, 10.00);
        Make expectedMake = new Make(gammeltFad, hashMap, LocalDate.now());
        int expectedTidsperioodeSize = 1;

        // Act
        Tidsperiode actualTidsperiode = nytFad.addMake(expectedMake, LocalDate.now());
        int actualTidsperiodeSize = nytFad.getTidsperioder().size();
        try {
            Make actualMake = nytFad.getMake();


            // Assert
            assertTrue(actualTidsperiode != null);
            assertTrue(expectedTidsperioodeSize == actualTidsperiodeSize);
            assertEquals(expectedMake, actualMake);
            System.out.println("addMake: TC1");
            System.out.println("\tTidsperiode: " + nytFad.getTidsperioder().get(0));
            System.out.println("\tActual make: " + actualMake);
            System.out.println("\tExpected make : " + expectedMake);
            System.out.println("\tActual:\t\t" + actualTidsperiodeSize);
            System.out.println("\tExpected:\t" + expectedTidsperioodeSize);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getMake_TC1() {
        // Arrange
        Forhandler forhandler = new Forhandler("Juan Igleasas", "Catalonien", "Spanien");
        Fad testFad = new Fad(Træsort.QUERCUSALBA, "", TidligereIndhold.SHERRY, 200, forhandler);
        Adresse adresse = new Adresse("1", "Gyllevej", "6969", "DK");
        Malteri malteri = new Malteri("Thy", "Malteri i Thy.", adresse);
        Medarbejder medarbejder = new Medarbejder("Mads Medarbejder", "010203-4555", "MAM");
        Mark mark = new Mark("Øko mark", "Langdahl", adresse);
        MaltBatch maltbatch = new MaltBatch(Kornsort.IRINA, 2, LocalDate.of(2024, 04, 17), malteri, mark);
        Destillat destillat1 = new Destillat(LocalDate.now().minusDays(5), LocalDate.now().minusDays(1), 50, 45, RygningsType.TØRVRØGET, "", medarbejder, maltbatch);
        HashMap<Væske, Double> hashMap1 = new HashMap<>();
        hashMap1.put(destillat1, 10.00);
        Destillat destillat2 = new Destillat(LocalDate.now(), LocalDate.now().plusDays(4), 50, 45, RygningsType.TØRVRØGET, "", medarbejder, maltbatch);
        HashMap<Væske, Double> hashMap2 = new HashMap<>();
        hashMap2.put(destillat2, 10.00);

        String expected = "Intet nuværende make.";

        // Act
        Exception actual = assertThrows(Exception.class, () -> {
            testFad.getMake();
        });

        // Assert
        assertEquals(expected, actual.getMessage());
        System.out.println("getMake: TC1");
        System.out.println("\tActual:\t\t" + actual.getMessage());
        System.out.println("\tExpected:\t" + expected);

    }

    @Test
    void getMake_TC2() throws Exception {
        // Arrange
        Forhandler forhandler = new Forhandler("Juan Igleasas", "Catalonien", "Spanien");
        Fad fad = new Fad(Træsort.QUERCUSALBA, "", TidligereIndhold.SHERRY, 200, forhandler);
        Adresse adresse = new Adresse("1", "Gyllevej", "6969", "DK");
        Malteri malteri = new Malteri("Thy", "Malteri i Thy.", adresse);
        Medarbejder medarbejder = new Medarbejder("Mads Medarbejder", "010203-4555", "MAM");
        Mark mark = new Mark("Øko mark", "Langdahl", adresse);
        MaltBatch maltbatch = new MaltBatch(Kornsort.IRINA, 2, LocalDate.of(2024, 04, 17), malteri, mark);
        Destillat destillat1 = new Destillat(LocalDate.now().minusDays(5), LocalDate.now().minusDays(1), 50, 45, RygningsType.TØRVRØGET, "", medarbejder, maltbatch);
        HashMap<Væske, Double> hashMap1 = new HashMap<>();
        hashMap1.put(destillat1, 10.00);
        Destillat destillat2 = new Destillat(LocalDate.now(), LocalDate.now().plusDays(4), 50, 45, RygningsType.TØRVRØGET, "", medarbejder, maltbatch);
        HashMap<Væske, Double> hashMap2 = new HashMap<>();
        hashMap2.put(destillat2, 10.00);

        // firstMake
        Make expected = new Make(fad, hashMap1, LocalDate.now().minusDays(1));

        // Act
        Make actual = fad.getMake();

        // Assert
        assertEquals(expected, actual);
        System.out.println("getMake: TC2");
        System.out.println("\tActual:\t\t" + actual);
        System.out.println("\tExpected:\t" + expected);
    }

    @Test
    void getMake_TC3() throws Exception {
        // Arrange
        Forhandler forhandler = new Forhandler("Juan Igleasas", "Catalonien", "Spanien");
        Fad fad = new Fad(Træsort.QUERCUSALBA, "", TidligereIndhold.SHERRY, 200, forhandler);
        Adresse adresse = new Adresse("1", "Gyllevej", "6969", "DK");
        Malteri malteri = new Malteri("Thy", "Malteri i Thy.", adresse);
        Medarbejder medarbejder = new Medarbejder("Mads Medarbejder", "010203-4555", "MAM");
        Mark mark = new Mark("Øko mark", "Langdahl", adresse);
        MaltBatch maltbatch = new MaltBatch(Kornsort.IRINA, 2, LocalDate.of(2024, 04, 17), malteri, mark);
        Destillat destillat1 = new Destillat(LocalDate.now().minusDays(5), LocalDate.now().minusDays(1), 50, 45, RygningsType.TØRVRØGET, "", medarbejder, maltbatch);
        HashMap<Væske, Double> hashMap1 = new HashMap<>();
        hashMap1.put(destillat1, 10.00);
        Destillat destillat2 = new Destillat(LocalDate.now(), LocalDate.now().plusDays(4), 50, 45, RygningsType.TØRVRØGET, "", medarbejder, maltbatch);
        HashMap<Væske, Double> hashMap2 = new HashMap<>();
        hashMap2.put(destillat2, 10.00);

        Make firstMake = new Make(fad, hashMap1, LocalDate.now().minusDays(1));
        // lastMake
        Make expected = new Make(fad, hashMap2, LocalDate.now());

        // Act
        Make actual = fad.getMake();

        // Assert
        assertEquals(expected, actual);
        System.out.println("getMake: TC3");
        System.out.println("\tActual:\t\t" + actual);
        System.out.println("\tExpected:\t" + expected);
    }

    @Test
    void erKlar_TC1() {
        // Arrange
        Forhandler forhandler = new Forhandler("Juan Igleasas", "Catalonien", "Spanien");
        Fad fad = new Fad(Træsort.QUERCUSALBA, "", TidligereIndhold.SHERRY, 200, forhandler);
        Adresse adresse = new Adresse("1", "Gyllevej", "6969", "DK");
        Malteri malteri = new Malteri("Thy", "Malteri i Thy.", adresse);
        Medarbejder medarbejder = new Medarbejder("Mads Medarbejder", "010203-4555", "MAM");
        Mark mark = new Mark("Øko mark", "Langdahl", adresse);
        MaltBatch maltbatch = new MaltBatch(Kornsort.IRINA, 2, LocalDate.of(2024, 04, 17), malteri, mark);
        Destillat destillat = new Destillat(LocalDate.now().minusDays(5), LocalDate.now().minusDays(1), 50, 45, RygningsType.TØRVRØGET, "", medarbejder, maltbatch);
        HashMap<Væske, Double> hashMap = new HashMap<>();
        hashMap.put(destillat, 10.00);

        boolean expected = false;

        // Act
        boolean actual = fad.erKlar();

        // Assert
        assertEquals(expected, actual);
        System.out.println("erKlar: TC1");
        System.out.println("\tActual:\t\t" + actual);
        System.out.println("\tExpected:\t" + expected);
    }

    @Test
    void erKlar_TC2() {
        // Arrange
        Forhandler forhandler = new Forhandler("Juan Igleasas", "Catalonien", "Spanien");
        Fad fad = new Fad(Træsort.QUERCUSALBA, "", TidligereIndhold.SHERRY, 200, forhandler);
        Adresse adresse = new Adresse("1", "Gyllevej", "6969", "DK");
        Malteri malteri = new Malteri("Thy", "Malteri i Thy.", adresse);
        Medarbejder medarbejder = new Medarbejder("Mads Medarbejder", "010203-4555", "MAM");
        Mark mark = new Mark("Øko mark", "Langdahl", adresse);
        MaltBatch maltbatch = new MaltBatch(Kornsort.IRINA, 2, LocalDate.of(2024, 04, 17), malteri, mark);
        Destillat destillat = new Destillat(LocalDate.now().minusDays(5), LocalDate.now().minusDays(1), 50, 45, RygningsType.TØRVRØGET, "", medarbejder, maltbatch);
        HashMap<Væske, Double> hashMap = new HashMap<>();
        hashMap.put(destillat, 10.00);

        Make make = new Make(fad, hashMap, LocalDate.now());
        boolean expected = false;

        // Act
        boolean actual = fad.erKlar();

        // Assert
        assertEquals(expected, actual);
        System.out.println("erKlar: TC2");
        System.out.println("\tActual:\t\t" + actual);
        System.out.println("\tExpected:\t" + expected);
    }

    @Test
    void erKlar_TC3() {
        // Arrange
        Forhandler forhandler = new Forhandler("Juan Igleasas", "Catalonien", "Spanien");
        Fad fad = new Fad(Træsort.QUERCUSALBA, "", TidligereIndhold.SHERRY, 200, forhandler);
        Adresse adresse = new Adresse("1", "Gyllevej", "6969", "DK");
        Malteri malteri = new Malteri("Thy", "Malteri i Thy.", adresse);
        Medarbejder medarbejder = new Medarbejder("Mads Medarbejder", "010203-4555", "MAM");
        Mark mark = new Mark("Øko mark", "Langdahl", adresse);
        MaltBatch maltbatch = new MaltBatch(Kornsort.IRINA, 2, LocalDate.of(2024, 04, 17), malteri, mark);
        Destillat destillat = new Destillat(LocalDate.now().minusDays(5), LocalDate.now().minusDays(1), 50, 45, RygningsType.TØRVRØGET, "", medarbejder, maltbatch);
        HashMap<Væske, Double> hashMap = new HashMap<>();
        hashMap.put(destillat, 10.00);

        Make make = new Make(fad, hashMap, LocalDate.now().minusYears(4));
        boolean expected = true;

        // Act
        boolean actual = fad.erKlar();

        // Assert
        assertEquals(expected, actual);
        System.out.println("addMake: TC3");
        System.out.println("\tActual:\t\t" + actual);
        System.out.println("\tExpected:\t" + expected);
    }

    @Test
    void hasMake_TC1() {
        // Arrange
        Forhandler forhandler = new Forhandler("Juan Igleasas", "Catalonien", "Spanien");
        Fad fad = new Fad(Træsort.QUERCUSALBA, "", TidligereIndhold.SHERRY, 200, forhandler);
        Adresse adresse = new Adresse("1", "Gyllevej", "6969", "DK");
        Malteri malteri = new Malteri("Thy", "Malteri i Thy.", adresse);
        Medarbejder medarbejder = new Medarbejder("Mads Medarbejder", "010203-4555", "MAM");
        Mark mark = new Mark("Øko mark", "Langdahl", adresse);
        MaltBatch maltbatch = new MaltBatch(Kornsort.IRINA, 2, LocalDate.of(2024, 04, 17), malteri, mark);
        Destillat destillat = new Destillat(LocalDate.now().minusDays(5), LocalDate.now().minusDays(1), 50, 45, RygningsType.TØRVRØGET, "", medarbejder, maltbatch);
        HashMap<Væske, Double> hashMap = new HashMap<>();
        hashMap.put(destillat, 10.00);

        Make make = new Make(fad, hashMap, LocalDate.now());
        boolean expected = true;

        // Act
        Boolean actual = fad.hasMake();

        // Assert
        assertEquals(expected, actual);
        System.out.println("hasMake: TC1");
        System.out.println("\tActual:\t\t" + actual);
        System.out.println("\tExpected:\t" + expected);
    }

    @Test
    void hasMake_TC2() {
        // Arrange
        Forhandler forhandler = new Forhandler("Juan Igleasas", "Catalonien", "Spanien");
        Fad fad = new Fad(Træsort.QUERCUSALBA, "", TidligereIndhold.SHERRY, 200, forhandler);
        Adresse adresse = new Adresse("1", "Gyllevej", "6969", "DK");
        Malteri malteri = new Malteri("Thy", "Malteri i Thy.", adresse);
        Medarbejder medarbejder = new Medarbejder("Mads Medarbejder", "010203-4555", "MAM");
        Mark mark = new Mark("Øko mark", "Langdahl", adresse);
        MaltBatch maltbatch = new MaltBatch(Kornsort.IRINA, 2, LocalDate.of(2024, 04, 17), malteri, mark);
        Destillat destillat = new Destillat(LocalDate.now().minusDays(5), LocalDate.now().minusDays(1), 50, 45, RygningsType.TØRVRØGET, "", medarbejder, maltbatch);
        HashMap<Væske, Double> hashMap = new HashMap<>();
        hashMap.put(destillat, 10.00);

        boolean expected = false;

        // Act
        Boolean actual = fad.hasMake();

        // Assert
        assertEquals(expected, actual);
        System.out.println("hasMake: TC2");
        System.out.println("\tActual:\t\t" + actual);
        System.out.println("\tExpected:\t" + expected);
    }

    @Test
    void setLagerlokation_TC1() throws Exception {
        //TODO
        Forhandler forhandler = new Forhandler("Juan Igleasas", "Catalonien", "Spanien");
        Fad fad = new Fad(Træsort.QUERCUSALBA, "", TidligereIndhold.SHERRY, 200, forhandler);
        Fad fad2 = new Fad(Træsort.QUERCUSALBA,"", TidligereIndhold.BOURBON, 200, forhandler);
        Adresse adresse = new Adresse("1", "Gyllevej", "6969", "DK");
        Lager lager = new Lager("Lagersted", adresse, 10, 5, 10);

        int expectedReolnummer = 1;
        int expectedHøjde = 1;
        int expectedPlaceringsNr = 1;

        // Act
        fad.setLagerlokation(lager, 1, 1, 1);
        int[] actualLagerLokation = fad.getLagerLokation();
        int actualReolNr = actualLagerLokation[0];
        int actualHøjde = actualLagerLokation[0];
        int actualPlaceringNr = actualLagerLokation[0];

        // Assert
        System.out.println("setLagerLokation: TC1");
        System.out.println("\tActual Reol Nr:\t\t\t" + actualReolNr);
        System.out.println("\tExpected Reol Nr:\t\t" + expectedReolnummer);
        assertEquals(actualReolNr,expectedReolnummer);
        System.out.println("\tActual Højde Nr:\t\t" + actualHøjde);
        System.out.println("\tExpected Højde Nr:\t\t" + expectedHøjde);
        assertEquals(actualHøjde,expectedHøjde);
        System.out.println("\tActual PlaceringsNr:\t" + actualPlaceringNr);
        System.out.println("\tExpected PlaceringsNr:\t" + expectedPlaceringsNr);
        assertEquals(actualPlaceringNr,expectedPlaceringsNr);
    }
    @Test
    void setLagerlokation_TC2() throws Exception {
        //TODO
        Forhandler forhandler = new Forhandler("Juan Igleasas", "Catalonien", "Spanien");
        Fad fad = new Fad(Træsort.QUERCUSALBA, "", TidligereIndhold.SHERRY, 200, forhandler);
        Fad fad2 = new Fad(Træsort.QUERCUSALBA,"", TidligereIndhold.BOURBON, 200, forhandler);
        Adresse adresse = new Adresse("1", "Gyllevej", "6969", "DK");
        Lager lager = new Lager("Lagersted", adresse, 10, 5, 10);
        fad2.setLagerlokation(lager, 1, 1, 1);

        String expected = "Der er ikke plads der.";

        // Act
        Exception actual = assertThrows(IllegalArgumentException.class, () -> {
            fad.setLagerlokation(lager,1,1,1);
        });

        // Assert
        System.out.println("setLagerLokation: TC2");
        assertEquals(expected,actual.getMessage());
        System.out.println("\tActual:\t\t" + actual.getMessage());
        System.out.println("\tExpected:\t" + expected);
    }
    @Test
    void setLagerlokation_TC3() throws Exception {
        //TODO
        Forhandler forhandler = new Forhandler("Juan Igleasas", "Catalonien", "Spanien");
        Fad fad = new Fad(Træsort.QUERCUSALBA, "", TidligereIndhold.SHERRY, 200, forhandler);
        Fad fad2 = new Fad(Træsort.QUERCUSALBA,"", TidligereIndhold.BOURBON, 200, forhandler);
        Adresse adresse = new Adresse("1", "Gyllevej", "6969", "DK");
        Lager lager = new Lager("Lagersted", adresse, 10, 5, 10);

        String expected = "Det er ikke en lokation på lageret";

        // Act
        Exception actual = assertThrows(IllegalArgumentException.class, () -> {
            fad.setLagerlokation(lager,100000,1,1);
        });

        // Assert
        System.out.println("setLagerLokation: TC2");
        assertEquals(expected,actual.getMessage());
        System.out.println("\tActual:\t\t" + actual.getMessage());
        System.out.println("\tExpected:\t" + expected);
    }

    @Test
    void fjernLagerLokation() throws Exception {
        Forhandler forhandler = new Forhandler("Juan Igleasas", "Catalonien", "Spanien");
        Fad fad = new Fad(Træsort.QUERCUSALBA, "", TidligereIndhold.SHERRY, 200, forhandler);
        Adresse adresse = new Adresse("1", "Gyllevej", "6969", "DK");
        Lager lager = new Lager("Lagersted", adresse, 10, 5, 10);
        //fad.setLagerlokation(lager, 1, 1, 1);

        Lager expectedLager = null;
        int[] expectedLokation = null;

        // Act
        fad.fjernLagerLokation();
        Lager actualLager = fad.getLager();
        int[] actualLokation = fad.getLagerLokation();

        // Assert
        System.out.println("fjernLagerLokation: TC1");
        assertEquals(expectedLager, actualLager);
        System.out.println("\tActual Lager:\t\t" + actualLager);
        System.out.println("\tExpected Lager:\t\t" + expectedLager);
        assertEquals(expectedLokation, actualLokation);
        System.out.println("\tActual Lokation:\t" + actualLokation);
        System.out.println("\tExpected Lokation:\t" + expectedLokation);

    }

    @Test
    void harLagerLokation_TC1() throws Exception {
        Forhandler forhandler = new Forhandler("Juan Igleasas", "Catalonien", "Spanien");
        Fad fad = new Fad(Træsort.QUERCUSALBA, "", TidligereIndhold.SHERRY, 200, forhandler);
        Adresse adresse = new Adresse("1", "Gyllevej", "6969", "DK");
        Lager lager = new Lager("Lagersted", adresse, 10, 5, 10);
        fad.setLagerlokation(lager, 1, 1, 1);

        boolean expected = true;

        // Act
        boolean actual = fad.harLagerlokation();

        // Assert
        System.out.println("harLagerlokation(): TC1");
        assertEquals(expected, actual);
        System.out.println("\tActual:\t\t" + actual);
        System.out.println("\tExpected:\t" + expected);
    }
    @Test
    void harLagerLokation_TC2() throws Exception {
        Forhandler forhandler = new Forhandler("Juan Igleasas", "Catalonien", "Spanien");
        Fad fad = new Fad(Træsort.QUERCUSALBA, "", TidligereIndhold.SHERRY, 200, forhandler);
        Adresse adresse = new Adresse("1", "Gyllevej", "6969", "DK");
        Lager lager = new Lager("Lagersted", adresse, 10, 5, 10);

        boolean expected = false;

        // Act
        boolean actual = fad.harLagerlokation();

        // Assert
        System.out.println("harLagerlokation(): TC2");
        assertEquals(expected, actual);
        System.out.println("\tActual:\t\t" + actual);
        System.out.println("\tExpected:\t" + expected);
    }
}
