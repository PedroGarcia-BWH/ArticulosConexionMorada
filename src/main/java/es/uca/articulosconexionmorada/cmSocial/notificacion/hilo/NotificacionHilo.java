package es.uca.articulosconexionmorada.cmSocial.notificacion.hilo;

import com.sun.istack.NotNull;
import es.uca.articulosconexionmorada.cmSocial.hilo.Hilo;
import es.uca.articulosconexionmorada.username.Username;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class NotificacionHilo {
    @Id
    @GeneratedValue
    @Column(length=16)
    private UUID id;

    @ManyToOne
    private Username userNotificador;

    @ManyToOne
    private Username userNotificado;

    @ManyToOne
    private Hilo hilo;

    @Column
    @NotNull
    private String mensaje;

    @Column
    private Date dateCreation;

    @Column
    private Date dateElimination;

    public NotificacionHilo(){}

    public NotificacionHilo(Username userNotificador, Username userNotificado, Hilo hilo, String mensaje, Date dateCreation, Date dateElimination) {
        this.userNotificador = userNotificador;
        this.userNotificado = userNotificado;
        this.hilo = hilo;
        this.mensaje = mensaje;
        this.dateCreation = dateCreation;
        this.dateElimination = dateElimination;
    }

    //getters
    public UUID getId() {
        return id;
    }

    public Username getUserNotificador() {
        return userNotificador;
    }

    public Username getUserNotificado() {
        return userNotificado;
    }

    public Hilo getHilo() {
        return hilo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public Date getDateElimination() {
        return dateElimination;
    }

    //setters
    public void setId(UUID id) {
        this.id = id;
    }

    public void setUserNotificador(Username userNotificador) {
        this.userNotificador = userNotificador;
    }

    public void setUserNotificado(Username userNotificado) {
        this.userNotificado = userNotificado;
    }

    public void setHilo(Hilo hilo) {
        this.hilo = hilo;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setDateElimination(Date dateElimination) {
        this.dateElimination = dateElimination;
    }

}
