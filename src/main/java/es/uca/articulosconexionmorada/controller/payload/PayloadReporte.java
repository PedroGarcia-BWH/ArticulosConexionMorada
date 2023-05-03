package es.uca.articulosconexionmorada.controller.payload;

public class PayloadReporte {

    private String reportadoUuid;

    private String reportadorUuid;

    private String motivo;

    private String descripcion;

    private String mensajeUuid;

    public PayloadReporte(){}

    public PayloadReporte(String reportado_uuid, String reportador_uuid, String motivo, String descripcion, String mensaje_uuid) {
        this.reportadoUuid = reportado_uuid;
        this.reportadorUuid = reportador_uuid;
        this.motivo = motivo;
        this.descripcion = descripcion;
        this.mensajeUuid = mensaje_uuid;
    }

    //getters

    public String getReportado_uuid() {
        return reportadoUuid;
    }

    public String getReportador_uuid() {
        return reportadorUuid;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getMensaje_uuid() {
        return mensajeUuid;
    }

    //setters

    public void setReportado_uuid(String reportado_uuid) {
        this.reportadoUuid = reportado_uuid;
    }

    public void setReportador_uuid(String reportador_uuid) {
        this.reportadorUuid = reportador_uuid;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setMensaje_uuid(String mensaje_uuid) {
        this.mensajeUuid = mensaje_uuid;
    }
}
