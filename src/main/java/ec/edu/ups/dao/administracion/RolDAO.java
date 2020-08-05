package ec.edu.ups.dao.administracion;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ec.edu.ups.common.GeneralException;
import ec.edu.ups.dto.administracion.RolDTO;


@Stateless
public class RolDAO {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Obtiene la lista de los roles
	 * @return
	 * @throws GeneralException
	 */
	public List<RolDTO> listarRoles() throws GeneralException {
		try{
			return em.createQuery("SELECT NEW ec.edu.ups.dto.administracion.RolDTO(r.id, r.codigo, r.nombre) " +
					"FROM RolDTO r", RolDTO.class)
					.getResultList();
		}catch (Exception ex) {
			throw new GeneralException("ERROR: " + ex.getMessage());
		}
	}
}
