package es.uca.articulosconexionmorada.sistemaCompanero.mensaje;

import es.uca.articulosconexionmorada.sistemaCompanero.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, UUID> {
    List<Mensaje> findByChatOrderByDateCreatedDesc(Chat chat);

    List<Mensaje> findMensajeByChatAndLeidoIsFalse(Chat chat);

    List<Mensaje> findByChatOrderByDateCreated(Chat chat);
}
