package es.uca.articulosconexionmorada.sistemaCompanero.mensaje;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    public MensajeService() {}

    public MensajeService(MensajeRepository mensajeRepository) {
        this.mensajeRepository = mensajeRepository;
    }
}
