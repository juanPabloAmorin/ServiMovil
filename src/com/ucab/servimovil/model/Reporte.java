package com.ucab.servimovil.model;

import java.util.Date;

public class Reporte {
	
	 long idReporte;
	 Date fechaSolicitud;
	 String dependencia;
	 String solicitante;
	 String descripcion;
	 
	public long getIdReporte() {
		return idReporte;
	}
	public void setIdReporte(long idReporte) {
		this.idReporte = idReporte;
	}
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	public String getDependencia() {
		return dependencia;
	}
	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}
	public String getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@Override
	public String toString() {
		return "Reporte [idReporte=" + idReporte + ", fechaSolicitud="
				+ fechaSolicitud + ", dependencia=" + dependencia
				+ ", solicitante=" + solicitante + ", descripcion="
				+ descripcion + "]";
	}
	
	 

}
