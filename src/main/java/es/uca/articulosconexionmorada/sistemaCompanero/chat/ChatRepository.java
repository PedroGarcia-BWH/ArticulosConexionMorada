package es.uca.articulosconexionmorada.sistemaCompanero.chat;

import es.uca.articulosconexionmorada.sistemaCompanero.puntoCompanero.PuntoCompanero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {
}
