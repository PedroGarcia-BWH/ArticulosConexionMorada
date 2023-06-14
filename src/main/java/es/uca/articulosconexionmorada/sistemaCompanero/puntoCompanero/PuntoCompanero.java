package es.uca.articulosconexionmorada.sistemaCompanero.puntoCompanero;

import es.uca.articulosconexionmorada.sistemaCompanero.marker.Marker;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class PuntoCompanero {

    @Id
    @GeneratedValue
    @Column(length=16)
    private UUID id;

    @Column
    private String uuidSolicitante;

    @Column
    private String uuidAceptante;

    @OneToOne
    private Marker markerOrigen;

    @OneToOne
    private Marker markerDestino;

    @Column
    private Date dateCreated;

    @Column
    private Date dateEliminated;

    @Column
    private Date dateEvento;

    public PuntoCompanero(){}

    public PuntoCompanero(String uuidSolicitante, String uuidAceptante, Marker markerOrigen, Marker markerDestino, Date dateEvento) {
        this.uuidSolicitante = uuidSolicitante;
        this.uuidAceptante = uuidAceptante;
        this.markerOrigen = markerOrigen;
        this.markerDestino = markerDestino;
        this.dateEvento = dateEvento;
        this.dateCreated = new Date();
    }

    public UUID getId() {
        return id;
    }

    public String getUuidSolicitante() {
        return uuidSolicitante;
    }

    public String getUuidAceptante() {
        return uuidAceptante;
    }

    public Marker getMarkerOrigen() {
        return markerOrigen;
    }

    public Marker getMarkerDestino() {
        return markerDestino;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getDateEliminated() {
        return dateEliminated;
    }

    public Date getDateEvento() {
        return dateEvento;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUuidSolicitante(String uuidSolicitante) {
        this.uuidSolicitante = uuidSolicitante;
    }

    public void setUuidAceptante(String uuidAceptante) {
        this.uuidAceptante = uuidAceptante;
    }

    public void setMarkerOrigen(Marker markerOrigen) {
        this.markerOrigen = markerOrigen;
    }

    public void setMarkerDestino(Marker markerDestino) {
        this.markerDestino = markerDestino;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateEliminated(Date dateEliminated) {
        this.dateEliminated = dateEliminated;
    }

    public void setDateEvento(Date dateEvento) {
        this.dateEvento = dateEvento;
    }

    public boolean isEliminado() {
        return dateEliminated != null;
    }

    public void eliminar() {
        dateEliminated = new Date();
    }

    public boolean isAceptado() {
        return uuidAceptante != null;
    }

    public void aceptar(String uuidAceptante) {
        this.uuidAceptante = uuidAceptante;
    }

    public boolean isSolicitado() {
        return uuidSolicitante != null;
    }

    public void solicitar(String uuidSolicitante) {
        this.uuidSolicitante = uuidSolicitante;
    }

    public boolean isEvento() {
        return dateEvento != null;
    }

    public void setEvento(Date dateEvento) {
        this.dateEvento = dateEvento;
    }

    public void eliminarEvento() {
        this.dateEvento = null;
    }

    public boolean isEventoPasado() {
        return dateEvento.before(new Date());
    }

    public boolean isEventoFuturo() {
        return dateEvento.after(new Date());
    }

    public boolean isEventoHoy() {
        Date hoy = new Date();
        return dateEvento.getYear() == hoy.getYear() && dateEvento.getMonth() == hoy.getMonth() && dateEvento.getDate() == hoy.getDate();
    }

}
