package ec.edu.ups.servicios;

import ec.edu.ups.gestor.TransaccionesON;

import javax.inject.Inject;
import javax.jws.WebService;

@WebService
public class TransaccionesSOAP {

    @Inject
    private TransaccionesON transaccionesON;


    public String transaccionTransferencia(String numeroCuentaOrigen, String numeroCuentaDestino, double cantidad) throws Exception {
        transaccionesON.transaccionAutomaticaTransaferencia(numeroCuentaOrigen, numeroCuentaDestino, cantidad);
        return "Transacion guardada";
    }


    public String transaccionDeposito(String numeroCuenta, double cantidad) throws Exception {
		transaccionesON.transaccionAutomaticaDeposito(numeroCuenta, cantidad);
    	return "Deposito Completo";
    }

	public String transaccionRetiro(String numeroCuenta, double cantidad) throws Exception {
		transaccionesON.transaccionAutomaticaRetiro(numeroCuenta, cantidad);
		return "Retiro";
	}
	
	
}
