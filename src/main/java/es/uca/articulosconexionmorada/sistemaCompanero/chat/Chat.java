package es.uca.articulosconexionmorada.sistemaCompanero.chat;

import es.uca.articulosconexionmorada.sistemaCompanero.puntoCompanero.PuntoCompanero;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Chat {
    @Id
    @GeneratedValue
    @Column(length=16)
    private UUID id;

    @Column
    private String uuidUser1;

    @Column
    private String uuidUser2;

    @Column
    private Date dateCreated;

    @Column
    private Date dateEliminated;

    public Chat(){}

    public Chat(String uuidUser1, String uuidUser2) {
        this.uuidUser1 = uuidUser1;
        this.uuidUser2 = uuidUser2;
        this.dateCreated = new Date();
    }

    public UUID getId() {
        return id;
    }

    public String getUuidUser1() {
        return uuidUser1;
    }

    public String getUuidUser2() {
        return uuidUser2;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getDateEliminated() {
        return dateEliminated;
    }

    public void setUuidUser1(String uuidUser1) {
        this.uuidUser1 = uuidUser1;
    }

    public void setUuidUser2(String uuidUser2) {
        this.uuidUser2 = uuidUser2;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateEliminated(Date dateEliminated) {
        this.dateEliminated = dateEliminated;
    }

}
