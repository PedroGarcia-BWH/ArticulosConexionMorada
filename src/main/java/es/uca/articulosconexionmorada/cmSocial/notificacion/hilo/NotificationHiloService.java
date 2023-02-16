package es.uca.articulosconexionmorada.cmSocial.notificacion.hilo;

import es.uca.articulosconexionmorada.username.Username;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationHiloService {

    @Autowired
    private NotificationHiloRepository notificationHiloRepository;

    public void save(NotificacionHilo notificacionHilo){
        notificationHiloRepository.save(notificacionHilo);
    }

    public void delete(NotificacionHilo notificacionHilo){
        notificacionHilo.setDateElimination(new Date());
        notificationHiloRepository.save(notificacionHilo);
    }

    public List<NotificacionHilo> findByUserAndDateEliminationIsNullOrderByDateCreation(Username user){
        return notificationHiloRepository.findByUserAndDateEliminationIsNullOrderByDateCreation(user);
    }

    public Optional<NotificacionHilo> findById(UUID id){
        return notificationHiloRepository.findById(id);
    }

    public void deleteAllByUser(Username username){
        List<NotificacionHilo> notificacionHilos = notificationHiloRepository.findByUserAndDateEliminationIsNullOrderByDateCreation(username);
        for(NotificacionHilo notificacionHilo : notificacionHilos){
            delete(notificacionHilo);
        }
    }

}
