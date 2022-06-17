package com.ine.cartografia.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ine.cartografia.entity.MultiGrafica;
import com.ine.cartografia.entity.User;
import com.ine.cartografia.entity.UserControl;
import com.ine.cartografia.entity.statusError;
import com.ine.cartografia.service.UserService;



@Controller
public class LoginController {

	protected static final Log logger = LogFactory.getLog(LoginController.class.getName());
	@Autowired
	public UserService userService;
	@Secured({ "ROLE_SUBMANAGERS", "ROLE_MANAGERS","ROLE_DEVELOPERS" })
	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage(Authentication authentication,
			HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		if(authentication != null) {
			logger.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));
			logger.info("Forma usando Authorities:  ".concat(authentication.getAuthorities().toString()).concat("acceso a app admon!"));
		}
	
		List<UserControl> listaPersonas = userService.lista();
		UserControl uControl = userService.findBycorreo(authentication.getName());
		HttpSession session = request.getSession();
		session.setAttribute("id", uControl.getId_usuario());
		session.setAttribute("entidad", uControl.getEntidad());
		session.setAttribute("tipo", uControl.getId_tipo_usuario());
		request.setAttribute("firstname",authentication.getName());
		request.setAttribute("lista", listaPersonas);
		model.setViewName("admin");
		return model;

	}

	@Secured({ "ROLE_SUBMANAGERS", "ROLE_MANAGERS","ROLE_DEVELOPERS" })
	@RequestMapping(value = "/viewAdmin", method = RequestMethod.GET)
	public ModelAndView atrasPage(Authentication authentication,HttpServletRequest request) throws IOException {
		logger.info("Forma usando Authorities:  ".concat(authentication.getAuthorities().toString()).concat("acceso a app /viewAdmin!"));
		ModelAndView model = new ModelAndView();
		List<UserControl> listaPersonas = userService.lista();
		HttpSession session = request.getSession();
		UserControl uControl = userService.findBycorreo(authentication.getName());
		Map<Integer,String> listEntidadesActivas = userService.entidadesActivas();
		session.setAttribute("id", uControl.getId_usuario());
		session.setAttribute("entidad", uControl.getEntidad());
		session.setAttribute("tipo", uControl.getId_tipo_usuario());
		request.setAttribute("entidadesActivas",listEntidadesActivas);
		request.setAttribute("firstname",authentication.getName());
		request.setAttribute("lista", listaPersonas);
		model.setViewName("admin");
		return model;

	}
	@Secured({ "ROLE_SUBMANAGERS", "ROLE_MANAGERS"})
	@RequestMapping(value = "/avance", method = RequestMethod.GET)
	public ModelAndView AvancePage(Authentication authentication,HttpServletRequest request) throws IOException{
		if(authentication != null) {
			logger.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));
			logger.info("Forma usando Authorities:  ".concat(authentication.getAuthorities().toString()).concat("acceso a app avance!"));
		}
		ModelAndView model = new ModelAndView();
		UserControl uControl = userService.findBycorreo(authentication.getName());	
		Map<Integer,String> listEntidadesActivas = userService.entidadesActivas();
		logger.info("Avance 	entidades activas: "+listEntidadesActivas.toString());
		HttpSession session = request.getSession();
		session.setAttribute("id", uControl.getId_usuario());
		session.setAttribute("tipo", uControl.getId_tipo_usuario());
		session.setAttribute("entidad", uControl.getEntidad());
		request.setAttribute("entidadesActivas",listEntidadesActivas);
		request.setAttribute("firstname",authentication.getName());
		
		// To do something
		model.setViewName("avance");

		return model;

	}
	@Secured({ "ROLE_SUBMANAGERS", "ROLE_MANAGERS"})
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public ModelAndView WelcomePage(Authentication authentication,HttpServletRequest request) throws IOException {
		ModelAndView model = new ModelAndView();
		
		logger.info("Forma usando Authorities:  ".concat(authentication.getAuthorities().toString()).concat("acceso a app /welcome!"));
		UserControl uControl = userService.findBycorreo(authentication.getName());
		List<User> listaPersonas = userService.listaFiltrada(uControl.getEntidad());
		Map<Integer,String> listEntidadesActivas = userService.entidadesActivas();
		HttpSession session = request.getSession();
		session.setAttribute("id", uControl.getId_usuario());
		session.setAttribute("entidad", uControl.getEntidad());
		session.setAttribute("tipo", uControl.getId_tipo_usuario());
		request.setAttribute("lista", listaPersonas);
		request.setAttribute("entidadesActivas",listEntidadesActivas);
		request.setAttribute("firstname",authentication.getName());	
		model.setViewName("adminC");
		return model;

	}
	@Secured({ "ROLE_DEVELOPERS", "ROLE_MANAGERS"})
	@RequestMapping("/MultiProcesos")
	public ModelAndView MultiProcesosPage(Authentication authentication,HttpServletRequest request){
		ModelAndView model = new ModelAndView();
		try {
			
			logger.info("Forma usando Authorities:  ".concat(authentication.getAuthorities().toString()).concat("acceso a app /MultiProcesos!"));
				Map<Integer,String> listEntidadesActivas = userService.entidadesActivas();
				UserControl uControl = userService.findBycorreo(authentication.getName());
				
				HttpSession session = request.getSession();
				session.setAttribute("id", uControl.getId_usuario());
				session.setAttribute("entidad", uControl.getEntidad());
				session.setAttribute("tipo", uControl.getId_tipo_usuario());
				
				request.setAttribute("firstname",authentication.getName());
				request.setAttribute("entidadesActivas",listEntidadesActivas);
				model.setViewName("multiprocesos");
				System.out.println("multiproceso get: "+model.getViewName());
				
			
			

				

			
		} catch (Exception e) {
			request.setAttribute("msgAcces", e.getMessage());
			logger.info("Exception  multiproceso"+e.getCause().toString());
			List<UserControl> listaPersonas = userService.lista();
			request.setAttribute("lista", listaPersonas);
			model.setViewName("admin");
			e.getStackTrace();
		}
		

		return model;

	}

	@RequestMapping( "/statusError")
	public @ResponseBody List<statusError> statusPage(HttpServletRequest request,@RequestParam("datoE") String dato){
		ModelAndView model = new ModelAndView();
			 LocalDate myObj = LocalDate.now();  // Create a date object
			    System.out.println(myObj); 
				List<statusError>  lista = userService.listaStatus(myObj.toString());

							statusError buscafecha=userService.buscarfecha(myObj.toString());
							if (buscafecha!=null && dato!=null) {
								System.out.println("_______________si hay lista_____________________");

								return lista;								
								
							} else {	
								System.out.println("_______________null_____________________de statusError");

								return null;
							
							}

		

	}

	@RequestMapping(value="/Error**", method = RequestMethod.GET)
	public ModelAndView error(Authentication authentication,HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		model.addObject("CustomSessionAttribute","ACCESS IS DENIED PARA : ".concat(authentication.getName()));
		List<UserControl> listaPersonas = userService.lista();
		request.setAttribute("firstname",authentication.getName());
		request.setAttribute("lista", listaPersonas);
		model.setViewName("admin");

 		return model;
 	}

	@RequestMapping(value = "/Welcome", method = RequestMethod.POST)
	public ModelAndView loginProcess(@RequestParam String password, @RequestParam String correo,
			HttpServletRequest request) throws IOException{
		ModelAndView mav = null;
		List<UserControl> listaP = userService.lista();

		System.out.println("en home");
		logger.info("Mensaje de vista:******************************** ");

		try {
			List<UserControl> usuarioControl = userService.findByUserControlAndPassword(correo, password);
			List<UserControl> userControl = userService.findByUserAndPassword(correo, password);
			UserControl uControl = userService.findBycorreo(correo);
			
			if (usuarioControl != null && !usuarioControl.isEmpty()) {

				HttpSession session = request.getSession();
				session.setAttribute("firstname", uControl.getCorreo());
				session.setAttribute("id", uControl.getId_usuario());
				session.setAttribute("entidad", uControl.getEntidad());
				session.setAttribute("tipo", uControl.getId_tipo_usuario());
				List<User> listaPersonas = userService.listaFiltrada(uControl.getEntidad());
				request.setAttribute("lista", listaPersonas);
				mav = new ModelAndView("adminC");

			} else if (uControl.getId_tipo_usuario() == 2 && userControl != null &&  uControl != null && !userControl.isEmpty() ) {
				HttpSession session = request.getSession();
				session.setAttribute("firstname", uControl.getCorreo());
				request.setAttribute("lista", listaP);
				mav = new ModelAndView("admin");
			} else if (uControl.getId_tipo_usuario() == 3 && userControl != null && uControl != null && !userControl.isEmpty() ) {
				Map<Integer,String> listEntidadesActivas = userService.entidadesActivas();
				
				HttpSession session = request.getSession();
				session.setAttribute("firstname", uControl.getCorreo());
				session.setAttribute("id", uControl.getId_usuario());
				session.setAttribute("entidad", uControl.getEntidad());
				session.setAttribute("tipo", uControl.getId_tipo_usuario());
	            session.setAttribute("entidadesActivas",listEntidadesActivas);
				mav = new ModelAndView("multiprocesos");
				
				
			}else {
				mav = new ModelAndView("login");
				mav.addObject("error", "Invalid username and password!");
				
			}

		} catch (UsernameNotFoundException ex) {
			ex.printStackTrace();
			mav = new ModelAndView("login");
			mav.addObject("error", "Invalid username and password!");
		} catch (Exception ex) {
			ex.printStackTrace();
			mav = new ModelAndView("login");
			mav.addObject("error", "Invalid username and password! ");
		}
		System.out.println("_______________dataStore_____________________insert:");
		return mav;

	}

	@RequestMapping(value = "/estatus", method = { RequestMethod.POST })
	@ResponseBody
	public String ejecutaEstatus(HttpServletRequest reques) throws NumberFormatException, ParseException, IOException {
		HttpSession session = reques.getSession();
		int id = (int) session.getAttribute("id");
		String listEntidadesActivas = userService.buscarEntidad();
		int entidadUsuario = (int) session.getAttribute("entidad");
		String opj = userService.buscarRemesa();
		System.out.println("query service buscarRemesa: " + opj);

		String listaEstatus = getEstatusRemesa(Integer.parseInt(listEntidadesActivas), Integer.parseInt(opj));

//		List<MultiGrafica> listaEstatusError = getEstatusError(entidadUsuario, Integer.parseInt(opj));
		 return "{\"alejandro\":" + listaEstatus + "}";
//		return "{\"errordata\":" + listaEstatusError.toString() + ",\"alejandro\":" + listaEstatus + "}";

	}
	@RequestMapping(value = "/estatusERROR", method = { RequestMethod.POST })
	@ResponseBody
	public List<MultiGrafica> ejecutaEstatusError(HttpServletRequest reques)
			throws NumberFormatException, ParseException, IOException {
		HttpSession session = reques.getSession();
		int id = (int) session.getAttribute("id");
		String listEntidadesActivas = userService.buscarEntidad();
		int entidadUsuario = (int) session.getAttribute("entidad");
		String opj = userService.buscarRemesa();
		System.out.println(" service statusERROR: " + opj);

		List<MultiGrafica> listaEstatusError = getEstatusError(Integer.parseInt(listEntidadesActivas), Integer.parseInt(opj));
		return listaEstatusError;

	}

	public String getEstatusRemesa(int entidad, int remesa) throws ParseException {

		String requestUri = "http://localhost:8080/GenerarRemesa/dce/GenerarEntidad/status?entidad={entidad}&remesa={remesa}";
		Map<String, String> urlParameters = new HashMap<>();
		urlParameters.put("entidad", Integer.toString(entidad));
		urlParameters.put("remesa", Long.toString(remesa));

		RestTemplate template = new RestTemplate();
		ResponseEntity<String> entity = template.getForEntity(requestUri, String.class, urlParameters);

		List<Integer> grafica = new ArrayList<Integer>();

		try {		
			JSONObject js = new JSONObject(entity.getBody());
			if (js != null || !js.isEmpty()) {
				for (Object o : js.getJSONArray("operacionesEjecutadas")) {
					if(o!=null){
						JSONObject ca = (JSONObject) o;
						JSONObject span = (JSONObject) ca.get("operaciones");
						int idOp = span.getInt("idOperacion");
						grafica.add(idOp);
						System.out.println(
								"*************************************************************************[operacionid"+ idOp);
					}else {
						System.out.println("*****no hay");
					}
					
					
				}

				return grafica.toString();

			} else {
				return null;
			}
		} catch (Exception e) 
		{ 
				System.out.println("Estatus del servicio : " + entity.getStatusCode().toString ());
				return entity.getStatusCode().getReasonPhrase();	
			
		}

			
		

	}


	public List<MultiGrafica> getEstatusError(int entidad, int remesa) throws ParseException {
		List<MultiGrafica> listGraficas = new ArrayList<MultiGrafica>();
		MultiGrafica multi = new MultiGrafica();
		String requestUri = "http://localhost:8080/GenerarRemesa/dce/GenerarEntidad/status?entidad={entidad}&remesa={remesa}";
		Map<String, String> urlParameters = new HashMap<>();
		urlParameters.put("entidad", Integer.toString(entidad));
		urlParameters.put("remesa", Long.toString(remesa));
		multi.setIdEntidad( entidad);
		String nombreEntidad = userService.obtieneNombreEntidad(Integer.toString(entidad));
        multi.setNombreEntidad(nombreEntidad);
        try {
        	RestTemplate template = new RestTemplate();
    		ResponseEntity<String> entity = template.getForEntity(requestUri, String.class, urlParameters);
    		List<String> grafica = new ArrayList<String>();
    		String string = entity.getBody();
    		JSONObject Jobject = new JSONObject(string);
    		JSONArray Jarray = Jobject.getJSONArray("operacionesConError");
    			String fecha, err;
    			int limit = Jarray.length();
    			String dataStore[] = new String[limit];
    			for (int i = 0; i < limit; i++) {
    				JSONObject object = Jarray.getJSONObject(i);
    				List<Integer> idOperaciones = new ArrayList<Integer>();
    				JSONObject ca = (JSONObject) object;
    				JSONObject span = (JSONObject) ca.get("operaciones");
    				int idOp = span.getInt("idOperacion");
    				fecha = object.getString("sFechaHora");
    				err = object.getString("error");
    				int enti = object.getInt("entidad");
    				int reme = object.getInt("remesa");

    				System.out.println("_______________idOperacion_____________________:" + span.getInt("idOperacion"));
    				System.out.println("_______________fecha_____________________:" + object.getString("sFechaHora"));
    				System.out.println("_______________entidad_____________________:" + object.getInt("entidad"));
    				System.out.println("_______________remesa_____________________:" + object.getInt("remesa"));
    				System.out.println("_______________error_____________________:" + object.getString("error"));
    				String varMensaje = "Fallo en la operacion: " + idOp + " Causa: " + err;
    				multi.setMessaje(varMensaje);
    				multi.setIdOperaciones(idOperaciones);
    				System.out.println("_______________error multi.getMessaje()_____________________:" + multi.getMessaje());
    				String var="=";
    				dataStore[i] = "{\"iden\":\"" + enti +"\",\"idre\":\""+null+"\",\"idfe\":\""+null+"\",\"error\":\"Se ha Encontrado un Error Checkout"+var+"\"}";

    			}
    			for (String content : dataStore) {
    			grafica.add(content);	
    			}
		} catch (Exception e) {
			multi.setMsgException(e.getCause().toString());
			logger.info("multi.setMsgException(e.getMessage()); "+multi.getMsgException());
		}
		
        listGraficas.add(multi);
        logger.info("response lista rafa listGraficas" + listGraficas);
		return listGraficas;
		

	}
	private boolean hasRole(String role) {
		
		SecurityContext context = SecurityContextHolder.getContext();
		
		if(context == null) {
			return false;
		}
		
		Authentication auth = context.getAuthentication();
		
		if(auth == null) {
			return false;
		}
		
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		return authorities.contains(new SimpleGrantedAuthority(role));
		
		/*
		 * for(GrantedAuthority authority: authorities) {
			if(role.equals(authority.getAuthority())) {
				logger.info("Hola usuario ".concat(auth.getName()).concat(" tu role es: ".concat(authority.getAuthority())));
				return true;
			}
		}
		
		return false;
		*/
		
	}
	
	
}
