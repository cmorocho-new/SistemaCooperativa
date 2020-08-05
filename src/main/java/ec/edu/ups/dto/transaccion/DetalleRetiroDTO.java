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
@Table(name = "trn_detalles_retiros")
@SuppressWarnings("serial")
public class DetalleRetiroDTO extends AuditoriaDTO implements Serializable {

    @Column(name="cantidad", nullable = false, length = 10)
    private int cantidad;

    @Column(name="monto", nullable = false, length = 10, scale = 2)
    private double monto;

    @Column(name="valor", nullable = false, length = 10, scale = 2)
    private double valor;

    @Column(name="observacion", length = 200)
    private String observacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_egreso_id", referencedColumnName = "id")
    private CatalogoDTO tipoEgreso;

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

    public CatalogoDTO getTipoEgreso() {
        return tipoEgreso;
    }

    public void setTipoEgreso(CatalogoDTO tipoEgreso) {
        this.tipoEgreso = tipoEgreso;
    }
}
