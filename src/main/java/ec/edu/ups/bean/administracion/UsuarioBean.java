package ec.edu.ups.bean.administracion;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import ec.edu.ups.common.GeneralException;
import ec.edu.ups.dto.administracion.RolDTO;
import ec.edu.ups.dto.administracion.UsuarioDTO;
import ec.edu.ups.gestor.AdministracionON;
import ec.edu.ups.utils.MessagesUtil;
import ec.edu.ups.utils.SessionUtil;

/**
 * Creacion de modelo para la tabla adm_usuarios
 * 
 * @author Damián Sumba
 *
 */

public class UsuarioBean {
	
	/**
     * Atributos 
     */

	@Inject
	private AdministracionON administracionON;

	private UsuarioDTO usuarioDTO;

	private List<RolDTO> listaRoles;

	@PostConstruct
	public void inicializar() {
		usuarioDTO = new UsuarioDTO();
		cargarRoles();
	}

	/**
     * Método para listar todos los tipos de roles y gargar en una interfaz grafica
     */
	public void cargarRoles() {
		try {
			this.setListaRoles(administracionON.obtenerInfoRoles());
		} catch (GeneralException ex) {
			ex.printStackTrace();
		}
	}

	/**
     * Método para guardar un usuario nuevo
     */
	public String guardarUsuariosDto() {
		try {
			long idUsuario = SessionUtil.getIdUsuarioLogeado();
			administracionON.guardarUsuario(usuarioDTO, false, idUsuario);
			MessagesUtil.agregarMensajeDone("DONE: El usuario fue agregado satisfactoriamente");
			inicializar();
		} catch (GeneralException e) {
			MessagesUtil.agregarMensajeError(e.getMessage());
		}
		return null;
	}

	
	/**
     * Método de get y set de los diferentes atributos instanciados 
     */

	public UsuarioDTO getUsuarioDTO() {
		return usuarioDTO;
	}

	public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public List<RolDTO> getListaRoles() {
		return listaRoles;
	}

	public void setListaRoles(List<RolDTO> listaRoles) {
		this.listaRoles = listaRoles;
	}

}
