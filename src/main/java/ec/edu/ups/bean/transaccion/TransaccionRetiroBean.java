package ec.edu.ups.bean.transaccion;

import ec.edu.ups.common.GeneralException;
import ec.edu.ups.dto.CatalogoDTO;
import ec.edu.ups.dto.transaccion.ComprobanteDTO;
import ec.edu.ups.dto.transaccion.DetalleRetiroDTO;
import ec.edu.ups.gestor.GeneralON;
import ec.edu.ups.gestor.TransaccionesON;
import ec.edu.ups.pojo.InfoCuenta;
import ec.edu.ups.pojo.InfoTransacion;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class TransaccionRetiroBean {

    @Inject
    private GeneralON generalON;
    @Inject
    private TransaccionesON transacionesON;

    private ComprobanteDTO comprobante;
    private InfoTransacion infoTransaccion;
    private InfoCuenta infoCuenta;
    private CatalogoDTO formaEgreso;
    private double total;

    private String numeroCuenta;
    private String mensajeError;
    private String mensajeSusses;

    private List<CatalogoDTO> listaFormasEgreso;

    @PostConstruct
    private void inicializar() {
        total = 0.0;
        numeroCuenta = "";
        infoCuenta = new InfoCuenta();
        comprobante = new ComprobanteDTO();
        comprobante.setDetallesRetiro(new ArrayList<>());
        cargarInfoTransaccion();
        cargarFormasEgreso();
    }

    public String guardarTransaccion() {
        eliminarMensajes();
        if(infoCuenta.getIdCuenta() == null){
            mensajeError = "Debe seeccionar una cuenta";
            return null;
        }
        if(comprobante.getDetallesRetiro().isEmpty()){
            mensajeError = "Debe agregar almenos un detalle";
            return null;
        }
        try {
            comprobante.setHaber(total);
            transacionesON.guardarComprobanteRetiro(
                    comprobante,
                    infoCuenta.getIdCuenta(),
                    infoTransaccion.getCodigoTransacion()
            );
            inicializar();
            mensajeSusses = "La transaccion retiro fue realizada con exito.";
        }catch (GeneralException ex){
            mensajeError = ex.getMessage();
        }
        return null;
    }

    public void agregarDetalle() {
        eliminarMensajes();
        if(formaEgreso != null) {
            if (comprobante.validarFormaEgreso(formaEgreso)) {
                double monto = Double.parseDouble(formaEgreso.getValor());
                DetalleRetiroDTO detalle = new DetalleRetiroDTO();
                detalle.setTipoEgreso(formaEgreso);
                detalle.setCantidad(1);
                detalle.setMonto(monto);
                detalle.setValor(monto);
                comprobante.addDetalleRetiro(detalle);
                calcularTotal();
            } else {
                mensajeError = "La forma de egreso seleccionada ya se encuentra agregada.";
            }
        }
    }

    private void calcularTotal(){
        total = 0.0;
        comprobante.getDetallesRetiro().forEach((d) -> {
            total += d.getValor();
        });
    }

    private void cargarInfoTransaccion() {
        try{
            eliminarMensajes();
            infoTransaccion = transacionesON.obtenerInfoTransaccionRetiro();
        }catch (GeneralException ex){
            mensajeError = ex.getMessage();
        }
    }

    private void cargarFormasEgreso() {
        try{
            eliminarMensajes();
            listaFormasEgreso = generalON.obtenerCatalogosFormaIE();
        }catch (GeneralException ex){
            mensajeError = ex.getMessage();
        }
    }

    public void buscarCuenta() {
        try {
            eliminarMensajes();
            infoCuenta = generalON.obtenerInfoCuenta(numeroCuenta);
            mensajeSusses = "Cuenta agregada satisfactoriamente.";
        }catch (GeneralException ex){
            mensajeError = "El numero de cuenta ingresado no existe.";
        }
    }

    public void blurCantidadDetalle(DetalleRetiroDTO detalle) {
        int index = comprobante.indexDetalleRetiro(detalle);
        detalle.setCantidad(detalle.getCantidad() == 0 ? 1: detalle.getCantidad());
        detalle.setValor(detalle.getMonto() * detalle.getCantidad());
        comprobante.replaceDetalleRetiro(index, detalle);
        calcularTotal();
    }

    public void eliminarListaDetalles(){
        eliminarMensajes();
        comprobante.getDetallesRetiro().clear();
        mensajeSusses = "Lista de detalles elimina";
    }

    private void eliminarMensajes(){
        mensajeError = "";
        mensajeSusses = "";
    }

    public ComprobanteDTO getComprobante() {
        return comprobante;
    }

    public void setComprobante(ComprobanteDTO comprobante) {
        this.comprobante = comprobante;
    }

    public InfoTransacion getInfoTransaccion() {
        return infoTransaccion;
    }

    public InfoCuenta getInfoCuenta() {
        return infoCuenta;
    }

    public void setInfoCuenta(InfoCuenta infoCuenta) {
        this.infoCuenta = infoCuenta;
    }

    public void setInfoTransaccion(InfoTransacion infoTransaccion) {
        this.infoTransaccion = infoTransaccion;
    }

    public CatalogoDTO getFormaEgreso() {
        return formaEgreso;
    }

    public void setFormaEgreso(CatalogoDTO formaEgreso) {
        this.formaEgreso = formaEgreso;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getMensajeSusses() {
        return mensajeSusses;
    }

    public void setMensajeSusses(String mensajeSusses) {
        this.mensajeSusses = mensajeSusses;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public List<CatalogoDTO> getListaFormasEgreso() {
        return listaFormasEgreso;
    }

    public void setListaFormasEgreso(List<CatalogoDTO> listaFormasEgreso) {
        this.listaFormasEgreso = listaFormasEgreso;
    }
}
