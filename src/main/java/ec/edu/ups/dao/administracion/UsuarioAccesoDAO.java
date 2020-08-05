package ec.edu.ups.dao.administracion;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.common.GeneralException;
import ec.edu.ups.dto.administracion.UsuarioAccesoDTO;

/**
 * Objeto de Acceso a Datos para Usuario Accesos
 * 
 * @author Nicolas Anazco
 *
 */

@Stateless
public class UsuarioAccesoDAO {

	@PersistenceContext
	private EntityManager em;
	
	/**
     * Guarda un acceso a la banca virtual
     */
	public void guardarUsuarioAcceso(UsuarioAccesoDTO usuarioAccesoDTO, long idUsuario) throws GeneralException {
		try {
			usuarioAccesoDTO.setFechaRegistro(new Date());
			usuarioAccesoDTO.setUsuarioRegistro(idUsuario);
			em.persist(usuarioAccesoDTO);
		}catch(Exception ex) {
			throw new GeneralException("ERROR: " + ex.getMessage());
		}
	}
	
	/**
     * Obtiene la lista de acceso de un usuario a la banca virtual
     */
    public List<UsuarioAccesoDTO> obtenerUsuarioAcceso(long idUsuario) throws GeneralException {
        try{
            return em.createQuery("SELECT ua FROM UsuarioAccesoDTO ua WHERE ua.usuarioDTO.id = :idUsuario",
					UsuarioAccesoDTO.class)
                    .setParameter("idUsuario",  idUsuario)
					.getResultList();
        }catch (Exception ex) {
            throw new GeneralException("ERROR: " + ex.getMessage());
        }
    }
	
}
