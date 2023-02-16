package es.uca.articulosconexionmorada.cmSocial.notificacion.persona;

import es.uca.articulosconexionmorada.username.Username;
import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class NotificacionPersona {
    @Id
    @GeneratedValue
    @Column(length=16)
    private UUID id;

    @ManyToOne
    private Username user;

    @ManyToOne
    private Username userNotificado;

    @Column
    private String mensaje;

    @Column
    private Date dateCreation;

    @Column
    private Date dateElimination;


    public NotificacionPersona(){}

    public NotificacionPersona(Username user, Username userNotificado, String mensaje, Date dateCreation, Date dateElimination) {
        this.user = user;
        this.userNotificado = userNotificado;
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

    public Username getUserNotificado() {
        return userNotificado;
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

    public void setUserNotificado(Username userNotificado) {
        this.userNotificado = userNotificado;
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
