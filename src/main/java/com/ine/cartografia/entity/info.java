package com.ine.cartografia.entity;

public class info {
	private String entidad ;
	private String anio ;
	private String semana ;
	private Integer estado_remesa ;
	private String ruta ;
	private String msgException;
	
	
	public String getMsgException() {
		return msgException;
	}
	public void setMsgException(String msgException) {
		this.msgException = msgException;
	}
	
	
	public String getEntidad() {
		return entidad;
	}
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}
	public String getAnio() {
		return anio;
	}
	public void setAnio(String anio) {
		this.anio = anio;
	}
	public String getSemana() {
		return semana;
	}
	public void setSemana(String semana) {
		this.semana = semana;
	}
	public Integer getEstado_remesa() {
		return estado_remesa;
	}
	public void setEstado_remesa(Integer estado_remesa) {
		this.estado_remesa = estado_remesa;
	}
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	@Override
	public String toString() {
		return "info [entidad=" + entidad + ", anio=" + anio + ", semana=" + semana + ", estado_remesa=" + estado_remesa
				+ ", ruta=" + ruta + ", msgException=" + msgException + "]";
	}
	
	
	    
}
