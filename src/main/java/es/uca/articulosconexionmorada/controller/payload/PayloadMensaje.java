package es.uca.articulosconexionmorada.controller.payload;

public class PayloadMensaje {
    String id;

    String mensaje;

    String uuidEmisor;

    String dateCreated;

    String dateEliminated;

    public PayloadMensaje(){}

    public PayloadMensaje(String id, String mensaje, String uuidEmisor, String dateCreated, String dateEliminated){
        this.id = id;
        this.mensaje = mensaje;
        this.uuidEmisor = uuidEmisor;
        this.dateCreated = dateCreated;
        this.dateEliminated = dateEliminated;
    }

    public String getId(){
        return id;
    }

    public String getMensaje(){
        return mensaje;
    }

    public String getUuidEmisor(){
        return uuidEmisor;
    }

    public String getDateCreated(){
        return dateCreated;
    }

    public String getDateEliminated(){
        return dateEliminated;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setMensaje(String mensaje){
        this.mensaje = mensaje;
    }

    public void setUuidEmisor(String uuidEmisor){
        this.uuidEmisor = uuidEmisor;
    }

    public void setDateCreated(String dateCreated){
        this.dateCreated = dateCreated;
    }

    public void setDateEliminated(String dateEliminated){
        this.dateEliminated = dateEliminated;
    }
}
