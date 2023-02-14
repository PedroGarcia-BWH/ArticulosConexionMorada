package es.uca.articulosconexionmorada.cmSocial.seguidores;

import es.uca.articulosconexionmorada.username.Username;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeguidoresService {

    @Autowired
    private SeguidoresRepository seguidoresRepository;

    public int countBySeguidores(Username seguidor){
        return seguidoresRepository.countBySeguidor(seguidor);
    }

    public int countBySeguido(Username seguido){
        return seguidoresRepository.countBySeguido(seguido);
    }

    public void save(Seguidores seguidores){
        seguidoresRepository.save(seguidores);
    }

    public void delete(Seguidores seguidores){
        seguidoresRepository.delete(seguidores);
    }

    public Optional<Seguidores> findBySeguidorAndSeguido(Username seguidor, Username seguido){
        return seguidoresRepository.findBySeguidorAndSeguido(seguidor, seguido);
    }

    public List<Seguidores> findBySeguido(Username seguidor){
        return seguidoresRepository.findBySeguido(seguidor);
    }

}
