package ec.edu.ups.dao;

import ec.edu.ups.common.GeneralException;
import ec.edu.ups.dto.ClienteDTO;
import ec.edu.ups.pojo.InfoCliente;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

/**
 * Objeto de Acceso a Datos para Clientes
 * 
 * @author Nicolas Anazco
 *
 */

@Stateless
public class ClienteDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	/**
     * Guarda los datos de un cliente
     */
	public void guardarCliente(ClienteDTO clienteDTO, long idUsario) throws GeneralException {
		try {
			clienteDTO.setFechaRegistro(new Date());
			clienteDTO.setUsuarioRegistro(idUsario);
			em.persist(clienteDTO);
		} catch (Exception ex) {
			throw new GeneralException("ERROR: " + ex.getMessage());
		}
	}

	/**
	 * Obtien el id del cliente por su identificacion
	 * @param identificacion
	 * @return
	 * @throws GeneralException
	 */
	public long obtenerIdCliente(String identificacion) throws GeneralException {
		try {
			return em.createQuery("SELECT ct.cliente.id " +
					"FROM CuentaDTO ct " +
					"WHERE ct.cliente.identificacion = :identificacion", Long.class)
					.setParameter("identificacion", identificacion)
					.getSingleResult();
		} catch (Exception ex) {
			throw new GeneralException("ERROR: " + ex.getMessage());
		}
	}

	/**
	 * Obtiene el id del cliente por el id del usuraio
	 * @param idUsuario
	 * @return
	 * @throws GeneralException
	 */
	public long obtenerIdCliente(long idUsuario) throws GeneralException {
		try {
			return em.createQuery("SELECT ct.cliente.id " +
					"FROM CuentaDTO ct " +
					"WHERE ct.usuario.id = :idUsuario", Long.class)
					.setParameter("idUsuario", idUsuario)
					.getSingleResult();
		} catch (Exception ex) {
			throw new GeneralException("ERROR: " + ex.getMessage());
		}
	}

	/**
	 * Obtiene la info del cliente por su id
	 * @param idCliente
	 * @return
	 * @throws GeneralException
	 */
	public InfoCliente obtenerInfoCliente(long idCliente) throws GeneralException {
		try {
			return em.createQuery("SELECT NEW ec.edu.ups.pojo.InfoCliente(ct.cliente.id, " +
					"ct.cliente.identificacion, ct.saldoActual, ct.cliente.estadoCivil.codigo, " +
					"ct.cliente.genero.codigo, ct.cliente.fechaNacimiento) " +
					"FROM CuentaDTO ct " +
					"WHERE ct.cliente.id = :idCliente", InfoCliente.class)
					.setParameter("idCliente", idCliente)
					.getSingleResult();
		} catch (Exception ex) {
			throw new GeneralException("ERROR: " + ex.getMessage());
		}
	}

}
