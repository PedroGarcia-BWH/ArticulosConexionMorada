package es.uca.articulosconexionmorada.controller;

import es.uca.articulosconexionmorada.cmSocial.hilo.Hilo;
import es.uca.articulosconexionmorada.cmSocial.userApp.UserApp;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.util.Date;

public class PayloadHilo {

    private String autor_uuid;

    private String mensaje;

    private String hiloPadre_uuid;

    private Date dateCreation;
    private Integer likes;
    private Integer dislikes;

    private Boolean liked;

    private Boolean disliked;

    public PayloadHilo(String autor_uuid, String mensaje, String hiloPadre_uuid, Integer likes, Integer dislikes, Boolean liked, Boolean disliked) {
        this.autor_uuid = autor_uuid;
        this.mensaje = mensaje;
        this.hiloPadre_uuid = hiloPadre_uuid;
        this.likes = likes;
        this.dislikes = dislikes;
        this.liked = liked;
        this.disliked = disliked;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public Boolean getDisliked() {
        return disliked;
    }

    public void setDisliked(Boolean disliked) {
        this.disliked = disliked;
    }

    public String getAutor_uuid() {
        return autor_uuid;
    }

    public void setAutor_uuid(String autor_uuid) {
        this.autor_uuid = autor_uuid;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getHiloPadre_uuid() {
        return hiloPadre_uuid;
    }

    public void setHiloPadre_uuid(String hiloPadre_uuid) {
        this.hiloPadre_uuid = hiloPadre_uuid;
    }

}
