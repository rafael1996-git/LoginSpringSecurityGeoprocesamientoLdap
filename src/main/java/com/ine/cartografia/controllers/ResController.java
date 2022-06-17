package com.ine.cartografia.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.uuid.Generators;
import com.ine.cartografia.dao.RequiresCaptcha;
import com.ine.cartografia.entity.Control;
import com.ine.cartografia.entity.ControlGrafica;
import com.ine.cartografia.entity.HelloDto;
import com.ine.cartografia.entity.HelloResponseDto;
import com.ine.cartografia.entity.MultiGrafica;
import com.ine.cartografia.entity.Remesa;
import com.ine.cartografia.entity.User;
import com.ine.cartografia.entity.info;
import com.ine.cartografia.service.UserService;
import com.ine.cartografia.service.ValidateCaptcha;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@RestController
public class ResController {

	public static final String DEFAULT_ERROR_SERVICE = "Ocurrio una exception: ";
	public static final String DEFAULT_SUCCESS_SERVICE = "Se ejecuto correctamente";
	public static final String INFORMACION = "Variables ejecutandose";
	public static final Integer DEFAULT_NO_APP_CONFIG = 601;

	private final Logger logger = LoggerFactory.getLogger(ResController.class);

	@Autowired
	public UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	 @Autowired
	    ValidateCaptcha service;

	    //URL - http://localhost:9001/api/welcome
	    @PostMapping("/welcome")
	    @ResponseStatus(code = HttpStatus.OK)
	    //custom annotation
	    @RequiresCaptcha
	    public HelloResponseDto welcome(@RequestBody final HelloDto dto) {
	        return new HelloResponseDto();
	    }

	@RequestMapping(value = "/remesa", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView RemesaPage(Authentication authentication,HttpServletRequest reques, HttpServletResponse response)  {
		ModelAndView model = new ModelAndView();
		UUID token = Generators.randomBasedGenerator().generate();
		Date objDate = new Date();
		// Mostrar la fecha y la hora usando toString ()
		System.out.println(objDate.toString());
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		objDate.equals(dateFormat.format(objDate));
		System.out.println(dateFormat.format(objDate));
		try {
			
			HttpSession session = reques.getSession();
			String listEntidadesActivas = userService.buscarEntidad();
			int id = (int) session.getAttribute("id");
			int entidad = (int) session.getAttribute("entidad");
			String opj;
			opj = userService.buscarRemesa();
			String anio = opj.substring(0,4);
			String semana = opj.substring(4,6);
			System.out.println(anio);
			System.out.println(semana);
			String valor=session.getAttribute("entidad").toString();
			System.out.println(""+Integer.parseInt(valor));
			System.out.println("buscarEntidad"+listEntidadesActivas);
			List<info> var ;
			if(Integer.parseInt(listEntidadesActivas)<10) {
				var =userService.validate( "0"+listEntidadesActivas, anio, semana);
			}else {
				var =userService.validate( listEntidadesActivas, anio, semana);
			}
			
			System.out.println("-----------antes del if"+""+""+session.getAttribute("entidad").toString()+""+ anio+""+semana);
			
			if (var!=null && !var.isEmpty()&&userService.validate( listEntidadesActivas, anio, semana)!=null) {
				System.out.println("despues del if"+""+""+listEntidadesActivas+""+ anio+""+semana);
					//****************************************************insertamos a la tabla autorizacion
					Remesa opjRemesa = new Remesa();
					opjRemesa.setId_status(1);
					opjRemesa.setFecha_hora(objDate);
					opjRemesa.setToken(token.toString());
					opjRemesa.setId_usuario(id);
					opjRemesa.setEntidad_remesa(opj.toString());
					userService.regisRemesa(opjRemesa);
					
					//****************************************************insertamos a la tabla control
					Control opjControl=new Control();
					
					for (int j = 0; j <=135; j++) {
						opjControl.setEntidad(Integer.parseInt(listEntidadesActivas));
						opjControl.setRemesa(Integer.parseInt(opj.toString()));
						opjControl.setFecha_hora(objDate);
						opjControl.setId_usuario(id);
						opjControl.setId_operacion(j);
						opjControl.setId_status(1);
						userService.register(opjControl);
					}

					System.out
							.println("*************************************************************************[HEADER : ");

					OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(120, TimeUnit.SECONDS)
							.readTimeout(120, TimeUnit.SECONDS)
							.writeTimeout(120, TimeUnit.SECONDS).build();
					HttpUrl.Builder urlBuilder = HttpUrl.parse("http://localhost:8080/GenerarRemesa/dce/GenerarEntidad?").newBuilder();
					urlBuilder.addQueryParameter("entidad", listEntidadesActivas);
					urlBuilder.addQueryParameter("remesa", opj.toString());
					String url = urlBuilder.build().toString();
					Request request = new Request.Builder().url(url).header("Authorization", token.toString()).build();

					try {
						Response respons = client.newCall(request).execute();
					} catch (IOException e) {
						System.err.println("Failed scraping");
						List<User> listaPersonas =userService.listaFiltrada(Integer.parseInt(listEntidadesActivas));
						reques.setAttribute("lista", listaPersonas);
						System.out.println("funcion Remesa no realizada por que no esta levantado el servicio : ");
						model.addObject("mensaje1", "¡"+e.getCause().toString());
						model.setViewName("adminC");
						e.printStackTrace();
					}

					System.out.println(
							"*************************************************************************[HEADER2 : ");

					List<User> listaPersonas =userService.listaFiltrada(Integer.parseInt(listEntidadesActivas));
					reques.setAttribute("lista", listaPersonas);
					model.setViewName("adminC");
					model.addObject("mensajee", "Remesa Realizada Correctamente");
			
				
			}else {				
			System.out.println("funcion Remesa no realizada :else  ");
			List<User> listaPersonas =userService.listaFiltrada(Integer.parseInt(listEntidadesActivas));
			reques.setAttribute("lista", listaPersonas);
			model.addObject("mensaje2", "La remesa no puede procesarse, ya que\r\n"
					+ "				aun no se concluye la carga al SIIRFE.");
			model.setViewName("adminC");
			}
		} catch (Exception e) {
			e.printStackTrace();
			HttpSession session = reques.getSession();
			int entidad = (int) session.getAttribute("entidad");
			List<User> listaPersonas =userService.listaFiltrada(entidad);
			reques.setAttribute("lista", listaPersonas);
			System.out.println("funcion Remesa no realizada :Exception  ");
			model.addObject("mensaje1", "¡"+e.getCause().toString());
			model.setViewName("adminC");
			e.getMessage();
		}
		return model;
	}


	@RequestMapping(value = "/funcionRemesas", method = { RequestMethod.GET })
	@ResponseBody
	public List<ControlGrafica> funcionRemesas(HttpServletRequest reques,
			@RequestParam(required = false) List<String> id) {

		List<ControlGrafica> control = new ArrayList<ControlGrafica>();
		if (!id.isEmpty()) {
			for (String entidad : id) {
				ControlGrafica controlEn = new ControlGrafica();
				UUID token = Generators.randomBasedGenerator().generate();
				Date objDate = new Date();
				logger.info(INFORMACION + objDate.toString());
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				objDate.equals(dateFormat.format(objDate));

				logger.info(INFORMACION + dateFormat.format(objDate));

				try {

					HttpSession session = reques.getSession();
					int idUsuario = (int) session.getAttribute("id");
					String opj;
					opj = userService.buscarRemesa();
					String anio = opj.substring(0, 4);
					String semana = opj.substring(4, 6);

					String informacio = anio.concat(semana).concat(entidad);
					logger.info("datos de remesa: " + informacio);
					List<info> var;
					//esta validacion se mantiene ya que  en las otras bases las app.config se maneja de 1-9 con 0
					if(Integer.parseInt(entidad)<10) {
						var = userService.validate("0"+entidad, anio, semana);
					}else {
						var = userService.validate(entidad, anio, semana);
					}
					
					for (info res : var) {
						if (res.getMsgException() != null) {
							controlEn.setMsgExceptionDao(res.getMsgException());
							logger.error(res.getMsgException());
						}

					}
					logger.info(INFORMACION + entidad + " " + anio + " " + semana);

					if (var != null && !var.isEmpty()) {
						// ****************************************************insertamos a la tabla
						// autorizacion
						Remesa opjRemesa = new Remesa();
						opjRemesa.setId_status(1);
						opjRemesa.setFecha_hora(objDate);
						opjRemesa.setToken(token.toString());
						opjRemesa.setId_usuario(idUsuario);
						opjRemesa.setEntidad_remesa(opj.toString());
						userService.regisRemesa(opjRemesa);
						// ****************************************************insertamos a la tabla
						// control
						Control opjControl = new Control();

						for (int j = 0; j <= 135; j++) {
							opjControl.setEntidad(Integer.parseInt(entidad));
							opjControl.setRemesa(Integer.parseInt(opj.toString()));
							opjControl.setFecha_hora(objDate);
							opjControl.setId_usuario(idUsuario);
							opjControl.setId_operacion(j);
							opjControl.setId_status(1);
							userService.register(opjControl);
						}

						OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(120, TimeUnit.SECONDS)
								.readTimeout(220, TimeUnit.SECONDS).writeTimeout(220, TimeUnit.SECONDS).build();
						HttpUrl.Builder urlBuilder = HttpUrl
								.parse("http://localhost:8080/GenerarRemesa/dce/GenerarEntidad?").newBuilder();

						urlBuilder.addQueryParameter("entidad", entidad);
						urlBuilder.addQueryParameter("remesa", opj.toString());
						String url = urlBuilder.build().toString();
						logger.info("request generarRemesa: " + url);
						Request request = new Request.Builder().url(url).header("Authorization", token.toString())
								.build();

						Response respons = client.newCall(request).execute();

						String bodyString = respons.body().string();

						logger.info("response generar remesa: " + bodyString);
						controlEn.setEntidadID(entidad);
						controlEn.setEstatusServicio("200");
						controlEn.setMensajeSalida(bodyString);
						control.add(controlEn);
					
					} else {
						controlEn.setEntidadID(entidad);
						controlEn.setEstatusServicio("601");
						controlEn.setMensajeSalida("no esta configurado app.config");
						logger.info("no esta configurado app.config ;" + controlEn.getMensajeSalida());
						control.add(controlEn);
					}

				} catch (IOException e) {
					logger.error(DEFAULT_ERROR_SERVICE +"-------------------------------------"+ e.getCause().toString());
					controlEn.setEntidadID(entidad);
					controlEn.setEstatusServicio(e.getLocalizedMessage());
					controlEn.setMsgExceptionDao(e.getCause().toString());
					control.add(controlEn);
				}

			}
		}
        logger.info(control.toString());
		return control;
	}

	@RequestMapping(value = "/estatusMulti", method = { RequestMethod.POST })
	@ResponseBody
	public List<MultiGrafica> ejecutaEstatus(HttpServletRequest reques,
			@RequestParam(required = false) List<String> entidad)
			throws NumberFormatException, ParseException, IOException {

		Date objDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		objDate.equals(dateFormat.format(objDate));
		String remesaActiva = userService.buscarRemesa();

		List<MultiGrafica> listaEstatus = servicioEstatus(entidad, Integer.parseInt(remesaActiva));

		return listaEstatus;

	}
	
	

	public List<MultiGrafica> servicioEstatus(List<String> entidad, int remesa) throws ParseException {

		List<MultiGrafica> listGraficas = new ArrayList<MultiGrafica>();
		if (!entidad.isEmpty()) {
			for (String idEntidad : entidad) {

				String requestUri = "http://localhost:8080/GenerarRemesa/dce/GenerarEntidad/multistatus?entidad={entidad}&remesa={remesa}";
				Map<String, String> urlParameters = new HashMap<>();

				MultiGrafica multi = new MultiGrafica();
				multi.setIdEntidad(Integer.parseInt(idEntidad));
				String nombreEntidad = userService.obtieneNombreEntidad(idEntidad);
                multi.setNombreEntidad(nombreEntidad);
                
				urlParameters.put("entidad", idEntidad);
				urlParameters.put("remesa", Long.toString(remesa));

				logger.info("request multistatus: " + urlParameters);
				try {
					RestTemplate template = new RestTemplate();
					ResponseEntity<String> entity = template.getForEntity(requestUri, String.class, urlParameters);

					logger.info("response multistatus:" + entity.getBody());
					JSONArray jsonarray = new JSONArray(entity.getBody());

					JSONObject objeto = jsonarray.getJSONObject(0);

					String estatus = objeto.getString("message");

					for (int indice = 0; indice < jsonarray.length(); indice++) {

						JSONObject responseMultiStatus = jsonarray.getJSONObject(indice);
						String msj = responseMultiStatus.getString("message");
						int status = responseMultiStatus.getInt("status");

						List<Integer> idOperaciones = new ArrayList<Integer>();
						if (status == 0) {

							if (!responseMultiStatus.getJSONArray("operacionesEjecutadas").isEmpty()) {
								for (Object o : responseMultiStatus.getJSONArray("operacionesEjecutadas")) {

									JSONObject ca = (JSONObject) o;
									JSONObject span = (JSONObject) ca.get("operaciones");
									int idOp = span.getInt("idOperacion");
									idOperaciones.add(idOp);

								}
							} else {
								logger.info("arreglo vacio en operaciones ejecutadas ");
							}
							if (!responseMultiStatus.getJSONArray("operacionesConError").isEmpty()) {
								for (Object o : responseMultiStatus.getJSONArray("operacionesConError")) {

									JSONObject ca = (JSONObject) o;
									String errrores = ca.getString("error");
									JSONObject span = (JSONObject) ca.get("operaciones");
									int idOp = span.getInt("idOperacion");

									String varMensaje = "Fallo en la operacion:------ " + idOp + " Causa: " + errrores;
									multi.setMessaje(varMensaje);
								}
							} else {
								logger.info("arreglo de opercaionesError vacio");
							}

						} else {
							multi.setMsgException("respuesta del servicio multistatus" + msj + " status: " + status);
						}
						multi.setIdOperaciones(idOperaciones);

					}
				} catch (Exception e) {
					multi.setMsgException(e.getMessage());
					logger.info("multi.setMsgException(e.getMessage()); "+multi.getMsgException());
				}

				listGraficas.add(multi);

			}

		}
		logger.info("response lista multiestatus " + listGraficas);
		return listGraficas;
		
	}
}