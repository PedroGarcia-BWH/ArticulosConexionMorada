package es.uca.articulosconexionmorada.controller;

import es.uca.articulosconexionmorada.cmSocial.notificacion.hilo.NotificacionHilo;
import es.uca.articulosconexionmorada.cmSocial.notificacion.hilo.NotificationHiloRepository;
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
    private NotificationHiloService notificationHiloService;

    @Autowired
    private NotificationPersonaService notificacionPersonaService;

    @Autowired
    private UsernameService usernameService;
    @Autowired
    private NotificationHiloRepository notificationHiloRepository;


    @GetMapping("/get/Notifications/hilo/{uuid}")
    public List<PayloadNotificationHilo> getNotificationsHilo(@PathVariable String uuid){
        List<NotificacionHilo> notificacionHilos = notificationHiloService.findByUserAndDateEliminationIsNullOrderByDateCreation(usernameService.findByFirebaseId(uuid));

        List<PayloadNotificationHilo> payloadNotificationHilos = new ArrayList<PayloadNotificationHilo>();

        String hiloPadre = null;

        for(NotificacionHilo notificacionHilo : notificacionHilos){
            if (notificacionHilo.getHilo().getHiloPadre() == null) {
                hiloPadre = null;
            }else{
                hiloPadre = notificacionHilo.getHilo().getHiloPadre().getId().toString();
            }
            payloadNotificationHilos.add(new PayloadNotificationHilo(notificacionHilo.getUserNotificado().getUsername(), notificacionHilo.getHilo().getId().toString(),
                    notificacionHilo.getHilo().getAutor().getFirebaseId(), notificacionHilo.getHilo().getMensaje(), hiloPadre,
                    notificacionHilo.getMensaje(), notificacionHilo.getDateCreation().toString()));
        }

        System.out.println("payloadNotificationHilos: " + payloadNotificationHilos.get(0).getUsername());

        return payloadNotificationHilos;
    }


    @GetMapping("/get/Notifications/persona/{uuid}")
    public List<PayloadNotificationPersona> getNotificationsPersona(@PathVariable String uuid){
        List<NotificacionPersona> notificacionPersonas = notificacionPersonaService.findByUserNotificadoAndDateEliminationIsNullOrderByDateCreation(usernameService.findByFirebaseId(uuid));

        List<PayloadNotificationPersona> payloadNotificationPersonas = new ArrayList<PayloadNotificationPersona>();

        for(NotificacionPersona notificacionPersona : notificacionPersonas){
            payloadNotificationPersonas.add(new PayloadNotificationPersona(notificacionPersona.getUserNotificado().getFirebaseId(), notificacionPersona.getMensaje(),
                    notificacionPersona.getDateCreation().toString()));
        }
        return payloadNotificationPersonas;
    }

    @DeleteMapping("/delete/Notifications/{uuid}")
    public void deleteNotifications(@PathVariable String uuid){
        List<NotificacionHilo> notificacionHilos = notificationHiloService.findByUserAndDateEliminationIsNullOrderByDateCreation(usernameService.findByFirebaseId(uuid));
        List<NotificacionPersona> notificacionPersonas = notificacionPersonaService.findByUserNotificadoAndDateEliminationIsNullOrderByDateCreation(usernameService.findByFirebaseId(uuid));

        for(NotificacionHilo notificacionHilo : notificacionHilos){
            notificationHiloService.delete(notificacionHilo);
        }

        for(NotificacionPersona notificacionPersona : notificacionPersonas){
            notificacionPersonaService.delete(notificacionPersona);
        }
    }
}
