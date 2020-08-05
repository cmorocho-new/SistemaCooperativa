package ec.edu.ups.servicios;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import ec.edu.ups.common.GeneralException;
import ec.edu.ups.gestor.TransaccionesON;

@Path("/transacciones")
public class TransaccionesREST {

    @Inject
    private TransaccionesON transaccionesON;

    @POST
	@Path("/transferencia")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String transferencia(@FormParam("cuentaOrigen") String cuentaOrigen,@FormParam("cuentaDestino") String cuentaDestino ,@FormParam("cantidad") String cantidad) {
		try {
			transaccionesON.transaccionAutomaticaTransaferencia(cuentaOrigen, cuentaDestino, Double.parseDouble(cantidad));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Transferencia Exitosa.....";
	}

    @POST
	@Path("/deposito")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String deposito(@FormParam("cuentaOrigen") String cuenta, @FormParam("cantidad") String cantidad) {
		try {
			transaccionesON.transaccionAutomaticaDeposito(cuenta, Double.parseDouble(cantidad));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Deposito Exitoso.....";
	}

    @POST
	@Path("/retiro")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String retiro(@FormParam("cuentaOrigen") String cuenta, @FormParam("cantidad") String cantidad) {
		try {
			transaccionesON.transaccionAutomaticaRetiro(cuenta, Double.parseDouble(cantidad));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Retiro Exitoso.....";
	}

}





