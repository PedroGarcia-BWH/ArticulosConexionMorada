package es.uca.articulosconexionmorada.sistemaCompanero.chat;

import es.uca.articulosconexionmorada.sistemaCompanero.puntoCompanero.PuntoCompanero;
import org.checkerframework.checker.units.qual.C;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {

    List<Chat> findByUuidUser1OrUuidUser2(String uuidUser1, String uuidUser2);

    Optional<Chat> findById(UUID id);

    Optional<Chat> findByUuidUser1AndUuidUser2(String uuidUser1, String uuidUser2);
}
