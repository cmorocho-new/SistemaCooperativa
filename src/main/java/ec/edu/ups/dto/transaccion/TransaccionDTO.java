package ec.edu.ups.dto.transaccion;

import ec.edu.ups.dto.AuditoriaDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Creacion de modelo para la tabla trn_transacciones
 */
@Entity
@Table(name = "trn_transacciones")
@SuppressWarnings("serial")
public class TransaccionDTO extends AuditoriaDTO implements Serializable {

    @Column(name="codigo", nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(name="nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name="descripcion", length = 200)
    private String descripcion;

    @Column(name="numero_siguiente",  nullable = false, length = 10)
    private Long numeroSiguiente;

    @Column(name="numero_sumador",  nullable = false, length = 10)
    private Long numeroSumador;

    public TransaccionDTO() {
    	
    }
    
    public TransaccionDTO(Long id){
        super.setId(id);
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

    public Long getNumeroSiguiente() {
        return numeroSiguiente;
    }

    public void setNumeroSiguiente(Long numeroSiguiente) {
        this.numeroSiguiente = numeroSiguiente;
    }

    public Long getNumeroSumador() {
        return numeroSumador;
    }

    public void setNumeroSumador(Long numeroSumador) {
        this.numeroSumador = numeroSumador;
    }
}
