package es.uca.articulosconexionmorada.cmSocial.reporte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    public Reporte save(Reporte reporte) {
        return reporteRepository.save(reporte);
    }

    public boolean reporteExists(String reportado_uuid, String reportador_uuid, String mensaje_uuid) {
        return reporteRepository.findByReportadoUuidAndReportadorUuidAndMensajeUuid(reportado_uuid, reportador_uuid, mensaje_uuid).isPresent();
    }

}
