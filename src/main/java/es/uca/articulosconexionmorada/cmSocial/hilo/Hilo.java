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
    @Column
    private UserApp autor;

    @Column
    private String mensaje;

    @Column
    private Date dateCreation;

    @Column
    private Date dateElimination;

    @Column
    @ManyToOne
    private Hilo hiloPadre;

    @Column
    private int  meGusta = 0;

    @Column
    private int  noMeGusta = 0;

    public Hilo(){}

    public Hilo(UserApp autor, String mensaje, Hilo hiloPadre) {
        this.autor = autor;
        this.mensaje = mensaje;
        this.hiloPadre = hiloPadre;
        this.dateCreation = new Date();
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

    public int getMeGusta() {
        return meGusta;
    }

    public int getNoMeGusta() {
        return noMeGusta;
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

    public void setMeGusta(int meGusta) {
        this.meGusta = meGusta;
    }

    public void setNoMeGusta(int noMeGusta) {
        this.noMeGusta = noMeGusta;
    }

}
