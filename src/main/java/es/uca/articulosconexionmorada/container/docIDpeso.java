package es.uca.articulosconexionmorada.container;

import java.util.HashMap;

public class docIDpeso {

    public HashMap<String, Double> docId = new HashMap<String, Double>() ;
    private double IDF ;

    public docIDpeso(double IDF) {
        this.IDF = IDF;
    }
    public docIDpeso() {}

    public void setIDF(double IDF) {
        this.IDF = IDF;
    }
    //get IDF
    public double getIDF() {
        return IDF;
    }

}