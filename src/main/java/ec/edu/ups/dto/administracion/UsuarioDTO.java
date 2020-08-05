package ec.edu.ups.dto.administracion;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import ec.edu.ups.dto.AuditoriaDTO;

/**
 * Creacion de modelo para la tabla adm_usuarios
 * 
 * @author Damián Sumba
 *
 */
@Entity
@Table(name = "adm_usuarios")
@SuppressWarnings("serial")
public class UsuarioDTO extends AuditoriaDTO implements Serializable {

	@Column(name = "nombre",  nullable = false, length = 100)
	private String nombre;
	
	@Column(name = "correo",  nullable = false, length = 100, unique = true)
	private String correo;
	
	@Column(name = "contrasena",  nullable = false, length = 100)
	private String contresena;

	@Column(name = "es_cliente")
	private boolean esCliente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rol_id", referencedColumnName = "id")
	private RolDTO rol;

	public UsuarioDTO (){

	}

	public UsuarioDTO (long id){
		super.setId(id);
	}
	
	public boolean isEsCliente() {
		return esCliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public boolean esCliente() {
		return esCliente;
	}

	public void setEsCliente(boolean esCliente) {
		this.esCliente = esCliente;
	}

	public RolDTO getRol() {
		return rol;
	}

	public void setRol(RolDTO rol) {
		this.rol = rol;
	}

	public String getContresena() {
		return contresena;
	}

	public void setContresena(String contresena) {
		this.contresena = contresena;
	}

	@Override
	public String toString() {
		return "UsuarioDTO [nombre=" + nombre + ", correo=" + correo + ", contresena="
				+ contresena + ", rol=" + rol + "]";
	}

}
