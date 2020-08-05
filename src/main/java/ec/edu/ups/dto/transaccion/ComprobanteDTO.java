package ec.edu.ups.dto.transaccion;

import ec.edu.ups.dto.AuditoriaDTO;
import ec.edu.ups.dto.CatalogoDTO;
import ec.edu.ups.dto.CuentaDTO;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Creacion de modelo para la tabla trn_transacciones
 */
@Entity
@Table(name = "trn_comprobantes")
@SuppressWarnings("serial")
public class ComprobanteDTO extends AuditoriaDTO implements Serializable {

    @Column(name="numero",  nullable = false, length = 10)
    private Long numero;

    @Column(name="fecha",  nullable = false)
    private Date fecha;

    @Column(name="observacion", length = 200)
    private String observacion;

    @Column(name="debe", length = 10, scale = 2)
    private double debe;

    @Column(name="haber", length = 10, scale = 2)
    private double haber;

    @Column(name="saldo", length = 10, scale = 2)
    private double saldo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_id", referencedColumnName = "id")
    private CuentaDTO cuenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaccion_id", referencedColumnName = "id")
    private TransaccionDTO transaccion;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "comprobante_id", referencedColumnName = "id")
    private List<DetalleDepositoDTO> detallesDeposito;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "comprobante_id", referencedColumnName = "id")
    private List<DetalleRetiroDTO> detallesRetiro;

    public ComprobanteDTO(){

    }

    public ComprobanteDTO(long id){
        super.setId(id);
    }

    public boolean validarFormaEgreso(CatalogoDTO forma){
        long total = detallesRetiro.stream()
                .filter((d) -> d.getTipoEgreso().getId().equals(forma.getId()))
                .count();
        return total != 1;
    }

    public boolean validarFormaIngreso(CatalogoDTO forma){
        long total = detallesDeposito.stream()
                .filter((d) -> d.getTipoIngreso().getId().equals(forma.getId()))
                .count();
        return total == 1;
    }

    public void addDetalleRetiro(DetalleRetiroDTO detalle){
        detallesRetiro.add(detalle);
    }

    public void addDetalleDeposito(DetalleDepositoDTO detalle){
        detallesDeposito.add(detalle);
    }

    public void replaceDetalleRetiro(int index, DetalleRetiroDTO detalle){
        detallesRetiro.set(index, detalle);
    }

    public int indexDetalleRetiro(DetalleRetiroDTO detalle){
        return detallesRetiro.indexOf(detalle);
    }

    public void replaceDetalleDeposito(int index, DetalleDepositoDTO detalle){
        detallesDeposito.set(index, detalle);
    }

    public int indexDetalleDeposito(DetalleDepositoDTO detalle){
        return detallesDeposito.indexOf(detalle);
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public double getDebe() {
        return debe;
    }

    public void setDebe(double debe) {
        this.debe = debe;
    }

    public double getHaber() {
        return haber;
    }

    public void setHaber(double haber) {
        this.haber = haber;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public CuentaDTO getCuenta() {
        return cuenta;
    }

    public void setCuenta(CuentaDTO cuenta) {
        this.cuenta = cuenta;
    }

    public TransaccionDTO getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(TransaccionDTO transaccion) {
        this.transaccion = transaccion;
    }

    public List<DetalleDepositoDTO> getDetallesDeposito() {
        return detallesDeposito;
    }

    public void setDetallesDeposito(List<DetalleDepositoDTO> detallesDeposito) {
        this.detallesDeposito = detallesDeposito;
    }

    public List<DetalleRetiroDTO> getDetallesRetiro() {
        return detallesRetiro;
    }

    public void setDetallesRetiro(List<DetalleRetiroDTO> detallesRetiro) {
        this.detallesRetiro = detallesRetiro;
    }
}
