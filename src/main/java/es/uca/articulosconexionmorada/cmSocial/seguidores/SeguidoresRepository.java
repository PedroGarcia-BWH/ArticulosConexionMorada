package es.uca.articulosconexionmorada.cmSocial.seguidores;


import es.uca.articulosconexionmorada.username.Username;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SeguidoresRepository extends JpaRepository<Seguidores, UUID> {
    int countBySeguidor(Username seguidor);

    int countBySeguido(Username seguido);

    List<Seguidores> findBySeguidor(Username seguidor);

    List<Seguidores> findBySeguido(Username seguido);
}

