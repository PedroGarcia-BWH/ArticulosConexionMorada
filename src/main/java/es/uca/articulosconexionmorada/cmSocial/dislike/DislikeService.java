package es.uca.articulosconexionmorada.cmSocial.dislike;

import es.uca.articulosconexionmorada.cmSocial.hilo.Hilo;
import es.uca.articulosconexionmorada.cmSocial.hilo.HiloService;
import es.uca.articulosconexionmorada.username.Username;
import es.uca.articulosconexionmorada.username.UsernameService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DislikeService {

    @Autowired
    private DislikeRepository dislikeRepository;

    @Autowired
    private HiloService hiloService;

    @Autowired
    private UsernameService usernameService;

    public void save(Dislike dislike){
        dislikeRepository.save(dislike);
    }

    public void delete(Dislike dislike){
        dislikeRepository.delete(dislike);
    }

    public long countByHilo(Hilo hilo){
        return dislikeRepository.countByHilo(hilo);
    }

    public boolean dislikeExists(Hilo hilo, Username userApp){
        return dislikeRepository.findByHiloAndUserApp(hilo, userApp).isPresent();
    }

    public Optional<Dislike> findByHiloAndUserApp(Hilo hilo, Username userApp){
        return dislikeRepository.findByHiloAndUserApp(hilo, userApp);
    }

    public Boolean getDislike(String idHilo, String uuid){
        Optional<Dislike> dislike = findByHiloAndUserApp(hiloService.findById(UUID.fromString(idHilo)).get(), usernameService.findByFirebaseId(uuid));
        return dislike.isPresent();
    }
}
