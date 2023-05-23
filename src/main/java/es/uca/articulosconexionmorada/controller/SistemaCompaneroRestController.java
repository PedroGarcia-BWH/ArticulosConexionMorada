package es.uca.articulosconexionmorada.controller;

import es.uca.articulosconexionmorada.controller.payload.PayloadChat;
import es.uca.articulosconexionmorada.controller.payload.PayloadMensaje;
import es.uca.articulosconexionmorada.controller.payload.PayloadPuntoCompanero;
import es.uca.articulosconexionmorada.sistemaCompanero.chat.Chat;
import es.uca.articulosconexionmorada.sistemaCompanero.chat.ChatService;
import es.uca.articulosconexionmorada.sistemaCompanero.marker.Marker;
import es.uca.articulosconexionmorada.sistemaCompanero.marker.MarkerService;
import es.uca.articulosconexionmorada.sistemaCompanero.mensaje.Mensaje;
import es.uca.articulosconexionmorada.sistemaCompanero.mensaje.MensajeService;
import es.uca.articulosconexionmorada.sistemaCompanero.puntoCompanero.PuntoCompanero;
import es.uca.articulosconexionmorada.sistemaCompanero.puntoCompanero.PuntoCompaneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class SistemaCompaneroRestController {

    @Autowired
    private PuntoCompaneroService puntoCompaneroService;

    @Autowired
    private MarkerService markerService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private MensajeService mensajeService;

    public SistemaCompaneroRestController(PuntoCompaneroService puntoCompaneroService, MarkerService markerService, ChatService chatService, MensajeService mensajeService) {
        this.puntoCompaneroService = puntoCompaneroService;
        this.markerService = markerService;
        this.chatService = chatService;
        this.mensajeService = mensajeService;
    }


    @GetMapping("/puntoCompanero/all")
    public List<PayloadPuntoCompanero> puntoCompaneroAll() {
        return puntoCompaneroService.allPuntoCompañeroActive();
    }

    @PostMapping("/puntoCompanero/add")
    public void puntoCompaneroAdd(@RequestBody PayloadPuntoCompanero payloadPuntoCompanero) {
        Marker origen = new Marker(payloadPuntoCompanero.getMarkerOrigenLatitud(), payloadPuntoCompanero.getMarkerOrigenLongitud(),
                payloadPuntoCompanero.getMarkerOrigenTitulo());

        Marker destino = new Marker(payloadPuntoCompanero.getMarkerDestinoLatitud(), payloadPuntoCompanero.getMarkerDestinoLongitud(),
                payloadPuntoCompanero.getMarkerDestinoTitulo());
        markerService.save(origen);
        markerService.save(destino);

        //print payloadPuntoCompanero atributes
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date dateEvento = null;
        try {
            dateEvento = dateFormat.parse(payloadPuntoCompanero.getDateEvento());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        puntoCompaneroService.save(new PuntoCompanero(payloadPuntoCompanero.getUuidSolicitante(), payloadPuntoCompanero.getUuidAceptante(),
                origen, destino, dateEvento ));
    }

    @PutMapping("/puntoCompanero/accept/{id}/{uuid}")
    public Boolean puntoCompaneroAccept(@PathVariable String id, @PathVariable String uuid) {
        Optional<PuntoCompanero> puntoCompanero = puntoCompaneroService.findById(id);
        if (puntoCompanero.isPresent()) {
            if(puntoCompanero.get().getUuidAceptante() == null && puntoCompanero.get().getDateEliminated() == null) {
                puntoCompanero.get().setUuidAceptante(uuid);
                puntoCompaneroService.save(puntoCompanero.get());
                Optional<Chat> chat1 = chatService.findByUuidUser1AndUuidUser2(puntoCompanero.get().getUuidSolicitante(), puntoCompanero.get().getUuidAceptante());
                Optional<Chat> chat2 = chatService.findByUuidUser1AndUuidUser2(puntoCompanero.get().getUuidAceptante(), puntoCompanero.get().getUuidSolicitante());

                if(!chat1.isPresent() && !chat2.isPresent()) {
                   Chat chat = new Chat(puntoCompanero.get().getUuidSolicitante(), puntoCompanero.get().getUuidAceptante());
                   chat = chatService.save(chat);

                   Mensaje mensaje = new Mensaje(chat, "¡Hola! He aceptado tu solicitud del Punto Compañero para el día " + puntoCompanero.get().getDateCreated(), uuid, new Date());
                   mensajeService.save(mensaje);
                }else if(chat1.isPresent()){
                    Mensaje mensaje = new Mensaje(chat1.get(), "¡Hola! He aceptado tu solicitud del Punto Compañero para el día " + puntoCompanero.get().getDateCreated(), uuid, new Date());
                    mensajeService.save(mensaje);
                }else {
                    Mensaje mensaje = new Mensaje(chat2.get(), "¡Hola! He aceptado tu solicitud del Punto Compañero para el día " + puntoCompanero.get().getDateCreated(), uuid, new Date());
                    mensajeService.save(mensaje);
                }
                return true;
            }
        }
        return false;
    }

    @GetMapping("/puntoCompanero/{id}")
    public PayloadPuntoCompanero puntoCompanero(@PathVariable String id) {
        Optional<PuntoCompanero> puntoCompanero = puntoCompaneroService.findById(id);
        if (puntoCompanero.isPresent()) {
            PayloadPuntoCompanero payloadPuntoCompanero = new PayloadPuntoCompanero(
                    id,
                    puntoCompanero.get().getUuidSolicitante(),
                    puntoCompanero.get().getUuidAceptante(),
                    puntoCompanero.get().getMarkerOrigen().getLatitud(),
                    puntoCompanero.get().getMarkerOrigen().getLongitud(),
                    puntoCompanero.get().getMarkerOrigen().getTitulo(),
                    puntoCompanero.get().getMarkerDestino().getLatitud(),
                    puntoCompanero.get().getMarkerDestino().getLongitud(),
                    puntoCompanero.get().getMarkerDestino().getTitulo(),
                    puntoCompanero.get().getDateCreated().toString(),
                    puntoCompanero.get().getDateEliminated() != null ? puntoCompanero.get().getDateEliminated().toString() : null,
                    puntoCompanero.get().getDateEvento().toString()
            );
            return payloadPuntoCompanero;
        }
        return null;
    }

    @GetMapping("/puntoCompanero/uuid/{uuid}")
    public List<PayloadPuntoCompanero> puntoCompaneroUuid(@PathVariable String uuid) {
        List<PuntoCompanero> puntoCompanero = puntoCompaneroService.findByUuid(uuid);
        List<PayloadPuntoCompanero> payloadPuntoCompanero = new ArrayList<>();
        for (PuntoCompanero punto : puntoCompanero) {
            payloadPuntoCompanero.add(new PayloadPuntoCompanero(
                    punto.getId().toString(),
                    punto.getUuidSolicitante(),
                    punto.getUuidAceptante(),
                    punto.getMarkerOrigen().getLatitud(),
                    punto.getMarkerOrigen().getLongitud(),
                    punto.getMarkerOrigen().getTitulo(),
                    punto.getMarkerDestino().getLatitud(),
                    punto.getMarkerDestino().getLongitud(),
                    punto.getMarkerDestino().getTitulo(),
                    punto.getDateCreated().toString(),
                    punto.getDateEliminated() != null ? punto.getDateEliminated().toString() : null,
                    punto.getDateEvento().toString()
            ));
        }
        return payloadPuntoCompanero;
    }

    @GetMapping("/chats/{id}") //Obtenemos todos los chats de un usuario
    public List<PayloadChat> chats(@PathVariable String id) {
        List<Chat> chats = chatService.findAllChatsByUserId(id);
        List<PayloadChat> payloadChats = new ArrayList<>();
        for (Chat chat : chats) {
            Mensaje mensaje = mensajeService.getLastMensajeByChat(chat);
            String uuidUser = chat.getUuidUser1();
            if (mensaje != null) {
                if(chat.getUuidUser1().equals(id))  uuidUser = chat.getUuidUser2();
                payloadChats.add(new PayloadChat(
                        chat.getId().toString(),
                        mensaje.getMensaje(),
                        mensaje.getDateCreated().toString(),
                        uuidUser,
                        String.valueOf(mensajeService.getNumMensajesNoLeidosByChat(chat, id))

                ));
            }
        }
        return payloadChats;
    }


    @GetMapping("/chat/{id}")
    public List<PayloadMensaje> getMensajesChat(@PathVariable String id) {
        List<Mensaje> mensajes = mensajeService.getMensajesByChat(chatService.findById(UUID.fromString(id)).get());
        List<PayloadMensaje> payloadMensajes = new ArrayList<>();
           for (Mensaje mensaje : mensajes) {
                payloadMensajes.add(new PayloadMensaje(
                        mensaje.getId().toString(),
                        mensaje.getMensaje(),
                        mensaje.getUuidEmisor(),
                        mensaje.getDateCreated().toString(),
                        mensaje.getDateEliminated() != null ? mensaje.getDateEliminated().toString() : null
                ));
            }
        return payloadMensajes;
    }

    @PutMapping("/check/mensajes/chat/{id}/user/{uuid}")
    public void checkMensajesChat(@PathVariable String id, @PathVariable String uuid) {
        List<Mensaje> mensajes = mensajeService.getMensajesNoLeidosByChat(chatService.findById(UUID.fromString(id)).get(), uuid);
        System.out.println(mensajes.size());
        for (Mensaje mensaje : mensajes) {
            if(!mensaje.getUuidEmisor().equals(id)) {
                mensaje.setLeido(true);
                mensajeService.save(mensaje);
            }
        }
    }

    @PostMapping("/mensaje")
    public boolean mensaje(@RequestBody PayloadMensaje payloadMensaje) {
        Optional<Chat> chat = chatService.findById(UUID.fromString(payloadMensaje.getId()));
        if (chat.isPresent()) {
            Mensaje mensaje = new Mensaje(chat.get(), payloadMensaje.getMensaje(), payloadMensaje.getUuidEmisor(), new Date());
            mensajeService.save(mensaje);
            return true;
        }
        return false;
    }



}
