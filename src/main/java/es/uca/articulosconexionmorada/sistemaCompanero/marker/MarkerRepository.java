package es.uca.articulosconexionmorada.sistemaCompanero.marker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MarkerRepository extends JpaRepository<Marker, UUID> {
}
