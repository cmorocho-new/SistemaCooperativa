package ec.edu.ups.dto.credito;

import ec.edu.ups.dto.AuditoriaDTO;
import ec.edu.ups.dto.CatalogoDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Modelo DTO para los atribuitos de la tabla ctr_cuotas_creditos
 */

@Entity
@Table(name = "ctr_cuotas_creditos")
public class CuotaCreditoDTO extends AuditoriaDTO implements Serializable {

    @Column(name="fecha_plazo", nullable = false)
    private Date fechaPlazo;

    @Column(name="fecha_pago")
    private Date fechaPago;

    @Column(name="valor", length = 10, scale = 2, nullable = false)
    private double valor;

    @Column(name="interez", length = 10, scale = 2, nullable = false)
    private double interez;

    @Column(name="amortizacion", length = 10, scale = 2, nullable = false)
    private double amortizacion;

    @Column(name="saldo", length = 10, scale = 2, nullable = false)
    private double saldo;

    @Column(name="saldo_final", length = 10, scale = 2, nullable = false)
    private double saldoFinal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_credito_id", referencedColumnName = "id", nullable = false)
    private CatalogoDTO estadoCredito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credito_id", referencedColumnName = "id", nullable = false)
    private CreditoDTO credito;

    public CuotaCreditoDTO() {
    }

    public CuotaCreditoDTO(long id) {
        super.setId(id);
    }

    public CuotaCreditoDTO(long id, Date fechaPlazo, double valor, double saldo, String nombreEstado) {
        super.setId(id);
        this.fechaPlazo = fechaPlazo;
        this.saldo = saldo;
        this.valor = valor;
        this.estadoCredito = new CatalogoDTO(0, nombreEstado);
    }

    public Date getFechaPlazo() {
        return fechaPlazo;
    }

    public void setFechaPlazo(Date fechaPlazo) {
        this.fechaPlazo = fechaPlazo;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getInterez() {
        return interez;
    }

    public void setInterez(double interez) {
        this.interez = interez;
    }

    public double getAmortizacion() {
        return amortizacion;
    }

    public void setAmortizacion(double amortizacion) {
        this.amortizacion = amortizacion;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(double saldoFinal) {
        this.saldoFinal = saldoFinal;
    }

    public CatalogoDTO getEstadoCredito() {
        return estadoCredito;
    }

    public void setEstadoCredito(CatalogoDTO estadoCredito) {
        this.estadoCredito = estadoCredito;
    }

    public CreditoDTO getCredito() {
        return credito;
    }

    public void setCredito(CreditoDTO credito) {
        this.credito = credito;
    }
}
