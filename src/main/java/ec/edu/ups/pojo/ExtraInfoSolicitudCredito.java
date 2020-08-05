package ec.edu.ups.pojo;

public class ExtraInfoSolicitudCredito {


    private String numeroCedulaGP;
    private String numeroCedulaGS;
    private boolean[] activos;

    public ExtraInfoSolicitudCredito(int lenght) {
        this.activos = new boolean[lenght];
    }

    public String getNumeroCedulaGP() {
        return numeroCedulaGP;
    }

    public void setNumeroCedulaGP(String numeroCedulaGP) {
        this.numeroCedulaGP = numeroCedulaGP;
    }

    public String getNumeroCedulaGS() {
        return numeroCedulaGS;
    }

    public void setNumeroCedulaGS(String numeroCedulaGS) {
        this.numeroCedulaGS = numeroCedulaGS;
    }

    public boolean[] getActivos() {
        return activos;
    }

    public void setActivos(boolean[] activos) {
        this.activos = activos;
    }
}
