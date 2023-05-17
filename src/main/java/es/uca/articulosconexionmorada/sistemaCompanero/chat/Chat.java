package es.uca.articulosconexionmorada.sistemaCompanero.chat;

import es.uca.articulosconexionmorada.sistemaCompanero.puntoCompanero.PuntoCompanero;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Chat {
    @Id
    @GeneratedValue
    @Column(length=16)
    private UUID id;

    @OneToMany
    private List<PuntoCompanero> puntoCompanero;

}
