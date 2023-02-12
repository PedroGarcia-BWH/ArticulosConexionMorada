package es.uca.articulosconexionmorada.cmSocial.like;

import es.uca.articulosconexionmorada.cmSocial.hilo.Hilo;
import es.uca.articulosconexionmorada.username.Username;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

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

}
