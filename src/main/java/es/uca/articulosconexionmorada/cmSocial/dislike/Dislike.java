package es.uca.articulosconexionmorada.cmSocial.dislike;

import es.uca.articulosconexionmorada.cmSocial.hilo.Hilo;
import es.uca.articulosconexionmorada.cmSocial.userApp.UserApp;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Dislike {
    @Id
    @GeneratedValue
    @Column(length=16)
    private UUID id;

    @ManyToOne
    private Hilo hilo;

    @ManyToOne
    private UserApp userApp;

    public Dislike(){}

    public Dislike(Hilo hilo, UserApp userApp) {
        this.hilo = hilo;
        this.userApp = userApp;
    }

    //getters

    public UUID getId() {
        return id;
    }

    public Hilo getHilo() {
        return hilo;
    }

    public UserApp getUserApp() {
        return userApp;
    }

    //setters

    public void setId(UUID id) {
        this.id = id;
    }

    public void setHilo(Hilo hilo) {
        this.hilo = hilo;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }

}
