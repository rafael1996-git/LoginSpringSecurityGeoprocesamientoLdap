package com.ine.cartografia.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ine.cartografia.dao.UserDao;
import com.ine.cartografia.entity.Control;
import com.ine.cartografia.entity.Remesa;
import com.ine.cartografia.entity.User;
import com.ine.cartografia.entity.UserControl;
import com.ine.cartografia.entity.info;
import com.ine.cartografia.entity.statusError;

public class UserServiceImpl implements UserService {

	@Autowired
	public UserDao userDao;

	public int register(UserControl user) {
		return userDao.register(user);
	}

	@Override
	public List<User> list() {
		// TODO Auto-generated method stub
		return userDao.list();
	}

	@Override
	public List<UserControl> lista() {
		// TODO Auto-generated method stub
		return userDao.lista();
	}

	@Override
	public int register(Control inserta) {
		return userDao.register(inserta);
	}

	@Override
	public int regisRemesa(Remesa remesa) {
		// TODO Auto-generated method stub
		return userDao.regisRemesa(remesa);
	}

	@Override
	public String buscarRemesa() {
		// TODO Auto-generated method stub
		return userDao.buscarRemesa();
	}

	@Override
	public List<info> validate(String entidad, String anio, String semana) {
		return userDao.validate(entidad, anio, semana);
	}

	@Override
	public List<UserControl> findByUserControlAndPassword(String correo, String password) {
		return userDao.findByUserControlAndPassword(correo, password);
	}

	@Override
	public UserControl findBycorreo(String correo) {
		return userDao.findBycorreo(correo);
	}

	@Override
	public String buscarAdmin() {
		return userDao.buscarAdmin();
	}

	@Override
	public List<UserControl> findByUserAndPassword(String correo, String password) {

		return userDao.findByUserAndPassword(correo, password);
	}

	@Override
	public User findByUsercorreo(String correo) {
		// TODO Auto-generated method stub
		return userDao.findByUsercorreo(correo);
	}

	@Override
	public void delete(String correo) {
		userDao.delete(correo);
	}

	@Override
	public List<User> listaFiltrada(int entidad) {

		return userDao.listaFiltrada(entidad);
	}

	@Override
	public void deleteAut(String token) {
		userDao.deleteAut(token);
	}

	@Override
	public int register(statusError status) {
		// TODO Auto-generated method stub
		return userDao.register(status);
	}

	@Override
	public List<statusError> listaStatus(String fecha) {
		// TODO Auto-generated method stub
		return userDao.listaStatus(fecha);
	}

	@Override
	public statusError findByfecha(String fecha, String error) {
		// TODO Auto-generated method stub
		return userDao.findByfecha(fecha, error);
	}

	@Override
	public statusError buscarfecha(String fecha) {
		// TODO Auto-generated method stub
		return userDao.buscarfecha(fecha);
	}

	@Override
	public Map<Integer,String> entidadesActivas() {
		return userDao.entidadesActivas();
	}

	@Override
	public String obtieneNombreEntidad(String idEntidad) {
		return userDao.obtieneNombreEntidad(idEntidad);
	}

	@Override
	public UserControl findByAuthority(String correo) {
		// TODO Auto-generated method stub
		return userDao.findByAuthority(correo);
	}

	@Override
	public String buscarEntidad() {
		// TODO Auto-generated method stub
		return userDao.buscarEntidad();
	}

	


}
