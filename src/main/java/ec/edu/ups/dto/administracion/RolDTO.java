package ec.edu.ups.dto.administracion;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ec.edu.ups.dto.AuditoriaDTO;

/**
 * Creacion de modelo para adm_roles
 * @author Damián Sumba
 *
 */
@Entity
@Table(name = "adm_roles")
@SuppressWarnings("serial")
public class RolDTO extends AuditoriaDTO implements Serializable{
	
	
	@Column(name = "codigo", nullable = false, length = 20, unique = true)
	private String codigo;
	
	@Column(name = "nombre", nullable = false, length = 100)
	private String nombre;
	
	@Column(name = "descripcion", length = 200)
	private String descripcion;

	public RolDTO(){

	}

	public RolDTO(long id, String codigo, String nombre){
		super.setId(id);
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "[ " + codigo + " ] " + nombre;
	}

}
