package ec.edu.ups.consumes;

import ec.edu.ups.trasient.ResultadoTrasient;

import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import java.util.List;

/**
 *
 * @author Carlos
 */
@Stateless
public class ApiServicioAnalisisDeDatos {
    
    private String API_SERIVICIO_PREDECIR_TIPO_CLIENTE = "http://127.0.0.1:5000/predecir_tipo_cliente/";
    private String API_SERIVICIO_AGREAGR_DATOS_CLIENTE = "http://127.0.0.1:5000/guardar_datos_cliente/";


    public ResultadoTrasient apiServicioPredecirTipoCliente(String identificacion) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(API_SERIVICIO_PREDECIR_TIPO_CLIENTE + "/" + identificacion + "/");
        ResultadoTrasient respuesta = target.request().get(ResultadoTrasient.class);
        client.close();
        return respuesta;
    }

    public ResultadoTrasient apiServicioGuardarDatosCliente(List<Object> datosCliente) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(API_SERIVICIO_AGREAGR_DATOS_CLIENTE);
        ResultadoTrasient respuesta = target.request().post(Entity.json(datosCliente), ResultadoTrasient.class);
        client.close();
        return respuesta;
    }
}
