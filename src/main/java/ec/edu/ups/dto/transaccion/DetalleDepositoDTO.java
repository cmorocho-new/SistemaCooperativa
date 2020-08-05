package ec.edu.ups.dto.transaccion;

import ec.edu.ups.dto.AuditoriaDTO;
import ec.edu.ups.dto.CatalogoDTO;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import java.io.Serializable;

@Entity
@Table(name = "trn_detalles_depositos")
@SuppressWarnings("serial")
public class DetalleDepositoDTO extends AuditoriaDTO implements Serializable {

    @Column(name="cantidad", nullable = false, length = 10, scale = 2)
    private int cantidad;

    @Column(name="monto", nullable = false, length = 10, scale = 2)
    private double monto;

    @Column(name="valor", nullable = false, length = 10, scale = 2)
    private double valor;

    @Column(name="observacion", length = 200)
    private String observacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_ingreso_id", referencedColumnName = "id")
    private CatalogoDTO tipoIngreso;

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public CatalogoDTO getTipoIngreso() {
        return tipoIngreso;
    }

    public void setTipoIngreso(CatalogoDTO tipoIngreso) {
        this.tipoIngreso = tipoIngreso;
    }
}
