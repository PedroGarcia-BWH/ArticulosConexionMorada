package es.uca.articulosconexionmorada.sistemaCompanero.mensaje;


import es.uca.articulosconexionmorada.controller.payload.PayloadMensaje;
import es.uca.articulosconexionmorada.sistemaCompanero.chat.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    public MensajeService() {}

    public MensajeService(MensajeRepository mensajeRepository) {
        this.mensajeRepository = mensajeRepository;
    }

    public Mensaje getLastMensajeByChat(Chat chat) {
        List <Mensaje> mensajes = mensajeRepository.findByChatOrderByDateCreatedDesc(chat);
        if (mensajes.isEmpty()) return null;
        return mensajes.get(0);
    }

    public int getNumMensajesNoLeidosByChat(Chat chat, String uuid) {
        return mensajeRepository.findByChatAndUuidEmisorAndLeidoIsFalse(chat, uuid).size();
    }

    public List<Mensaje> getMensajesNoLeidosByChat(Chat chat, String uuid) {
        return mensajeRepository.findByChatAndUuidEmisorAndLeidoIsFalse(chat, uuid);
    }

    public void save(Mensaje mensaje) {
        mensajeRepository.save(mensaje);
    }

    public List<Mensaje> getMensajesByChat(Chat chat) {
        return mensajeRepository.findByChatOrderByDateCreated(chat);
    }
}
