package es.uca.articulosconexionmorada.cmSocial.like;

import es.uca.articulosconexionmorada.cmSocial.hilo.Hilo;
import es.uca.articulosconexionmorada.username.Username;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table (name = "likes")
public class Like {
    @Id
    @GeneratedValue
    @Column(length=16)
    private UUID id;

    @ManyToOne
    private Hilo hilo;

    @ManyToOne
    private Username userApp;


    public Like(){}

    public Like(Hilo hilo, Username userApp) {
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

    public Username getUserApp() {
        return userApp;
    }

    //setters

    public void setId(UUID id) {
        this.id = id;
    }

    public void setHilo(Hilo hilo) {
        this.hilo = hilo;
    }

    public void setUserApp(Username userApp) {
        this.userApp = userApp;
    }

}
