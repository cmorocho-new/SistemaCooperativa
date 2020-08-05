package ec.edu.ups.pojo;

import java.util.Date;

public class InfoTransacion {

    private Long idTransacion;

    private Date fechaTransacion;

    private String codigoTransacion;

    private Long numeroTransacion;

    public InfoTransacion(Long idTransacion, Long numeroTransacion) {
        this.idTransacion = idTransacion;
        this.numeroTransacion = numeroTransacion;
    }

    public InfoTransacion(Long idTransacion, Date fechaTransacion, String codigoTransacion, Long numeroTransacion) {
        this.idTransacion = idTransacion;
        this.fechaTransacion = fechaTransacion;
        this.codigoTransacion = codigoTransacion;
        this.numeroTransacion = numeroTransacion;
    }

    public Long getIdTransacion() {
        return idTransacion;
    }

    public void setIdTransacion(Long idTransacion) {
        this.idTransacion = idTransacion;
    }

    public Date getFechaTransacion() {
        return fechaTransacion;
    }

    public void setFechaTransacion(Date fechaTransacion) {
        this.fechaTransacion = fechaTransacion;
    }

    public String getCodigoTransacion() {
        return codigoTransacion;
    }

    public void setCodigoTransacion(String codigoTransacion) {
        this.codigoTransacion = codigoTransacion;
    }

    public Long getNumeroTransacion() {
        return numeroTransacion;
    }

    public void setNumeroTransacion(Long numeroTransacion) {
        this.numeroTransacion = numeroTransacion;
    }
}
