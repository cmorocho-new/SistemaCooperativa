package ec.edu.ups.trasient;

import java.io.Serializable;

public class ResultadoTrasient implements Serializable {

    private String codigo;
    private Object resultado;

    public ResultadoTrasient (String codigo, Object resultado ){
        this.codigo = codigo;
        this.resultado = resultado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Object getResultado() {
        return resultado;
    }

    public void setResultado(Object resultado) {
        this.resultado = resultado;
    }
}
