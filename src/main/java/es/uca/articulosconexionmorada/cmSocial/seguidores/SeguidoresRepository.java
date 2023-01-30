package es.uca.articulosconexionmorada.cmSocial.seguidores;

import es.uca.articulosconexionmorada.cmSocial.userApp.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SeguidoresRepository extends JpaRepository<Seguidores, UUID> {
    int countBySeguidor(UserApp seguidor);

    int countBySeguido(UserApp seguido);

    List<Seguidores> findBySeguidor(UserApp seguidor);

    List<Seguidores> findBySeguido(UserApp seguido);
}
