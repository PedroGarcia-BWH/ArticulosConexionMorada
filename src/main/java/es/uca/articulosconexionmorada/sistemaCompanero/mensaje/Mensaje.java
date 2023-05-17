package es.uca.articulosconexionmorada.sistemaCompanero.mensaje;

import es.uca.articulosconexionmorada.sistemaCompanero.chat.Chat;

import javax.persistence.*;
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

    private String dateCreated;

    private String dateEliminated;

    public Mensaje(){}

    public Mensaje(Chat chat, String mensaje, String uuidEmisor, String dateCreated) {
        this.chat = chat;
        this.mensaje = mensaje;
        this.uuidEmisor = uuidEmisor;
        this.dateCreated = dateCreated;
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

    public String getDateCreated() {
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

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}
