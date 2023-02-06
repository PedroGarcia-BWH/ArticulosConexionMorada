package es.uca.articulosconexionmorada.cmSocial.dislike;

import es.uca.articulosconexionmorada.cmSocial.hilo.Hilo;
import es.uca.articulosconexionmorada.cmSocial.userApp.UserApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DislikeService {

    @Autowired
    private DislikeRepository dislikeRepository;

    public void save(Dislike dislike){
        dislikeRepository.save(dislike);
    }

    public void delete(Dislike dislike){
        dislikeRepository.delete(dislike);
    }

    public int countByHilo(Hilo hilo){
        return dislikeRepository.countByHilo(hilo);
    }

    public boolean dislikeExists(Hilo hilo, UserApp userApp){
        return dislikeRepository.findByHiloAndUserApp(hilo, userApp).isPresent();
    }

    public Optional<Dislike> findByHiloAndUserApp(Hilo hilo, UserApp userApp){
        return dislikeRepository.findByHiloAndUserApp(hilo, userApp);
    }
}
