package storage;

import application.model.*;

import java.util.ArrayList;

public class Storage {
    private static ArrayList<Fad> fade = new ArrayList<>();
    public static ArrayList<Fad> getFade(){
        return new ArrayList<Fad>(fade);
    }
    public static void addFad(Fad fad){
        fade.add(fad);
    }

    public static void removeFad(Fad fad) {
        fade.remove(fad);
    }
    //------------------------------------------------------------------------------------------------------------------
    private static ArrayList<Destillat> destillater = new ArrayList<>();
    public static ArrayList<Destillat> getDestillater(){
        return new ArrayList<Destillat>(destillater);
    }
    public static void addDestillat(Destillat destillat){
        destillater.add(destillat);
    }
    public static void removeDestillat(Destillat destillat) {
        destillater.remove(destillat);
    }
    //------------------------------------------------------------------------------------------------------------------
    private static ArrayList<Malteri> malterier = new ArrayList<>();
    public static ArrayList<Malteri> getMalterier(){
        return new ArrayList<Malteri>(malterier);
    }
    public static void addMalteri(Malteri malteri){
        malterier.add(malteri);
    }
    //------------------------------------------------------------------------------------------------------------------
    private static ArrayList<Mark> marker = new ArrayList<>();
    public static ArrayList<Mark> getMarker(){
        return new ArrayList<Mark>(marker);
    }
    public static void addMark(Mark mark){
        marker.add(mark);
    }
    //------------------------------------------------------------------------------------------------------------------
    private static ArrayList<Medarbejder> medarbejdere = new ArrayList<>();
    public static ArrayList<Medarbejder> getMedarbejdere(){
        return new ArrayList<Medarbejder>(medarbejdere);
    }
    public static void addMedarbejder(Medarbejder medarbejder){
        medarbejdere.add(medarbejder);
    }
    public static Medarbejder removeMedarbejder(Medarbejder medarbejder){
        medarbejdere.remove(medarbejder);
        return medarbejder;
    }
    //------------------------------------------------------------------------------------------------------------------
    private static ArrayList<Forhandler> forhandlere = new ArrayList<>();
    public static ArrayList<Forhandler> getForhandlere(){
        return new ArrayList<Forhandler>(forhandlere);
    }
    public static void addForhandler(Forhandler forhandler){
        forhandlere.add(forhandler);
    }
    //------------------------------------------------------------------------------------------------------------------
    private static ArrayList<MaltBatch> maltBatches = new ArrayList<>();
    public static ArrayList<MaltBatch> getMaltBatches(){
        return new ArrayList<MaltBatch>(maltBatches);
    }
    public static void addMaltbatch(MaltBatch maltBatch){
        maltBatches.add(maltBatch);
    }

}
