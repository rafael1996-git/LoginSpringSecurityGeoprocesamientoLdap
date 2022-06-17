package com.ine.cartografia.dao;

import java.util.List;
import java.util.Map;

import com.ine.cartografia.entity.Control;
import com.ine.cartografia.entity.Remesa;
import com.ine.cartografia.entity.User;
import com.ine.cartografia.entity.UserControl;
import com.ine.cartografia.entity.info;
import com.ine.cartografia.entity.statusError;





public interface UserDao {

	public int register(UserControl user);

	public int register(statusError status);

	public int register(Control inserta);

	public int regisRemesa(Remesa remesa);

	public List<info> validate(String entidad, String anio, String semana);

	public List<User> list();

	public List<User> listaFiltrada(int entidad);

	public List<UserControl> lista();

	public List<statusError> listaStatus(String fecha);

	public String buscarRemesa();
	public String buscarEntidad();

	public List<UserControl> findByUserControlAndPassword(String correo, String password);

	public List<UserControl> findByUserAndPassword(String correo, String password);

	public UserControl findBycorreo(String correo);
	public UserControl findByAuthority(String correo);
	public statusError findByfecha(String fecha, String error);

	public statusError buscarfecha(String fecha);

	public User findByUsercorreo(String correo);

	public String buscarAdmin();

	public void delete(String correo);

	public void deleteAut(String token);

	public Map<Integer, String> entidadesActivas();

	public String obtieneNombreEntidad(String idEntidad);

	

}
