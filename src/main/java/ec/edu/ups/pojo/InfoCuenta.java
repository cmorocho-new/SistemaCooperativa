package ec.edu.ups.pojo;

import java.util.Date;

public class InfoCuenta {

    private Long idCuenta;
    private String numeroCuenta;
    private String identifificaioPropietario;
    private String nombrePropietrio;
    private String correoPropietario;
    private Date ultimaTransaccion;
    private double saldoActual;

    public InfoCuenta(){

    }

    public InfoCuenta(Long idCuenta, String numeroCuenta, double saldoActual) {
        this.idCuenta = idCuenta;
        this.numeroCuenta = numeroCuenta;
        this.saldoActual = saldoActual;
    }

    public InfoCuenta(Long idCuenta, String numeroCuenta, String identifificaioPropietario, String nombrePropietrio, String correoPropietario, Date ultimaTransaccion) {
        this.idCuenta = idCuenta;
        this.numeroCuenta = numeroCuenta;
        this.identifificaioPropietario = identifificaioPropietario;
        this.nombrePropietrio = nombrePropietrio;
        this.correoPropietario = correoPropietario;
        this.ultimaTransaccion = ultimaTransaccion;
    }

    public InfoCuenta(String numeroCuenta, String nombrePropietrio, Date ultimaTransaccion, double saldoActual) {
        this.numeroCuenta = numeroCuenta;
        this.nombrePropietrio = nombrePropietrio;
        this.ultimaTransaccion = ultimaTransaccion;
        this.saldoActual = saldoActual;
    }

    public Long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getIdentifificaioPropietario() {
        return identifificaioPropietario;
    }

    public void setIdentifificaioPropietario(String identifificaioPropietario) {
        this.identifificaioPropietario = identifificaioPropietario;
    }

    public String getNombrePropietrio() {
        return nombrePropietrio;
    }

    public void setNombrePropietrio(String nombrePropietrio) {
        this.nombrePropietrio = nombrePropietrio;
    }

    public String getCorreoPropietario() {
        return correoPropietario;
    }

    public void setCorreoPropietario(String correoPropietario) {
        this.correoPropietario = correoPropietario;
    }

    public Date getUltimaTransaccion() {
        return ultimaTransaccion;
    }

    public void setUltimaTransaccion(Date ultimaTransaccion) {
        this.ultimaTransaccion = ultimaTransaccion;
    }

    public double getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(double saldoActual) {
        this.saldoActual = saldoActual;
    }
}
