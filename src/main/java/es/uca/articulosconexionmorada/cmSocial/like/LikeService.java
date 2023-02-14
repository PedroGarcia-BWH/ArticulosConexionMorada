package es.uca.articulosconexionmorada.cmSocial.like;

import es.uca.articulosconexionmorada.cmSocial.hilo.Hilo;
import es.uca.articulosconexionmorada.cmSocial.hilo.HiloService;
import es.uca.articulosconexionmorada.username.Username;
import es.uca.articulosconexionmorada.username.UsernameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UsernameService usernameService;

    @Autowired
    private HiloService hiloService;

    public void save(Like like){
        likeRepository.save(like);
    }

    public void delete(Like like){
        likeRepository.delete(like);
    }

    public long countByHilo(Hilo hilo){
        return likeRepository.countByHilo(hilo);
    }

    public boolean likeExists(Hilo hilo, Username userApp){
        return likeRepository.findByHiloAndUserApp(hilo, userApp).isPresent();
    }

    public Optional<Like> findByHiloAndUserApp(Hilo hilo, Username userApp){
        return likeRepository.findByHiloAndUserApp(hilo, userApp);
    }

    public Boolean getLike(String idHilo, String uuid){
        Optional<Like> like = findByHiloAndUserApp(hiloService.findById(UUID.fromString(idHilo)).get(), usernameService.findByFirebaseId(uuid));
        return like.isPresent();
    }

}
