package es.uca.articulosconexionmorada.cmSocial.notificacion.hilo;

import es.uca.articulosconexionmorada.cmSocial.notificacion.firebase.NotificationMessage;
import es.uca.articulosconexionmorada.cmSocial.notificacion.firebase.NotificationMessageService;
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

    @Autowired
    private NotificationMessageService notificationMessageService;

    public void save(NotificacionHilo notificacionHilo){
        notificationHiloRepository.save(notificacionHilo);
    }

    public void saveAndSendNotification(NotificacionHilo notificacionHilo){
        notificationHiloRepository.save(notificacionHilo);
        NotificationMessage notificationMessage = new NotificationMessage();
        notificationMessageService.sendNotificationByToken(notificationMessage);
    }

    public void delete(NotificacionHilo notificacionHilo){
        notificacionHilo.setDateElimination(new Date());
        notificationHiloRepository.save(notificacionHilo);
    }

    public List<NotificacionHilo> findByUserAndDateEliminationIsNullOrderByDateCreation(Username user){
        return notificationHiloRepository.findByUserNotificadorAndDateEliminationIsNullOrderByDateCreation(user);
    }

    public Optional<NotificacionHilo> findById(UUID id){
        return notificationHiloRepository.findById(id);
    }

    public void deleteAllByUser(Username username){
        List<NotificacionHilo> notificacionHilos = notificationHiloRepository.findByUserNotificadorAndDateEliminationIsNullOrderByDateCreation(username);
        for(NotificacionHilo notificacionHilo : notificacionHilos){
            delete(notificacionHilo);
        }
    }

}
