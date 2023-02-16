package es.uca.articulosconexionmorada.cmSocial.notificacion.persona;

import es.uca.articulosconexionmorada.cmSocial.notificacion.hilo.NotificacionHilo;
import es.uca.articulosconexionmorada.username.Username;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificacionPersonaRepository extends JpaRepository<NotificacionPersona, UUID> {

    List<NotificacionPersona> findByUserNotificadoAndDateEliminationIsNullOrderByDateCreation(Username user);



}
