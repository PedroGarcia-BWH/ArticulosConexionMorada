package es.uca.articulosconexionmorada.cmSocial.notificacion.firebase;

import es.uca.articulosconexionmorada.cmSocial.notificacion.hilo.NotificacionHilo;
import es.uca.articulosconexionmorada.cmSocial.notificacion.hilo.NotificationHiloService;
import es.uca.articulosconexionmorada.cmSocial.notificacion.persona.NotificacionPersona;
import es.uca.articulosconexionmorada.cmSocial.notificacion.persona.NotificationPersonaService;
import es.uca.articulosconexionmorada.controller.payload.PayloadNotificationHilo;
import es.uca.articulosconexionmorada.controller.payload.PayloadNotificationPersona;
import es.uca.articulosconexionmorada.username.UsernameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class NotificationMessageController {

    @Autowired
    private NotificationMessageService notificationMessageService;

    @Autowired
    private NotificationHiloService notificationHiloService;

    @Autowired
    private NotificationPersonaService notificacionPersonaService;

    @Autowired
    private UsernameService usernameService;


    @PostMapping("/send/Notification")
    public String sendNotificationByToken(@RequestBody NotificationMessage notificationMessage){
        return notificationMessageService.sendNotificationByToken(notificationMessage);
    }


    @GetMapping("/get/Notifications/hilo/{uuid}")
    public List<PayloadNotificationHilo> getNotificationsHilo(@PathVariable String uuid){
        List<NotificacionHilo> notificacionHilos = notificationHiloService.findByUserAndDateEliminationIsNullOrderByDateCreation(usernameService.findByFirebaseId(uuid));

        List<PayloadNotificationHilo> payloadNotificationHilos = new ArrayList<PayloadNotificationHilo>();

        for(NotificacionHilo notificacionHilo : notificacionHilos){
            payloadNotificationHilos.add(new PayloadNotificationHilo(notificacionHilo.getUser().getUsername(), notificacionHilo.getHilo().getId().toString(),
                    notificacionHilo.getHilo().getAutor().getFirebaseId(), notificacionHilo.getMensaje(), notificacionHilo.getHilo().getHiloPadre().getId().toString(),
                    notificacionHilo.getMensaje(), notificacionHilo.getDateCreation().toString()));
        }


        return payloadNotificationHilos;
    }


    @GetMapping("/get/Notifications/persona/{uuid}")
    public List<PayloadNotificationPersona> getNotificationsPersona(@PathVariable String uuid){
        List<NotificacionPersona> notificacionPersonas = notificacionPersonaService.findByUserNotificadoAndDateEliminationIsNullOrderByDateCreation(usernameService.findByFirebaseId(uuid));

        List<PayloadNotificationPersona> payloadNotificationPersonas = new ArrayList<PayloadNotificationPersona>();

        for(NotificacionPersona notificacionPersona : notificacionPersonas){
            payloadNotificationPersonas.add(new PayloadNotificationPersona(notificacionPersona.getUser().getFirebaseId(), notificacionPersona.getMensaje(),
                    notificacionPersona.getDateCreation().toString()));
        }
        return payloadNotificationPersonas;
    }
}
