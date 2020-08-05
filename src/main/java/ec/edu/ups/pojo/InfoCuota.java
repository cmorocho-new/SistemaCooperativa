package ec.edu.ups.pojo;

public class InfoCuota {

    private long idCuota;
    private long idCuenta;
    private double saldoCuenta;
    private double saldoCuota;
    private String estadoCuota;
    private double valorPago;

    public InfoCuota(long idCuota, long idCuenta, double saldoCuenta, double saldoCuota, String estadoCuota) {
        this.idCuota = idCuota;
        this.idCuenta = idCuenta;
        this.saldoCuenta = saldoCuenta;
        this.saldoCuota = saldoCuota;
        this.estadoCuota = estadoCuota;
    }

    public long getIdCuota() {
        return idCuota;
    }

    public void setIdCuota(long idCuota) {
        this.idCuota = idCuota;
    }

    public long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public double getSaldoCuenta() {
        return saldoCuenta;
    }

    public void setSaldoCuenta(double saldoCuenta) {
        this.saldoCuenta = saldoCuenta;
    }

    public double getSaldoCuota() {
        return saldoCuota;
    }

    public void setSaldoCuota(double saldoCuota) {
        this.saldoCuota = saldoCuota;
    }

    public String getEstadoCuota() {
        return estadoCuota;
    }

    public void setEstadoCuota(String estadoCuota) {
        this.estadoCuota = estadoCuota;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }
}
