package ec.edu.ups.pojo;

import java.util.Date;

public class InfoPagoCredito {

    private Date fechaPago;
    private long numeroPago;
    private double valorPago;
    private double saldoCuota;

    public InfoPagoCredito(Date fechaPago, long numeroPago, double valorPago, double saldoCuota) {
        this.fechaPago = fechaPago;
        this.numeroPago = numeroPago;
        this.valorPago = valorPago;
        this.saldoCuota = saldoCuota;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public long getNumeroPago() {
        return numeroPago;
    }

    public void setNumeroPago(long numeroPago) {
        this.numeroPago = numeroPago;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public double getSaldoCuota() {
        return saldoCuota;
    }

    public void setSaldoCuota(double saldoCuota) {
        this.saldoCuota = saldoCuota;
    }
}
