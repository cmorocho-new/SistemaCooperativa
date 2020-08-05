package ec.edu.ups.dao.transaccion;

import ec.edu.ups.common.GeneralException;
import ec.edu.ups.dto.transaccion.ComprobanteDTO;
import ec.edu.ups.pojo.DetalleTransaccion;
import ec.edu.ups.pojo.InfoTransacion;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;


@Stateless
public class TransaccionDAO {

@PersistenceContext
    private EntityManager em;

    /**
     * Guardar un comprobante y sus detalles
     * @param comprobante
     * @throws GeneralException
     */
    public void guardarComprobante(ComprobanteDTO comprobante) throws  GeneralException {
        try {
            comprobante.setFechaRegistro(new Date());
            comprobante.setUsuarioRegistro((long) 1);
            em.persist(comprobante);
        }catch (Exception ex){
            throw new GeneralException("ERROR: " + ex.getMessage());
        }
    }

    /**
     * Guardar un comprobante Retiro
     * @param comprobante
     * @throws GeneralException
     */
    public void guardarComprobanteRetiro(ComprobanteDTO comprobante) throws  GeneralException {
        // Argea losa tributos de auditoria a los detalles
        if (comprobante.getDetallesRetiro() != null) {
            comprobante.getDetallesRetiro().forEach((detalle) -> {
                detalle.setFechaRegistro(new Date());
                detalle.setUsuarioRegistro((long) 1);
            });
        }
        guardarComprobante(comprobante);
    }

    /**
     * Guardar un comprobante Retiro
     * @param comprobante
     * @throws GeneralException
     */
    public void guardarComprobanteDeposito(ComprobanteDTO comprobante) throws  GeneralException {
        // Argea losa tributos de auditoria a los detalles
        if ( comprobante.getDetallesDeposito() != null) {
            comprobante.getDetallesDeposito().forEach((detalle) -> {
                detalle.setFechaRegistro(new Date());
                detalle.setUsuarioRegistro((long) 1);
            });
        }
        guardarComprobante(comprobante);
    }


    /**
     *
     * @param idTransacion
     * @throws GeneralException
     */
    public void actualizarNumeroTransaccion(Long idTransacion) throws  GeneralException {
        try {
            em.createQuery("UPDATE TransaccionDTO T set t.numeroSiguiente = t.numeroSiguiente + t.numeroSumador " +
                    "WHERE t.id = :idTransacion")
                    .setParameter("idTransacion",  idTransacion)
                    .executeUpdate();
        }catch (Exception ex){
            throw new GeneralException("ERROR: " + ex.getMessage());
        }
    }

    /**
     * Consulta el numero siguiente de la trnasacion
     * @param codigoTrans
     * @return
     * @throws GeneralException
     */
    public InfoTransacion obtenerInfoTransaccion(String codigoTrans) throws  GeneralException {
        try {
            return em.createQuery("SELECT NEW ec.edu.ups.pojo.InfoTransacion(t.id, t.numeroSiguiente) " +
                    "FROM TransaccionDTO t " +
                    "WHERE t.codigo = :codigoTrans", InfoTransacion.class)
                    .setParameter("codigoTrans",  codigoTrans)
                    .getSingleResult();
        }catch (Exception ex){
            ex.printStackTrace();
            throw new GeneralException("ERROR: " + ex.getMessage());
        }
    }

    /**
     * Obtiene las transaciones de la cuenta de un usuairo
     * @return
     */
    public List<DetalleTransaccion> obtenerTransaccionesCuenta(long idUsuario, Date fechaInicio, Date fechaFin) throws  GeneralException {
        try {
            return em.createQuery("SELECT NEW ec.edu.ups.pojo.DetalleTransaccion(c.fecha, " +
                    "t.codigo , t.nombre, c.debe, c.haber, c.saldo) FROM ComprobanteDTO c " +
                    "JOIN TransaccionDTO t ON c.transaccion.id = t.id " +
                    "JOIN CuentaDTO u ON c.cuenta.id = u.id " +
                    "WHERE u.usuario.id = :idUsuario AND c.fecha >= :fechaInicio AND c.fecha <= :fechaFin", DetalleTransaccion.class)
                    .setParameter("idUsuario",  idUsuario)
                    .setParameter("fechaInicio",  fechaInicio)
                    .setParameter("fechaFin",  fechaFin)
                    .getResultList();
        }catch (Exception ex){
            throw new GeneralException("ERROR: " + ex.getMessage());
        }
    }

}
