package ec.edu.ups.pojo;

import java.util.Date;

public class DetalleTransaccion {

    private Date fechaEminision;
    private String transacion;
    private String descripcion;
    private double debe;
    private double haber;
    private double saldo;

    public DetalleTransaccion(Date fechaEminision, String transacion, String descripcion, double debe, double haber, double saldo) {
        this.fechaEminision = fechaEminision;
        this.transacion = transacion;
        this.descripcion = descripcion;
        this.debe = debe;
        this.haber = haber;
        this.saldo = saldo;
    }

    public Date getFechaEminision() {
        return fechaEminision;
    }

    public void setFechaEminision(Date fechaEminision) {
        this.fechaEminision = fechaEminision;
    }

    public String getTransacion() {
        return transacion;
    }

    public void setTransacion(String transacion) {
        this.transacion = transacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getDebe() {
        return debe;
    }

    public void setDebe(double debe) {
        this.debe = debe;
    }

    public double getHaber() {
        return haber;
    }

    public void setHaber(double haber) {
        this.haber = haber;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
