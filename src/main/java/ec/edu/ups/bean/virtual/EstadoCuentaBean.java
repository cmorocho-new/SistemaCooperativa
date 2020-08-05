package ec.edu.ups.bean.virtual;

import ec.edu.ups.common.GeneralException;
import ec.edu.ups.gestor.TransaccionesON;
import ec.edu.ups.pojo.DetalleTransaccion;
import ec.edu.ups.utils.SessionUtil;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class EstadoCuentaBean {

    @Inject
    private TransaccionesON transaccionesON;
    private List<DetalleTransaccion> detallesTransaciones;
    private Date fechaInicio;
    private Date fechaFin;

    @PostConstruct
    private void inicializar() {
        cargarEstadoCuenta();
    }

    private void cargarEstadoCuenta() {
        try {
            GregorianCalendar fechaActual = new GregorianCalendar();
            fechaActual.setTime(new Date());
            fechaActual.add(Calendar.DAY_OF_MONTH, -30);
            long idUsuario = SessionUtil.getIdUsuarioLogeado();
            detallesTransaciones = transaccionesON
                    .obtenerTransaccionesCuenta(idUsuario, fechaActual.getTime(),new Date());
        }catch (GeneralException ex) {

        }
    }

    public void cargarEstadoCuentaFecha(){
        try {
            long idUsuario = SessionUtil.getIdUsuarioLogeado();
            detallesTransaciones = transaccionesON
                    .obtenerTransaccionesCuenta(idUsuario, fechaInicio, fechaFin);
        }catch (GeneralException ex) {

        }
    }

    public List<DetalleTransaccion> getDetallesTransaciones() {
        return detallesTransaciones;
    }

    public void setDetallesTransaciones(List<DetalleTransaccion> detallesTransaciones) {
        this.detallesTransaciones = detallesTransaciones;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
}
