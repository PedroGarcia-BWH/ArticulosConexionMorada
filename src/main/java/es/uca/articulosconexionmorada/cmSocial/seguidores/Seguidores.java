package es.uca.articulosconexionmorada.cmSocial.seguidores;

import es.uca.articulosconexionmorada.cmSocial.userApp.UserApp;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Seguidores {

    @Id
    @GeneratedValue
    @Column(length=16)
    private UUID id;

    @ManyToOne
    private UserApp seguidor;

    @ManyToOne
    private UserApp seguido;

    public Seguidores(){}

    public Seguidores(UserApp seguidor, UserApp seguido) {
        this.seguidor = seguidor;
        this.seguido = seguido;
    }

    //getters
    public UUID getId() {
        return id;
    }
    public UserApp getSeguidor() {
        return seguidor;
    }
    public UserApp getSeguido() {
        return seguido;
    }

    //setters
    public void setSeguidor(UserApp seguidor) {
        this.seguidor = seguidor;
    }
    public void setSeguido(UserApp seguido) {
        this.seguido = seguido;
    }
}
