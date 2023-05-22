package es.uca.articulosconexionmorada.sistemaCompanero.puntoCompanero;

import es.uca.articulosconexionmorada.controller.payload.PayloadPuntoCompanero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PuntoCompaneroService {

    @Autowired
    private PuntoCompaneroRepository puntoCompaneroRepository;

    public List<PayloadPuntoCompanero> allPuntoCompañeroActive() {
        List<PuntoCompanero> puntos = puntoCompaneroRepository.findByDateEliminatedIsNullAndUuidAceptanteIsNull();
        List<PayloadPuntoCompanero> payloadPuntoCompaneros = new ArrayList<>();
        for (PuntoCompanero punto : puntos) {
            //comprobar que una fecha no sea anterior a la actual
            if (punto.getDateEvento().before(new Date())) {
                delete(punto);
            }else{
                //si no es anterior a la actual, se añade a la lista
                PayloadPuntoCompanero payloadPuntoCompanero = new PayloadPuntoCompanero(
                        punto.getId().toString(),
                        punto.getUuidSolicitante(),
                        null,
                        punto.getMarkerOrigen().getLatitud(),
                        punto.getMarkerOrigen().getLongitud(),
                        punto.getMarkerOrigen().getTitulo(),
                        punto.getMarkerDestino().getLatitud(),
                        punto.getMarkerDestino().getLongitud(),
                        punto.getMarkerDestino().getTitulo(),
                        punto.getDateCreated().toString(),
                        null,
                        punto.getDateEvento().toString()
                );
                payloadPuntoCompaneros.add(payloadPuntoCompanero);
            }
        }
        return payloadPuntoCompaneros;
    }

    public void delete(PuntoCompanero puntoCompanero) {
        puntoCompanero.setDateEliminated(new Date());
        puntoCompaneroRepository.save(puntoCompanero);
    }

    public void save(PuntoCompanero puntoCompanero) {
        puntoCompaneroRepository.save(puntoCompanero);
    }

    public Optional<PuntoCompanero> findById(String id) {
        return puntoCompaneroRepository.findById(UUID.fromString(id));
    }

    public List<PuntoCompanero> findByUuid(String uuid) {
        return puntoCompaneroRepository.findByUuidSolicitanteOrUuidAceptante(uuid,uuid);
    }

}
