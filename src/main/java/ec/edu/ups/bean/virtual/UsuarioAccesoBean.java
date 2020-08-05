package ec.edu.ups.bean.virtual;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import ec.edu.ups.common.GeneralException;
import ec.edu.ups.dto.administracion.UsuarioAccesoDTO;
import ec.edu.ups.gestor.AdministracionON;
import ec.edu.ups.utils.SessionUtil;

/**
 * Creacion de ManagedBean para Usuario Accesos
 * 
 * @author Nicolas Anazco
 *
 */
public class UsuarioAccesoBean {
	
	@Inject
	private AdministracionON administracionON;
	private List<UsuarioAccesoDTO> listaAccesos;

	@PostConstruct
	public void init() {
		cargarAccesos();
	}
	
	public void cargarAccesos(){
		try {
			long idUsuario = SessionUtil.getIdUsuarioLogeado();
			listaAccesos = administracionON.obtenerInfoAccesos(idUsuario);
		}catch (GeneralException ex) {
			ex.printStackTrace();
		}
	}

	public List<UsuarioAccesoDTO> getListaAccesos() {
		return listaAccesos;
	}
	public void setListaAccesos(List<UsuarioAccesoDTO> listaAccesos) {
		this.listaAccesos = listaAccesos;
	}

}
