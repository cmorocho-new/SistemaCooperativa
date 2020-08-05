package ec.edu.ups.dto.credito;

import ec.edu.ups.dto.AuditoriaDTO;
import ec.edu.ups.dto.transaccion.ComprobanteDTO;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Modelo DTO para los atribuitos de la tabla ctr_creditos
 */

@Entity
@Table(name = "ctr_creditos")
public class CreditoDTO extends AuditoriaDTO implements Serializable {

    @Column(name="fecha_inicio", nullable = false)
    private Date fechaInicio;

    @Column(name="fecha_vencimiento", nullable = false)
    private Date fechaVencimiento;

    @Column(name= "saldo", nullable = false, precision = 10, scale = 2)
    private double saldo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comprobante_id", referencedColumnName = "id", nullable = false, unique = true)
    private ComprobanteDTO comprobante;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitud_id", referencedColumnName = "id", nullable = false, unique = true)
    private SolicitudCreditoDTO solicitud;

    @OneToMany(mappedBy = "credito", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CuotaCreditoDTO> cuotas;

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public ComprobanteDTO getComprobante() {
        return comprobante;
    }

    public void setComprobante(ComprobanteDTO comprobante) {
        this.comprobante = comprobante;
    }

    public SolicitudCreditoDTO getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudCreditoDTO solicitud) {
        this.solicitud = solicitud;
    }

    public List<CuotaCreditoDTO> getCuotas() {
        return cuotas;
    }

    public void setCuotas(List<CuotaCreditoDTO> cuotas) {
        this.cuotas = cuotas;
    }
}
