package ec.edu.ups.dao.credito;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ec.edu.ups.common.GeneralException;
import ec.edu.ups.dto.credito.SolicitudCreditoDTO;
import ec.edu.ups.pojo.InfoSolisitudCredito;

import java.util.Date;
import java.util.List;

@Stateless
public class SolicitudCreditoDAO {
	
	@PersistenceContext
	private EntityManager em;

	/**
	 * Guarda una solicitud de credito
	 * @param solicitudCreditoDTO
	 * @param idUsuario
	 * @throws GeneralException
	 */
	public void guadarSolicitud(SolicitudCreditoDTO solicitudCreditoDTO, long idUsuario) throws GeneralException {
		try {
			solicitudCreditoDTO.setFechaRegistro(new Date());
			solicitudCreditoDTO.setUsuarioRegistro(idUsuario);
			em.persist(solicitudCreditoDTO);
		} catch (Exception ex) {
			throw new GeneralException("ERROR: " + ex.getMessage());
		}
	}

	/**
	 * Actualiza una solicitud de credito
	 * @param solicitudCreditoDTO
	 * @throws GeneralException
	 */
	public void actualizarSolicitud(SolicitudCreditoDTO solicitudCreditoDTO) throws GeneralException {
		try {
			em.merge(solicitudCreditoDTO);
		} catch (Exception ex) {
			throw new GeneralException("ERROR: " + ex.getMessage());
		}
	}

	/**
	 * Actualiza el estado de la solicitud
	 * @param idSolicitud
	 * @param idEstado
	 * @param observacion
	 * @throws GeneralException
	 */
	public void actualizarEstadoSolicitud(long idSolicitud, long idEstado, String observacion) throws GeneralException {
		try {
			em.createQuery("UPDATE SolicitudCreditoDTO s SET s.estado.id = :idEstado, " +
					"s.observacion = :observacion "+
					"WHERE s.id = :idSolicitud")
					.setParameter("idEstado", idEstado)
					.setParameter("observacion", observacion)
					.setParameter("idSolicitud", idSolicitud)
					.executeUpdate();
		} catch (Exception ex) {
			throw new GeneralException("ERROR: " + ex.getMessage());
		}
	}

	/**
	 * Lista las solicitudes de credito por estado
	 * @return
	 * @throws GeneralException
	 */
	public List<InfoSolisitudCredito> obtenerSolicitudesCredito(long idEstado) throws GeneralException {
		try {
			return em.createQuery("SELECT NEW ec.edu.ups.pojo.InfoSolisitudCredito(s.id, " +
					"CONCAT(s.cliente.nombres, ' ', s.cliente.apellidos), s.propositoCredito.nombre, " +
					"s.plazoMeses, s.montoCredito, c.valor) " +
					"FROM SolicitudCreditoDTO s LEFT JOIN CatalogoDTO c ON c.id = s.tipoCliente.id " +
					"WHERE s.estado.id = :idEstado", InfoSolisitudCredito.class)
					.setParameter("idEstado", idEstado)
					.getResultList();
		} catch (Exception ex) {
			throw new GeneralException("ERROR: " + ex.getMessage());
		}
	}

	/**
	 * Obtiene los datos de la solicitud
	 * @param idSolicitud
	 * @return
	 * @throws GeneralException
	 */
	public SolicitudCreditoDTO obtenerInfoSolicitudCredito(long idSolicitud) throws GeneralException {
		try {
			return em.createQuery("SELECT s FROM SolicitudCreditoDTO s " +
					"WHERE s.id = :idSolicitud", SolicitudCreditoDTO.class)
					.setParameter("idSolicitud", idSolicitud)
					.getSingleResult();
		} catch (Exception ex) {
			throw new GeneralException("ERROR: " + ex.getMessage());
		}
	}

}
