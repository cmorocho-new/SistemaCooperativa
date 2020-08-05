package ec.edu.ups.dto;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Modelo DTO para los atribuitos de gnl_tipo_catalogos
 * @author Nicolas Añazco
 *
 */

@Entity
@Table(name = "gnl_tipo_catalogos")
@SuppressWarnings("serial")
public class TipoCatalogoDTO extends AuditoriaDTO implements Serializable{
	
	@Column(name = "codigo", nullable = false, unique= true, length = 20)
	private String codigo;
	
	@Column(name = "nombre", nullable = false, length = 100)
	private String nombre;
	
	@Column(name = "descripcion", length = 200)
	private String descripcion;

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
	
}
