package es.uca.articulosconexionmorada.sistemaCompanero.mensaje;


import es.uca.articulosconexionmorada.controller.payload.PayloadMensaje;
import es.uca.articulosconexionmorada.firebase.CloudMessage;
import es.uca.articulosconexionmorada.firebase.NotificationData;
import es.uca.articulosconexionmorada.sistemaCompanero.chat.Chat;
import es.uca.articulosconexionmorada.sistemaCompanero.chat.ChatService;
import es.uca.articulosconexionmorada.username.Username;
import es.uca.articulosconexionmorada.username.UsernameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private UsernameService usernameService;

    public MensajeService() {}

    public MensajeService(MensajeRepository mensajeRepository, UsernameService usernameService) {
        this.mensajeRepository = mensajeRepository;
        this.usernameService = usernameService;
    }

    public Mensaje getLastMensajeByChat(Chat chat) {
        List <Mensaje> mensajes = mensajeRepository.findByChatOrderByDateCreatedDesc(chat);
        if (mensajes.isEmpty()) return null;
        return mensajes.get(0);
    }

    public int getNumMensajesNoLeidosByChat(Chat chat, String uuid) {
        List<Mensaje> mensajes = mensajeRepository.findMensajeByChatAndLeidoIsFalse(chat);
        List<Mensaje> mensajesUser = new ArrayList<>();

        for (Mensaje mensaje : mensajes) {
            if(mensaje.getUuidEmisor() != null) {
                if (!mensaje.getUuidEmisor().equals(uuid)) {
                    mensajesUser.add(mensaje);
                }
            }

        }
        return mensajesUser.size();
    }

    public List<Mensaje> getMensajesNoLeidosByChat(Chat chat, String uuid) {
        List<Mensaje> mensajes = mensajeRepository.findMensajeByChatAndLeidoIsFalse(chat);
        List<Mensaje> mensajesUser = new ArrayList<>();

        for (Mensaje mensaje : mensajes) {
            if (!mensaje.getUuidEmisor().equals(uuid)) {
                mensajesUser.add(mensaje);
            }
        }
        return mensajesUser;
    }

    public void save(Mensaje mensaje) throws IOException {
        if(mensaje.getId() == null) {
            NotificationData notificationData = new NotificationData();
            notificationData.setTitle("Sistema Compañero");

            String uuidNotificado = mensaje.getChat().getUuidUser2();

            if(!mensaje.getChat().getUuidUser1().equals(mensaje.getUuidEmisor())){
                uuidNotificado = mensaje.getChat().getUuidUser1();
            }
            Username username = usernameService.findByFirebaseId(uuidNotificado);
            notificationData.setRecipientToken(username.getFirebaseToken());
            notificationData.setBody(username.getUsername() + " " + mensaje.getMensaje() );
            CloudMessage.sendNotification(notificationData);
        }
        mensajeRepository.save(mensaje);
    }

    public List<Mensaje> getMensajesByChat(Chat chat) {
        return mensajeRepository.findByChatOrderByDateCreated(chat);
    }
}
