package ec.edu.ups.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Modelo DTO para los atribuitos de la tabla gnl_clientes
 * @author Nicolas Añazco
 *
 */
@Entity
@Table(name = "gnl_clientes")
@SuppressWarnings("serial")
public class ClienteDTO extends AuditoriaDTO implements Serializable {

	@Column(name= "identificacion",  nullable = false, length = 13, unique = true)
	private String identificacion;
	
	@Column(name= "nombres",  nullable = false, length = 100)
	private String nombres;
	
	@Column(name= "apellidos", nullable = false, length = 100)
	private String apellidos;
	
	@Column(name= "correo", nullable = false, length = 100)
	private String correo;
	
	@Column(name= "fecha_nacimiento", nullable = false)
	private Date fechaNacimiento;
	
	@Column(name= "telefono", nullable = false, length = 10)
	private String telefono;
	
	@Column(name= "celular", nullable = false, length = 10)
	private String celular;
	
	@Column(name= "calle_principal", nullable = false, length = 200)
	private String callePrincipal;
	
	@Column(name= "calle_secuntaria", length = 200)
	private String calleSecundaria;

	@ManyToOne
	@JoinColumn(name = "estado_civil_id", referencedColumnName = "id", nullable = false)
	private CatalogoDTO estadoCivil;
	
	@ManyToOne
	@JoinColumn(name = "genero_id", referencedColumnName = "id", nullable = false)
	private CatalogoDTO genero;
	
	@ManyToOne
	@JoinColumn(name = "tipo_identificacion_id", referencedColumnName = "id", nullable = false)
	private CatalogoDTO tipoIdentificacion;

	public ClienteDTO() {
	}

	public ClienteDTO(long id) {
		super.setId(id);
	}

	public String nombreCompleto() {
		return nombres.toUpperCase() + " " + apellidos.toUpperCase();
	}
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getCallePrincipal() {
		return callePrincipal;
	}
	public void setCallePrincipal(String callePrincipal) {
		this.callePrincipal = callePrincipal;
	}
	public String getCalleSecundaria() {
		return calleSecundaria;
	}
	public void setCalleSecundaria(String calleSecundaria) {
		this.calleSecundaria = calleSecundaria;
	}
	public CatalogoDTO getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(CatalogoDTO estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	public CatalogoDTO getGenero() {
		return genero;
	}
	public void setGenero(CatalogoDTO genero) {
		this.genero = genero;
	}
	public CatalogoDTO getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	public void setTipoIdentificacion(CatalogoDTO tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public String getNombreCompleto(){
		return nombres + " " + apellidos;
	}
	@Override
	public String toString() {
		return "[ " + identificacion + " ] " + nombres + " " + apellidos;
	}

}
