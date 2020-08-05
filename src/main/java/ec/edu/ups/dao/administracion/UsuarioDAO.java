package ec.edu.ups.dao.administracion;

import ec.edu.ups.common.GeneralException;
import ec.edu.ups.dto.administracion.UsuarioDTO;
import ec.edu.ups.pojo.InfoUsuario;

import java.util.Date;
import javax.ejb.NoSuchEntityException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Objeto de Acceso a Datos para Usuarios
 * 
 * @author Damián Sumba
 *
 */
@Stateless
public class UsuarioDAO {
	
	@PersistenceContext
	private EntityManager em;

	/**
	 * Guarda los datos de un usuario
	 * @param usuarioDTO
	 * @throws GeneralException
	 */
	public void guardarUsuario (UsuarioDTO usuarioDTO, long idUsuario) throws GeneralException {
		try {
			usuarioDTO.setFechaRegistro(new Date());
			usuarioDTO.setUsuarioRegistro(idUsuario);
			em.persist(usuarioDTO);
		} catch (Exception ex) {
			throw new GeneralException("ERROR: " + ex.getMessage());
		}
	}

	/**
	 * Obtiene la informacion de un usario administrativo mediante el correo
	 * @param correo
	 * @return
	 * @throws GeneralException
	 */
	public InfoUsuario obtenerUsuarioAdministrativo(String correo) throws GeneralException {
		try {
			return em.createQuery("SELECT NEW ec.edu.ups.pojo.InfoUsuario(u.id, u.nombre, u.contresena, " +
					"u.rol.codigo, u.rol.nombre) FROM UsuarioDTO u " +
					"WHERE u.correo = :correo AND u.esCliente = :esCliente",
					 InfoUsuario.class)
					.setParameter("correo",  correo)
					.setParameter("esCliente",  false)
					.getSingleResult();
		}catch (NoSuchEntityException ex) {
			throw new GeneralException(101, "Usuario no encontrado");
		}catch (Exception ex) {
			throw new GeneralException("ERROR: " + ex.getMessage());
		}
	}

	/**
	 * Obtiene la informacion de un usario cliente mediante el correo
	 * @param correo
	 * @return
	 */
	public InfoUsuario obtenerUsuarioCliente(String correo) throws GeneralException {
		try {
			return em.createQuery("SELECT NEW ec.edu.ups.pojo.InfoUsuario(u.id, u.nombre, u.contresena) " +
					"FROM UsuarioDTO u " +
					"WHERE u.correo = :correo AND u.esCliente = :esCliente ", InfoUsuario.class)
					.setParameter("correo",  correo)
					.setParameter("esCliente",  true)
					.getSingleResult();
		}catch (NoSuchEntityException ex) {
			throw new GeneralException(101, "Usuario no encontrado");
		}catch (Exception ex){
			throw new GeneralException("ERROR: " + ex.getMessage());
		}
	}


	
}
