package es.uca.articulosconexionmorada.sistemaCompanero.mensaje;

import es.uca.articulosconexionmorada.sistemaCompanero.chat.Chat;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.UUID;

@Entity
public class Mensaje {
    @Id
    @GeneratedValue
    @Column(length=16)
    private UUID id;

    @ManyToOne
    private Chat chat;

    private String mensaje;

    private String uuidEmisor;

    private Date dateCreated;

    private String dateEliminated;

    private Boolean leido;

    public Mensaje(){}

    public Mensaje(Chat chat, String mensaje, String uuidEmisor, Date dateCreated) {
        this.chat = chat;
        this.mensaje = mensaje;
        this.uuidEmisor = uuidEmisor;
        this.dateCreated = dateCreated;
        this.dateEliminated = null;
        this.leido = false;
    }

    public UUID getId() {
        return id;
    }

    public Chat getChat() {
        return chat;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getUuidEmisor() {
        return uuidEmisor;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public String getDateEliminated() {
        return dateEliminated;
    }

    public void setDateEliminated(String dateEliminated) {
        this.dateEliminated = dateEliminated;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setUuidEmisor(String uuidEmisor) {
        this.uuidEmisor = uuidEmisor;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Boolean getLeido() {
    	return leido;
    }

    public void setLeido(Boolean leido) {
    	this.leido = leido;
    }

}
