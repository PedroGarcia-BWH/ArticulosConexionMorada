package es.uca.articulosconexionmorada.sistemaCompanero.mensaje;

import es.uca.articulosconexionmorada.sistemaCompanero.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, UUID> {
}
