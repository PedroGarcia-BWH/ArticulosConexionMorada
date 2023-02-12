package es.uca.articulosconexionmorada.cmSocial.dislike;


import es.uca.articulosconexionmorada.cmSocial.hilo.Hilo;
import es.uca.articulosconexionmorada.cmSocial.like.Like;
import es.uca.articulosconexionmorada.username.Username;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DislikeRepository extends JpaRepository<Dislike, UUID> {

    long countByHilo(Hilo hilo);

    Optional<Dislike> findByHiloAndUserApp(Hilo hilo, Username userApp);

}
