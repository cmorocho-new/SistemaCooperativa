package ec.edu.ups.dto;

import ec.edu.ups.dto.administracion.UsuarioDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "gnl_cuentas")
@SuppressWarnings("serial")
public class CuentaDTO extends AuditoriaDTO implements Serializable {

    @Column(name="numero", nullable = false, unique = true, length = 20)
    private String numero;

    @Column(name="sado_actual", nullable = false, length = 10, scale = 2)
    private double saldoActual;

    @Column(name="ultima_transaccion")
    private Date ultimaTransaccion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
    private UsuarioDTO usuario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = false)
    private ClienteDTO cliente;


    public CuentaDTO(){
    }

    public CuentaDTO(Long id){
        super.setId(id);
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(double saldoActual) {
        this.saldoActual = saldoActual;
    }

    public Date getUltimaTransaccion() {
        return ultimaTransaccion;
    }

    public void setUltimaTransaccion(Date ultimaTransaccion) {
        this.ultimaTransaccion = ultimaTransaccion;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }
}
