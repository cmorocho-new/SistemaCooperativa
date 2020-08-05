package ec.edu.ups.dto.administracion;

import ec.edu.ups.dto.AuditoriaDTO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Creacion de modelo para la tabla adm_usuario_accesos
 * 
 * @author Nicolas Anazco
 *
 */

@Entity
@Table(name = "adm_usuario_accesos")
@SuppressWarnings("serial")
public class UsuarioAccesoDTO extends AuditoriaDTO implements Serializable{

	
	@Column(name= "ubicacion",  nullable = false, length = 500)
	private String ubicacion;
	
	@Column(name= "dispositivo",  nullable = false, length = 500)
	private String Dispositivo;
	
	@Column(name= "acceso")
	private boolean acceso;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id")
	private UsuarioDTO usuarioDTO;

	public UsuarioAccesoDTO(){

	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getDispositivo() {
		return Dispositivo;
	}

	public void setDispositivo(String dispositivo) {
		Dispositivo = dispositivo;
	}

	public boolean getAcceso() {
		return acceso;
	}

	public void setAcceso(boolean acceso) {
		this.acceso = acceso;
	}

	public UsuarioDTO getUsuarioDTO() {
		return usuarioDTO;
	}

	public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}
}
