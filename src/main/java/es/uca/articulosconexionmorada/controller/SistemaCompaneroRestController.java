package es.uca.articulosconexionmorada.controller;

import es.uca.articulosconexionmorada.controller.payload.PayloadPuntoCompanero;
import es.uca.articulosconexionmorada.sistemaCompanero.chat.ChatService;
import es.uca.articulosconexionmorada.sistemaCompanero.marker.Marker;
import es.uca.articulosconexionmorada.sistemaCompanero.marker.MarkerService;
import es.uca.articulosconexionmorada.sistemaCompanero.mensaje.MensajeService;
import es.uca.articulosconexionmorada.sistemaCompanero.puntoCompanero.PuntoCompanero;
import es.uca.articulosconexionmorada.sistemaCompanero.puntoCompanero.PuntoCompaneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        Marker origen = new Marker(payloadPuntoCompanero.getMarkerOrigenLatitud(), payloadPuntoCompanero.getMarkerDestinoLatitud(),
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
}
