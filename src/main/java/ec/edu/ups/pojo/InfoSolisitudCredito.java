package ec.edu.ups.pojo;

public class InfoSolisitudCredito {

    private long idSolicitud;
    private String solicitante;
    private String identificacion;
    private String correo;
    private String tipoCredito;
    private String observacion;
    private int plazoCredito;
    private double montoCredito;
    private double interes;
    private int tipo;

    public InfoSolisitudCredito(long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public InfoSolisitudCredito(long idSolicitud, String solicitante, String tipoCredito, int plazoCredito,
                                double montoCredito, String tipo) {
        this.idSolicitud = idSolicitud;
        this.solicitante = solicitante;
        this.tipoCredito = tipoCredito;
        this.plazoCredito = plazoCredito;
        this.montoCredito = montoCredito;
        if (tipo == null) {
            this.tipo = 0;
        }else {
            this.tipo = Integer.parseInt(tipo);
        }
    }

    public long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getTipoCredito() {
        return tipoCredito;
    }

    public void setTipoCredito(String tipoCredito) {
        this.tipoCredito = tipoCredito;
    }

    public int getPlazoCredito() {
        return plazoCredito;
    }

    public void setPlazoCredito(int plazoCredito) {
        this.plazoCredito = plazoCredito;
    }

    public double getMontoCredito() {
        return montoCredito;
    }

    public void setMontoCredito(double montoCredito) {
        this.montoCredito = montoCredito;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public double getInteres() {
        return interes;
    }

    public void setInteres(double interes) {
        this.interes = interes;
    }
}

