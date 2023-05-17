package es.uca.articulosconexionmorada.sistemaCompanero.marker;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Marker {

    @Id
    @GeneratedValue
    @Column(length=16)
    private UUID id;
    @Column
    private String latitud;
    @Column
    private String longitud;

    @Column
    private String titulo;

    public Marker(){}

    public Marker(String latitud, String longitud, String titulo) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.titulo = titulo;
    }

    public UUID getId() {
        return id;
    }

    public String getLatitud() {
        return latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
