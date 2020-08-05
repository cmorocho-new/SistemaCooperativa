package ec.edu.ups.dto;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Modelo DTO para los atribuitos de la tabla gnl_catalogos
 * @author Nicolas Añazco
 *
 */

@Entity
@Table(name = "gnl_catalogos")
@SuppressWarnings("serial")
public class CatalogoDTO extends AuditoriaDTO implements Serializable {

	@Column(name= "codigo",  nullable = false, unique= true, length = 20)
	private String codigo;
	
	@Column(name= "nombre",  nullable = false, length = 100)
	private String nombre;
	
	@Column(name = "valor", length = 100)
	private String valor;
	
	@Column(name = "descripcion",  length = 200)
	private String descripcion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_catalogo_id", referencedColumnName = "id")
	private TipoCatalogoDTO tipoCatalogo;

	public CatalogoDTO () {

	}
	public CatalogoDTO (long id) {
		super.setId(id);
	}

	public CatalogoDTO (long id, String nombre){
		super.setId(id);
		this.nombre = nombre;
	}

	public CatalogoDTO (long id, String codigo, String nombre){
		super.setId(id);
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public CatalogoDTO (long id, String codigo, String nombre, String valor){
		super.setId(id);
		this.codigo = codigo;
		this.nombre = nombre;
		this.valor = valor;
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

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public TipoCatalogoDTO getTipoCatalogo() {
		return tipoCatalogo;
	}

	public void setTipoCatalogo(TipoCatalogoDTO tipoCatalogo) {
		this.tipoCatalogo = tipoCatalogo;
	}

	@Override
	public String toString() {
		return "( " + codigo + " ) " + nombre;
	}

}
