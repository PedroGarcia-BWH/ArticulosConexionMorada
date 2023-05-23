package es.uca.articulosconexionmorada.cmSocial.notificacion.hilo;


import es.uca.articulosconexionmorada.firebase.CloudMessage;
import es.uca.articulosconexionmorada.firebase.NotificationData;
import es.uca.articulosconexionmorada.username.Username;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationHiloService {

    @Autowired
    private NotificationHiloRepository notificationHiloRepository;


    public void save(NotificacionHilo notificacionHilo) throws IOException {
        //Send notification push
        if(notificacionHilo.getId() == null){
            NotificationData notificationData = new NotificationData();
            notificationData.setTitle("Conexión Morada Social");
            notificationData.setBody(notificacionHilo.getUserNotificado().getUsername() + notificacionHilo.getMensaje());
            notificationData.setRecipientToken(notificacionHilo.getUserNotificado().getFirebaseToken());
            CloudMessage.sendNotification(notificationData);
        }
        notificationHiloRepository.save(notificacionHilo);
    }

    public void saveAndSendNotification(NotificacionHilo notificacionHilo){
        notificationHiloRepository.save(notificacionHilo);
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
