package es.uca.articulosconexionmorada.cmSocial.seguidores;

import es.uca.articulosconexionmorada.username.Username;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
