package ec.edu.ups.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;


/**
 * Modelo DTO para los atributos auditoria
 * @author Carlos Morocho
 *
 */
@MappedSuperclass
@SuppressWarnings("serial")
public abstract class AuditoriaDTO implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="fecha_registro", nullable = false)
	private Date fechaRegistro;
	
	@Column(name="usuario_registro",  nullable = false)
	private Long usuarioRegistro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Long getUsuarioRegistro() {
		return usuarioRegistro;
	}

	public void setUsuarioRegistro(Long usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}
    
}
