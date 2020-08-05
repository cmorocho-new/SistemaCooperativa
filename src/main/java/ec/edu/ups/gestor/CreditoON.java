package ec.edu.ups.gestor;

import ec.edu.ups.common.GeneralException;
import ec.edu.ups.consumes.ApiServicioAnalisisDeDatos;
import ec.edu.ups.dao.credito.CreditoDAO;
import ec.edu.ups.dao.credito.SolicitudCreditoDAO;
import ec.edu.ups.dto.CatalogoDTO;
import ec.edu.ups.dto.ClienteDTO;
import ec.edu.ups.dto.credito.CreditoDTO;
import ec.edu.ups.dto.credito.CuotaCreditoDTO;
import ec.edu.ups.dto.credito.SolicitudCreditoDTO;
import ec.edu.ups.dto.transaccion.ComprobanteDTO;
import ec.edu.ups.pojo.*;
import ec.edu.ups.trasient.ResultadoTrasient;
import ec.edu.ups.utils.DateUtil;
import ec.edu.ups.utils.MailUtil;
import ec.edu.ups.utils.MathUtil;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.MessagingException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Stateless
public class CreditoON {

    @Inject
    private GeneralON generalON;
    @Inject
    private CreditoDAO creditoDAO;
    @Inject
    private TransaccionesON transaccionesON;
    @Inject
    private SolicitudCreditoDAO solicitudCreditoDAO;
    @Inject
    private ApiServicioAnalisisDeDatos apiServicioAnalisisDeDatos;
    

    /**
     * Método para guargar una solicitud
     */
    public void guardarSolicitud (SolicitudCreditoDTO solicitudCreditoDTO, ExtraInfoSolicitudCredito extraInfo,
                                  long idUsuario) throws GeneralException {
        // Obtiene el id de la cuenta
        long idCliente = generalON.obtenerIdCliente(idUsuario);
        // Validacion proposito credito
        String codigoTipoProposito = solicitudCreditoDTO.getPropositoCredito().getCodigo();
        if(codigoTipoProposito.equals("TIP-PRO-OTR")){
            if(solicitudCreditoDTO.getDescripcionMotivo().isEmpty()){
                throw new GeneralException("ERROR: Debe ingresar el motivo del credito");
            }
        }
        // Validacion de monto y plazo
        if(solicitudCreditoDTO.getPlazoMeses() == 0){
            throw  new GeneralException("ERROR: El plazo de credito debe ser mayor a 0");
        }
        if(solicitudCreditoDTO.getMontoCredito() == 0){
            throw  new GeneralException("ERROR: El monto de credito debe ser mayor a 0");
        }
        // Verifcia el tipo de empleo
        String codigoTipoEmpelo = solicitudCreditoDTO.getTipoEmpleo().getCodigo();
        if (codigoTipoEmpelo.equals("TIP-EMP-DES") || codigoTipoEmpelo.equals("TIP-EMP-JUB")){
            solicitudCreditoDTO.setTiempoEmpleo(0);
            solicitudCreditoDTO.setTrabajadorExtrangero(false);
        }else{
            if(solicitudCreditoDTO.getTiempoEmpleo() == 0){
                throw  new GeneralException("ERROR: El tiempo de empleo debe ser mayor a 0");
            }
        }
        // Validacion el tipo de vivienda
        String codigoTipoVivienda = solicitudCreditoDTO.getTipoVienda().getCodigo();
        if (codigoTipoVivienda.equals("TIP-VIV-GRA") || codigoTipoVivienda.equals("TIP-VIV-ALQ")){
            solicitudCreditoDTO.setAvaluoVivienda(0);
        }else{
            if(solicitudCreditoDTO.getAvaluoVivienda() == 0){
                throw  new GeneralException("ERROR: El avaluo de vivienda debe ser mayor a 0");
            }
        }
        // Validacion la tasa de pago
        double diferencia = solicitudCreditoDTO.getTotalIngresos() - solicitudCreditoDTO.getTotalEgresos();
        if (diferencia > 0){
            double tasaPago = diferencia*100/solicitudCreditoDTO.getTotalIngresos();
            solicitudCreditoDTO.setTasaPago(MathUtil.roundAvoid(tasaPago, 2));
        }else{
            solicitudCreditoDTO.setTasaPago(0);
        }
        // Valida los garantes
        try {
            long idgGaranteP = generalON.obtenerIdCliente(extraInfo.getNumeroCedulaGP());
            solicitudCreditoDTO.setGarantePrincipal(new ClienteDTO(idgGaranteP));
        }catch (GeneralException ex){
            throw new GeneralException("ERROR: Garante principal no se encuentra registrado");
        }
        if(!extraInfo.getNumeroCedulaGS().isEmpty()){
            try {
                long idgGaranteS = generalON.obtenerIdCliente(extraInfo.getNumeroCedulaGS());
                solicitudCreditoDTO.setGaranteSecundario(new ClienteDTO(idgGaranteS));
            }catch (GeneralException ex){
                throw new GeneralException("ERROR: Garante secundario no se encuentra registrado");
            }
        }
        solicitudCreditoDTO.setInterez(0.14);
        // Agrega el cliente a la solicitud
        solicitudCreditoDTO.setCliente(new ClienteDTO(idCliente));
        // Agrega el estado
        long idEstadoSolicitud = generalON.obtenerIdCatalogoPorCodigo("TIP-ESL-PEN");
        solicitudCreditoDTO.setEstado(new CatalogoDTO(idEstadoSolicitud));
        solicitudCreditoDAO.guadarSolicitud(solicitudCreditoDTO, idUsuario);
        // Predice el tipo de cliente
        CompletableFuture.runAsync(() -> {
            try {
                actualizarTipoCliente(solicitudCreditoDTO, idCliente);
            } catch (GeneralException ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Método para predecir el tipo de cliente
     */
    public void actualizarTipoCliente (SolicitudCreditoDTO solicitudCreditoDTO, long idCliente) throws GeneralException {
        List<Object> listaAtributosAnalisis = new ArrayList<>();
        // Obtiene los datos del cliente
        InfoCliente infoCliente = generalON.obtenerInfoCliente(idCliente);
        listaAtributosAnalisis.add(infoCliente.getIdentificion()); // agrega el dni
        listaAtributosAnalisis.add(solicitudCreditoDTO.getPlazoMeses()); // agrega meses plazo
        // agrega historial credito
        listaAtributosAnalisis.add("A30");
        solicitudCreditoDTO.setHistorialCredito(new CatalogoDTO(76));
        listaAtributosAnalisis.add(solicitudCreditoDTO.getPropositoCredito().getValor()); // agrega el proposito
        listaAtributosAnalisis.add(solicitudCreditoDTO.getMontoCredito()); // agrega el monto de credito
        // agrega saldo de la cuenta
        solicitudCreditoDTO.setSaldoActual(infoCliente.getSaldoActual());
        if (infoCliente.getSaldoActual() >= 0 && infoCliente.getSaldoActual() < 500) {
            listaAtributosAnalisis.add("A61");
        } else if (infoCliente.getSaldoActual() >= 500 && infoCliente.getSaldoActual() < 100) {
            listaAtributosAnalisis.add("A62");
        }else if (infoCliente.getSaldoActual() >= 1000 && infoCliente.getSaldoActual() < 1500) {
            listaAtributosAnalisis.add("A63");
        }else{
            listaAtributosAnalisis.add("A64");
        }
        // agrega el tiempo de empleo
        if (solicitudCreditoDTO.getTiempoEmpleo() == 0){
            listaAtributosAnalisis.add("A71");
        }else if (solicitudCreditoDTO.getTiempoEmpleo() < 1){
            listaAtributosAnalisis.add("A72");
        }else if (solicitudCreditoDTO.getTiempoEmpleo() >= 1 && solicitudCreditoDTO.getTiempoEmpleo() < 4){
            listaAtributosAnalisis.add("A73");
        }else if (solicitudCreditoDTO.getTiempoEmpleo() >= 4 && solicitudCreditoDTO.getTiempoEmpleo() < 7){
            listaAtributosAnalisis.add("A74");
        }else{
            listaAtributosAnalisis.add("A75");
        }
        listaAtributosAnalisis.add(solicitudCreditoDTO.getTasaPago()); // agrega tasa pago
        // Verifica el tipo de estdo civil y genero
        if (infoCliente.getGenero().equals("GEN-MAS") && infoCliente.getEstadoCivil().equals("EST-DIV")){
            listaAtributosAnalisis.add("A91");
        }else if (infoCliente.getGenero().equals("GEN-FEM") &&
                (infoCliente.getEstadoCivil().equals("EST-DIV") || infoCliente.getEstadoCivil().equals("EST-CAS") ||
                        infoCliente.getEstadoCivil().equals("EST-VIU"))){
            listaAtributosAnalisis.add("A92");
        }else if (infoCliente.getGenero().equals("GEN-MAS") && infoCliente.getEstadoCivil().equals("EST-SOL")){
            listaAtributosAnalisis.add("A93");
        }else if (infoCliente.getGenero().equals("GEN-MAS") &&
                (infoCliente.getEstadoCivil().equals("EST-DIV") || infoCliente.getEstadoCivil().equals("EST-VIU"))){
            listaAtributosAnalisis.add("A94");
        }else {
            listaAtributosAnalisis.add("A95");
        }
        // agrega el tipo garante
        String codigoTipoGarante;
        long totalGarantes = creditoDAO.obtenerTotalGarante(idCliente);
        if (totalGarantes == 0){
            codigoTipoGarante = "TIP-GAR-NIN";
            listaAtributosAnalisis.add("A101");
        }else{
            codigoTipoGarante = "TIP-GAR-GAR";
            listaAtributosAnalisis.add("A103");
        }
        long idTipoGarante = generalON.obtenerIdCatalogoPorCodigo(codigoTipoGarante);
        solicitudCreditoDTO.setTipoGarante(new CatalogoDTO(idTipoGarante));
        listaAtributosAnalisis.add(solicitudCreditoDTO.getAvaluoVivienda()); // agrega el avluo vivienda
        listaAtributosAnalisis.add("A121"); // agrega el tipo activo
        // agrega la edad
        solicitudCreditoDTO.setEdad(DateUtil.obtenerEdad(infoCliente.getFechaNacimiento()));
        listaAtributosAnalisis.add(solicitudCreditoDTO.getEdad());
        listaAtributosAnalisis.add(solicitudCreditoDTO.getTipoVienda().getValor()); // agrega el tipo vivienda
        // agrega total creditos
        long totalCreditos = creditoDAO.obtenerTotalCreditosCliente(infoCliente.getIdCliente());
        solicitudCreditoDTO.setCantidadCreditos((int) totalCreditos);
        listaAtributosAnalisis.add(totalCreditos);
        listaAtributosAnalisis.add(solicitudCreditoDTO.getTipoEmpleo().getValor()); // agrega el tipo empleo
        // agrega trabajor extranjero
        if (solicitudCreditoDTO.isTrabajadorExtrangero()){
            listaAtributosAnalisis.add("A201");
        }else{
            listaAtributosAnalisis.add("A202");
        }
        // Manda a precedir el tipo cliente
        ResultadoTrasient respuesta = apiServicioAnalisisDeDatos.apiServicioGuardarDatosCliente(listaAtributosAnalisis);
        if (respuesta.getResultado().equals(new BigDecimal("1"))) {
            // Agrega el tipo cliente
            long idTipoCliente = generalON.obtenerIdCatalogoPorCodigo("TIP-CLI-BUE");
            solicitudCreditoDTO.setEstado(new CatalogoDTO(idTipoCliente));
        }else if (respuesta.getResultado().equals(new BigDecimal("2"))) {
            // Agrega el tipo cliente
            long idTipoCliente = generalON.obtenerIdCatalogoPorCodigo("TIP-CLI-MAL");
            solicitudCreditoDTO.setEstado(new CatalogoDTO(idTipoCliente));
        }
        // Actualiza el credito
        solicitudCreditoDAO.actualizarSolicitud(solicitudCreditoDTO);
    }

    /**
     * Aprueba una solicitud de credito
     * @param infoSolisitudCredito
     * @param idUsuario
     * @throws GeneralException
     */
    public void aprobarSolicitudCredito(InfoSolisitudCredito infoSolisitudCredito, long idUsuario)
            throws GeneralException {
        // Genera la transaccion de credito
        double montoCredito = infoSolisitudCredito.getMontoCredito();
        int plazoCredito = infoSolisitudCredito.getPlazoCredito();
        long idComprobante = transaccionesON.transaccionAutomaticaCredito(
                infoSolisitudCredito.getIdentificacion(), montoCredito
        );
        // Crea el detalle del credito
        CreditoDTO creditoDTO = new CreditoDTO();
        creditoDTO.setFechaInicio(DateUtil.sumarMesesFecha(new Date(), 1));
        creditoDTO.setFechaVencimiento(DateUtil.sumarMesesFecha(new Date(), plazoCredito + 1));
        creditoDTO.setSaldo(montoCredito);
        creditoDTO.setComprobante(new ComprobanteDTO(idComprobante));
        creditoDTO.setSolicitud(new SolicitudCreditoDTO(infoSolisitudCredito.getIdSolicitud()));
        // Crea las cuotas de pago
        double interezMensual = MathUtil.roundAvoid(infoSolisitudCredito.getInteres() / 12, 2);
        double pagoMensual = montoCredito * Math.abs(interezMensual / (1 - Math.pow(1 + interezMensual, plazoCredito)));
        pagoMensual = MathUtil.roundAvoid(pagoMensual, 2);
        long idEstadoPendiente = generalON.obtenerIdCatalogoPorCodigo("TIP-PAG-CUO-PEN");
        List<CuotaCreditoDTO> listaCuotas = new ArrayList<>();
        for (int i = 1; i <= plazoCredito; i++) {
            CuotaCreditoDTO cuota = new CuotaCreditoDTO();
            cuota.setCredito(creditoDTO);
            cuota.setFechaPlazo(DateUtil.sumarMesesFecha(new Date(), i));
            cuota.setValor(pagoMensual);
            cuota.setSaldo(pagoMensual);
            double interezCuota = montoCredito * interezMensual;
            cuota.setInterez(MathUtil.roundAvoid(interezCuota, 2));
            double amortizacion = pagoMensual - interezCuota;
            cuota.setAmortizacion(MathUtil.roundAvoid(amortizacion, 2));
            montoCredito -= amortizacion;
            cuota.setSaldoFinal(MathUtil.roundAvoid(montoCredito, 2));
            cuota.setEstadoCredito(new CatalogoDTO(idEstadoPendiente));
            listaCuotas.add(cuota);
        }
        creditoDTO.setCuotas(listaCuotas);
        creditoDAO.guardarCredito(creditoDTO, idUsuario);
        // Actualiza el estado de la solicitud
        long idEstado = generalON.obtenerIdCatalogoPorCodigo("TIP-ESL-APO");
        solicitudCreditoDAO.actualizarEstadoSolicitud(infoSolisitudCredito.getIdSolicitud(), idEstado,
                infoSolisitudCredito.getObservacion()
        );
        // Envia el correo de aprobacion
        enviarCorreoAprobacionCredito(infoSolisitudCredito, listaCuotas);
    }

    /**
     * Rechaza una solicitud de credito
     * @param infoSolisitudCredito
     * @throws GeneralException
     */
    public void rechazarSolicitudCredito(InfoSolisitudCredito infoSolisitudCredito) throws GeneralException {
        if (infoSolisitudCredito.getObservacion().isEmpty()){
            throw new GeneralException("No se puede rechazar la solicitud sin dar una observacion");
        }
        // Actualiza el estado de la solicitud
        long idEstado = generalON.obtenerIdCatalogoPorCodigo("TIP-ESL-REC");
        solicitudCreditoDAO.actualizarEstadoSolicitud(infoSolisitudCredito.getIdSolicitud(), idEstado,
                infoSolisitudCredito.getObservacion()
        );
        // Envia el correo de rechazo
        enviarCorreoRechazoCredito(infoSolisitudCredito);
    }

    /**
     * Envia el correo de aprobacion de una solicitud de credito
     * @param solicitudCreditoDTO
     * @param listaCuotas
     */
    public void enviarCorreoAprobacionCredito(InfoSolisitudCredito solicitudCreditoDTO, List<CuotaCreditoDTO> listaCuotas){
        String asuntoMensaje = "Aprobacion de la solicitud credito";
        StringBuilder cuerpoMensaje = new StringBuilder("Estimado(a): <strong>")
                .append(solicitudCreditoDTO.getSolicitante()).append("</strong><br>")
                .append("SISTEMA FINANCIERO notifica a Ud. la siguiente informacion <br>")
                .append("========================================================== <br>")
                .append("<table style=\"width:100%\">").append("<tr>")
                .append("<th width=\"100px\">OREDEN</th>")
                .append("<th width=\"200px\">FECHA PAGO</th>")
                .append("<th width=\"150px\">CUOTA</th>")
                .append("<th width=\"150px\">INTEREZ</th>")
                .append("<th width=\"150px\">AMORTIZACION</th>")
                .append("<th width=\"150px\">SALDO</th>").append("</tr>");
        for (int i = 0; i < listaCuotas.size(); i++) {
            cuerpoMensaje.append("<tr><td width=\"100px\">").append(i+1).append("</td>")
                    .append("<td width=\"200px\">").append(listaCuotas.get(i).getFechaPlazo()).append("</td>")
                    .append("<td width=\"150px\">").append(listaCuotas.get(i).getValor()).append("</td>")
                    .append("<td width=\"150px\">").append(listaCuotas.get(i).getInterez()).append("</td>")
                    .append("<td width=\"150px\">").append(listaCuotas.get(i).getAmortizacion()).append("</td>")
                    .append("<td width=\"150px\">").append(listaCuotas.get(i).getSaldoFinal()).append("</td></tr>");
        }
        cuerpoMensaje.append("</table> <br>========================================================== <br>");
        CompletableFuture.runAsync(() -> {
            try {
                MailUtil.enviarCorreo(solicitudCreditoDTO.getCorreo(), asuntoMensaje, cuerpoMensaje.toString());
            } catch (MessagingException ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Envia el correo de rechazo de una solicitud de credito
     * @param solicitudCreditoDTO
     */
    public void enviarCorreoRechazoCredito(InfoSolisitudCredito solicitudCreditoDTO){
        String asuntoMensaje = "Rechazo de la solicitud de credito";
        StringBuilder cuerpoMensaje = new StringBuilder("Estimado(a): <strong>")
                .append(solicitudCreditoDTO.getSolicitante()).append("</strong><br>")
                .append("SISTEMA FINANCIERO notifica a Ud. la siguiente informacion <br>")
                .append("========================================================== <br>")
                .append("<strong> DETALLE: </strong> ").append(solicitudCreditoDTO.getObservacion()).append("<br>")
                .append("========================================================== <br>");
        CompletableFuture.runAsync(() -> {
            try {
                MailUtil.enviarCorreo(solicitudCreditoDTO.getCorreo(), asuntoMensaje, cuerpoMensaje.toString());
            } catch (MessagingException ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Obtiene la informacion de la cuota
     * @param idCuotaCredito
     * @return
     * @throws GeneralException
     */
    public InfoCuota obtenerInfoCuota(long idCuotaCredito) throws GeneralException {
        return creditoDAO.obtenerInfoCuota(idCuotaCredito);
    }

    /**
     * Obtiene los pgos anteriores de una cuota
     * @param idCuotaCredito
     * @return
     * @throws GeneralException
     */
    public List<InfoPagoCredito> obtenerPagosCuota(long idCuotaCredito) throws GeneralException {
        return creditoDAO.obtenerPagosCuota(idCuotaCredito);
    }

    /**
     * Obtiene las cuotas de un credito
     * @param idCredito
     * @return
     * @throws GeneralException
     */
    public List<CuotaCreditoDTO> obtenerCuotasCredito(long idCredito) throws GeneralException {
        return creditoDAO.obtenerCuotasCredito(idCredito);
    }

    /**
     * Obtiene la informacion de una solicitud
     * @param idSolicitud
     * @return
     * @throws GeneralException
     */
    public SolicitudCreditoDTO obtenerInfoSolicitudCredito(long idSolicitud) throws GeneralException {
        return solicitudCreditoDAO.obtenerInfoSolicitudCredito(idSolicitud);
    }

    /**
     * Obtiene la info de las solicitudes de los cleintes
     * @param idEstado
     * @return
     * @throws GeneralException
     */
    public List<InfoSolisitudCredito> obtenerSolicitudesCredito(long idEstado) throws GeneralException {
        return solicitudCreditoDAO.obtenerSolicitudesCredito(idEstado);
    }

    /**
     * Obtiene los creditos aprobados de un cliente
     * @param idUsuario
     * @return
     * @throws GeneralException
     */
    public List<InfoCredito> obtenerListaCreditoPorCliente(Long idUsuario) throws GeneralException {
        long idCliente = generalON.obtenerIdCliente(idUsuario);
    	return creditoDAO.obtenerListaCreditosPorCliente(idCliente);
    }
    
    public byte[] convertirArchivo(InputStream in) throws IOException {

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		byte[] buffer = new byte[1024];
		int len;

		// read bytes from the input stream and store them in buffer
		while ((len = in.read(buffer)) != -1) {
			// write bytes from the buffer into output stream
			os.write(buffer, 0, len);
		}

		return os.toByteArray();
	}
}
