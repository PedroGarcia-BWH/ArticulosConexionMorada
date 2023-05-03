package es.uca.articulosconexionmorada.cmSocial.reporte;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, UUID>  {
    List<Reporte> findByDateEliminatedIsNull();

    List<Reporte> findByDateEliminatedIsNotNull();

    Optional<Reporte> findByReportadoUuidAndReportadorUuidAndMensajeUuid(String reportadoUuid, String reportadorUuid, String mensajeUuid);
}
