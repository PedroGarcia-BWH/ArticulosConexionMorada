package es.uca.articulosconexionmorada.container;

public class ContainerDocId {
    public String sDocID;
    public double iPeso;

    public ContainerDocId(String sDocID, double iPeso) {
        this.sDocID= sDocID;
        this.iPeso = iPeso;
        System.out.println(iPeso);
    }

}
