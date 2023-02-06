package es.uca.articulosconexionmorada.cmSocial.hilo;

import es.uca.articulosconexionmorada.cmSocial.userApp.UserApp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class Hilo {

    @Id
    @GeneratedValue
    @Column(length=16)
    private UUID id;

    @ManyToOne
    private UserApp autor;

    @Column
    private String mensaje;

    @Column
    private Date dateCreation;

    @Column
    private Date dateElimination;
    
    @ManyToOne
    private Hilo hiloPadre;

    public Hilo(){}

    public Hilo(UserApp autor, String mensaje, Hilo hiloPadre) {
        this.autor = autor;
        this.mensaje = mensaje;
        this.hiloPadre = hiloPadre;
        this.dateCreation = new Date();
    }

    public Hilo(Hilo hilo) {
        this.autor = hilo.getAutor();
        this.mensaje = hilo.getMensaje();
        this.hiloPadre = hilo.getHiloPadre();
        this.dateCreation = hilo.getDateCreation();
        this.dateElimination = hilo.getDateElimination();
    }

    //getters

    public UUID getId() {
        return id;
    }

    public UserApp getAutor() {
        return autor;
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

    public Hilo getHiloPadre() {
        return hiloPadre;
    }


    //setters

    public void setAutor(UserApp autor) {
        this.autor = autor;
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

    public void setHiloPadre(Hilo hiloPadre) {
        this.hiloPadre = hiloPadre;
    }

}
