package ec.edu.ups.dao.credito;

import ec.edu.ups.common.GeneralException;
import ec.edu.ups.dto.credito.CreditoDTO;
import ec.edu.ups.dto.credito.CuotaCreditoDTO;
import ec.edu.ups.pojo.InfoCredito;
import ec.edu.ups.pojo.InfoCuota;
import ec.edu.ups.pojo.InfoPagoCredito;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CreditoDAO {

    @PersistenceContext
    private EntityManager em;

    /**
     * Guarda los datos de un credito
     * @throws GeneralException
     */
    public void guardarCredito(CreditoDTO creditoDTO, long idUsuario) throws GeneralException {
        try {
            Date fechaActual = new Date();
            creditoDTO.getCuotas().forEach((c) -> {
                c.setFechaRegistro(fechaActual);
                c.setUsuarioRegistro(idUsuario);
            });
            creditoDTO.setFechaRegistro(fechaActual);
            creditoDTO.setUsuarioRegistro(idUsuario);
            em.persist(creditoDTO);
        } catch (Exception ex) {
            throw new GeneralException("ERROR: " + ex.getMessage());
        }
    }

    /**
     * Obtiene el total de creditos de un cliente
     * @param idCliente
     * @return
     * @throws GeneralException
     */
    public long obtenerTotalCreditosCliente(long idCliente) throws GeneralException {
        try {
            return em.createQuery("SELECT COUNT(c.id) FROM CreditoDTO c " +
                    "WHERE c.solicitud.cliente.id = :idCliente", Long.class)
                    .setParameter("idCliente", idCliente)
                    .getSingleResult();
        } catch (Exception ex) {
            throw new GeneralException("ERROR: " + ex.getMessage());
        }
    }

    /**
     * Obtiene el total de garantes del cliente
     * @param idCliente
     * @return
     * @throws GeneralException
     */
    public long obtenerTotalGarante(long idCliente) throws GeneralException {
        try {
            return em.createQuery("SELECT COUNT(c.id) FROM CreditoDTO c " +
                    "WHERE c.solicitud.garantePrincipal.id = :idCliente", Long.class)
                    .setParameter("idCliente", idCliente)
                    .getSingleResult();
        } catch (Exception ex) {
            throw new GeneralException("ERROR: " + ex.getMessage());
        }
    }

    /**
     * Obtiene la info de una cuota
     * @param idCuotaCredito
     * @return
     * @throws GeneralException
     */
    public InfoCuota obtenerInfoCuota(long idCuotaCredito) throws GeneralException {
        try {
            return em.createQuery("SELECT NEW ec.edu.ups.pojo.InfoCuota (c.id, c.credito.comprobante.cuenta.id," +
                    "c.credito.comprobante.cuenta.saldoActual, c.saldo, c.estadoCredito.nombre) " +
                    "FROM CuotaCreditoDTO c where c.id = :idCuotaCredito", InfoCuota.class)
                    .setParameter("idCuotaCredito", idCuotaCredito)
                    .getSingleResult();
        } catch (Exception e) {
            throw new GeneralException("ERROR: " + e.getMessage());
        }
    }

    /**
     * Obtiene los pgos anteriores de una cuota
     * @param idCuotaCredito
     * @return
     * @throws GeneralException
     */
    public List<InfoPagoCredito> obtenerPagosCuota(long idCuotaCredito) throws GeneralException {
        try {
            return em.createQuery("SELECT NEW ec.edu.ups.pojo.InfoPagoCredito (p.comprobante.fecha, " +
                    "p.comprobante.numero, p.comprobante.haber, p.saldo) " +
                    "FROM PagoCuotaDTO p where p.cutaCredito.id = :idCuotaCredito", InfoPagoCredito.class)
                    .setParameter("idCuotaCredito", idCuotaCredito)
                    .getResultList();
        } catch (Exception e) {
            throw new GeneralException("ERROR: " + e.getMessage());
        }
    }

    /**
     * Lista los creditos de un cliente
     * @param idCliente
     * @return
     * @throws GeneralException
     */
    public List<InfoCredito> obtenerListaCreditosPorCliente(Long idCliente) throws GeneralException{
    	try {
            return em.createQuery("SELECT NEW ec.edu.ups.pojo.InfoCredito (p.id, p.comprobante.numero, " +
                    "p.solicitud.propositoCredito.nombre, p.comprobante.debe, p.saldo, p.fechaVencimiento) " +
                    "FROM CreditoDTO p where p.solicitud.cliente.id = :idClient", InfoCredito.class)
                    .setParameter("idClient", idCliente)
                    .getResultList();
		} catch (Exception e) {
	         throw new GeneralException("ERROR: " + e.getMessage());
		}
    }

    /**
     * Obtiene las cuotas de pago de un credito
     * @param idCredito
     * @return
     * @throws GeneralException
     */
    public List<CuotaCreditoDTO> obtenerCuotasCredito(long idCredito) throws GeneralException {
        try {
            return em.createQuery("SELECT NEW ec.edu.ups.dto.credito.CuotaCreditoDTO (c.id, c.fechaPlazo, " +
                    "c.valor, c.saldo, c.estadoCredito.nombre ) " +
                    "FROM CuotaCreditoDTO c where c.credito.id = :idCredito", CuotaCreditoDTO.class)
                    .setParameter("idCredito", idCredito)
                    .getResultList();
        } catch (Exception e) {
            throw new GeneralException("ERROR: " + e.getMessage());
        }
    }
}


