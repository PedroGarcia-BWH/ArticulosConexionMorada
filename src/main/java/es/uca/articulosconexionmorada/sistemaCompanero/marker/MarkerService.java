package es.uca.articulosconexionmorada.sistemaCompanero.marker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarkerService {

    @Autowired
    private MarkerRepository markerRepository;

    MarkerService() {}

    MarkerService(MarkerRepository markerRepository) {
        this.markerRepository = markerRepository;
    }

    public Marker save(Marker marker) {
        return markerRepository.save(marker);
    }


}
