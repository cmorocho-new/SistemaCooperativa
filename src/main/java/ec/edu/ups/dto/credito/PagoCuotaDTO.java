package ec.edu.ups.dto.credito;

import ec.edu.ups.dto.AuditoriaDTO;
import ec.edu.ups.dto.transaccion.ComprobanteDTO;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="trn_pagos_cuotas_creditos")
public class PagoCuotaDTO extends AuditoriaDTO implements Serializable {

    @Column(name= "saldo", nullable = false, precision = 10, scale = 2)
    private double saldo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comprobante_id", referencedColumnName = "id", nullable = false, unique = true)
    private ComprobanteDTO comprobante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuota_credito_id", referencedColumnName = "id", nullable = false)
    private CuotaCreditoDTO cutaCredito;

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

    public CuotaCreditoDTO getCutaCredito() {
        return cutaCredito;
    }

    public void setCutaCredito(CuotaCreditoDTO cutaCredito) {
        this.cutaCredito = cutaCredito;
    }
}
