package es.uca.articulosconexionmorada.cmSocial.seguidores;

import es.uca.articulosconexionmorada.username.Username;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class  Seguidores {

    @Id
    @GeneratedValue
    @Column(length=16)
    private UUID id;

    @ManyToOne
    private Username seguidor;

    @ManyToOne
    private Username seguido;

    public Seguidores(){}

    public Seguidores(Username seguidor, Username seguido) {
        this.seguidor = seguidor;
        this.seguido = seguido;
    }

    //getters
    public UUID getId() {
        return id;
    }
    public Username getSeguidor() {
        return seguidor;
    }
    public Username getSeguido() {
        return seguido;
    }

    //setters
    public void setSeguidor(Username seguidor) {
        this.seguidor = seguidor;
    }
    public void setSeguido(Username seguido) {
        this.seguido = seguido;
    }
}
