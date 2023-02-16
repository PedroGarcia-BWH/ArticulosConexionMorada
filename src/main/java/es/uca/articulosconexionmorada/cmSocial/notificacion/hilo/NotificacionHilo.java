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
    private Username user;

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

    public NotificacionHilo(Username user, Hilo hilo, String mensaje, Date dateCreation, Date dateElimination) {
        this.user = user;
        this.hilo = hilo;
        this.mensaje = mensaje;
        this.dateCreation = dateCreation;
        this.dateElimination = dateElimination;
    }

    //getters
    public UUID getId() {
        return id;
    }

    public Username getUser() {
        return user;
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

    public void setUser(Username user) {
        this.user = user;
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
