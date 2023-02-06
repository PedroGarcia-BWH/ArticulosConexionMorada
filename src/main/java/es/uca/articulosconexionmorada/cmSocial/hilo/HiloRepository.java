package es.uca.articulosconexionmorada.cmSocial.hilo;

import es.uca.articulosconexionmorada.cmSocial.userApp.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HiloRepository extends JpaRepository<Hilo, UUID> {
    List<Hilo> findByAutorAndHiloPadreIsNullOrderByDateCreation(UserApp userApp);

    List<Hilo> findByHiloPadreIsNullOrderByDateCreation();


}

