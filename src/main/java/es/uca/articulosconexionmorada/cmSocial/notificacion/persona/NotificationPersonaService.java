package es.uca.articulosconexionmorada.cmSocial.notificacion.persona;

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
public class NotificationPersonaService {

    @Autowired
    private NotificacionPersonaRepository notificacionPersonaRepository;

    public void save(NotificacionPersona notificacionPersona) throws IOException {
        if(notificacionPersona.getId() == null){
            NotificationData notificationData = new NotificationData();
            notificationData.setTitle("Conexión Morada Social");
            notificationData.setBody(notificacionPersona.getUserNotificado().getUsername() + notificacionPersona.getMensaje());
            notificationData.setRecipientToken(notificacionPersona.getUserNotificado().getFirebaseToken());
            CloudMessage.sendNotification(notificationData);
        }

        notificacionPersonaRepository.save(notificacionPersona);
    }

    public void delete(NotificacionPersona notificacionPersona){
        notificacionPersona.setDateElimination(new Date());
        notificacionPersonaRepository.save(notificacionPersona);
    }

    public List<NotificacionPersona> findByUserNotificadoAndDateEliminationIsNullOrderByDateCreation(Username user){
        return notificacionPersonaRepository.findByUserAndDateEliminationIsNullOrderByDateCreation(user);
    }

    public void deleteAllByUser(Username username){
        List<NotificacionPersona> notificacionPersonas = notificacionPersonaRepository.findByUserAndDateEliminationIsNullOrderByDateCreation(username);
        for(NotificacionPersona notificacionPersona : notificacionPersonas){
            delete(notificacionPersona);
        }
    }

    public Optional<NotificacionPersona> findById(UUID id){
        return notificacionPersonaRepository.findById(id);
    }

}
