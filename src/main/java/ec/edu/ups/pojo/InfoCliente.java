package ec.edu.ups.pojo;

import java.util.Date;

public class InfoCliente {

    private long idCliente;
    private String identificion;
    private double saldoActual;
    private String estadoCivil;
    private String genero;
    private Date fechaNacimiento;

    public InfoCliente(long idCliente, String identificion, double saldoActual, String estadoCivil, String genero, Date fechaNacimiento) {
        this.idCliente = idCliente;
        this.identificion = identificion;
        this.saldoActual = saldoActual;
        this.estadoCivil = estadoCivil;
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdentificion() {
        return identificion;
    }

    public void setIdentificion(String identificion) {
        this.identificion = identificion;
    }

    public double getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(double saldoActual) {
        this.saldoActual = saldoActual;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
