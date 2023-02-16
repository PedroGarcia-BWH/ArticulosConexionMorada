package es.uca.articulosconexionmorada.cmSocial.notificacion.hilo;


import es.uca.articulosconexionmorada.username.Username;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationHiloRepository extends JpaRepository<NotificacionHilo, UUID> {

    List<NotificacionHilo> findByUserAndDateEliminationIsNullOrderByDateCreation(Username user);

    Optional<NotificacionHilo> findById(UUID id);

}
