package es.uca.articulosconexionmorada.cmSocial.like;

import es.uca.articulosconexionmorada.cmSocial.hilo.Hilo;
import es.uca.articulosconexionmorada.username.Username;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<Like, UUID> {

    long countByHilo(Hilo hilo);

    Optional<Like> findByHiloAndUserApp(Hilo hilo, Username userApp);


}
