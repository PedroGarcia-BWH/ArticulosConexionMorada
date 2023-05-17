package es.uca.articulosconexionmorada.sistemaCompanero.puntoCompanero;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PuntoCompaneroRepository extends JpaRepository<PuntoCompanero, UUID> {

    List<PuntoCompanero> findByDateEliminatedIsNullAndUuidAceptanteIsNull();

}
