package es.uca.articulosconexionmorada.cmSocial.notificacion.persona;

import es.uca.articulosconexionmorada.username.Username;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationPersonaService {

    @Autowired
    private NotificacionPersonaRepository notificacionPersonaRepository;

    public void save(NotificacionPersona notificacionPersona){
        notificacionPersonaRepository.save(notificacionPersona);
    }

    public void delete(NotificacionPersona notificacionPersona){
        notificacionPersona.setDateElimination(new Date());
        notificacionPersonaRepository.save(notificacionPersona);
    }

    public List<NotificacionPersona> findByUserNotificadoAndDateEliminationIsNullOrderByDateCreation(Username user){
        return notificacionPersonaRepository.findByUserNotificadoAndDateEliminationIsNullOrderByDateCreation(user);
    }

    public void deleteAllByUser(Username username){
        List<NotificacionPersona> notificacionPersonas = notificacionPersonaRepository.findByUserNotificadoAndDateEliminationIsNullOrderByDateCreation(username);
        for(NotificacionPersona notificacionPersona : notificacionPersonas){
            delete(notificacionPersona);
        }
    }

    public Optional<NotificacionPersona> findById(UUID id){
        return notificacionPersonaRepository.findById(id);
    }

}
