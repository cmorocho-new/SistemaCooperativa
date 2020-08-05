package ec.edu.ups.bean;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import ec.edu.ups.common.GeneralException;
import ec.edu.ups.dto.CatalogoDTO;
import ec.edu.ups.dto.ClienteDTO;
import ec.edu.ups.gestor.GeneralON;
import ec.edu.ups.utils.MessagesUtil;
import ec.edu.ups.utils.SessionUtil;

/**
 * Creacion de ManagedBean para Clientes
 * 
 * @author Nicolas Anazco
 *
 */

public class ClienteBean {
	
	@Inject
	private GeneralON generalON;

	private ClienteDTO clienteDTO;
	private List<CatalogoDTO> listaEstadoCivil;
	private List<CatalogoDTO> listaGenero;
	private List<CatalogoDTO> listaIdentificacion;
	
	/**
     * Método de inicialización de un bean
     */
	@PostConstruct
	public void inicializar() {
		clienteDTO =  new ClienteDTO();
		cargarCatalogos();
	}

	/**
     * Método que guarda un cliente
	 *
     */
	public String guardarClientesDto() {
		try {
			long idUsuario = SessionUtil.getIdUsuarioLogeado();
			clienteDTO.setId(null);
			generalON.guardarCliente(clienteDTO, idUsuario);
			MessagesUtil.agregarMensajeDone("DONE: El cliente fue agregado satisfactoriamente");
			inicializar();
		} catch (GeneralException e) {
			MessagesUtil.agregarMensajeError(e.getMessage());
		}		
		return null;
	}

	/**
     * Método que retorna una lista de Estados Civiles
     */
	public void cargarCatalogos(){
		try {
			setListaEstadoCivil(generalON.obtenerCatalogosEstadoCivil());
			setListaGenero(generalON.obtenerCatalogosGenero());
			setListaIdentificacion(generalON.obtenerCatalogosIdentificacion());
		}catch (GeneralException ex) {
			ex.printStackTrace();
		}
	}
	
	public List<CatalogoDTO> getListaIdentificacion() {
		return listaIdentificacion;
	}

	public void setListaIdentificacion(List<CatalogoDTO> listaIdentificacion) {
		this.listaIdentificacion = listaIdentificacion;
	}

	public List<CatalogoDTO> getListaGenero() {
		return listaGenero;
	}

	public void setListaGenero(List<CatalogoDTO> listaGenero) {
		this.listaGenero = listaGenero;
	}

	public List<CatalogoDTO> getListaEstadoCivil() {
		return listaEstadoCivil;
	}

	public void setListaEstadoCivil(List<CatalogoDTO> listaEstadoCivil) {
		this.listaEstadoCivil = listaEstadoCivil;
	}

	public ClienteDTO getClienteDTO() {
		return clienteDTO;
	}

	public void setClienteDTO(ClienteDTO clienteDTO) {
		this.clienteDTO = clienteDTO;
	}
}
