package com.ucab.servimovil.model;

import java.io.Serializable;

public class Activo implements Serializable {
	

	private static final long serialVersionUID = 1L;
	private long idActivo;
	private long realId;
	private long idTransaccion;
	private long idDependencia;
	private String serial;
	private String numero;
	private String nombre;
	private int contador;
	
	public long getIdActivo() {
		return idActivo;
	}
	public void setIdActivo(long idActivo) {
		this.idActivo = idActivo;
	}
	public long getRealId() {
		return realId;
	}
	public void setRealId(long realId) {
		this.realId = realId;
	}
	public long getIdTransaccion() {
		return idTransaccion;
	}
	public void setIdTransaccion(long idTransaccion) {
		this.idTransaccion = idTransaccion;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getContador() {
		return contador;
	}
	public void setContador(int contador) {
		this.contador = contador;
	}
	public long getIdDependencia() {
		return idDependencia;
	}
	public void setIdDependencia(long idDependencia) {
		this.idDependencia = idDependencia;
	}
	@Override
	public String toString() {
		return "Activo [idActivo=" + idActivo + ", realId=" + realId
				+ ", idTransaccion=" + idTransaccion + ", serial=" + serial
				+ ", numero=" + numero + ", nombre=" + nombre + "]";
	}
	
	

}
