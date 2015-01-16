package com.ucab.servimovil.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ReporteService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private long idReporte;
	private Date fechaSolicitud;
	private String dependencia;
	private String solicitante;
	private String descripcion;
	private String dependenciaDescripcion;
	private long deptId;
	
	List<Activo> activos;
	
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
	public String getDependenciaDescripcion() {
		return dependenciaDescripcion;
	}
	public void setDependenciaDescripcion(String dependenciaDescripcion) {
		this.dependenciaDescripcion = dependenciaDescripcion;
	}
	public long getDeptId() {
		return deptId;
	}
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}
	public List<Activo> getActivos() {
		return activos;
	}
	public void setActivos(List<Activo> activos) {
		this.activos = activos;
	}
	
	@Override
	public String toString() {
		return "ReporteService [idReporte=" + idReporte + ", fechaSolicitud="
				+ fechaSolicitud + ", dependencia=" + dependencia
				+ ", solicitante=" + solicitante + ", descripcion="
				+ descripcion + ", dependenciaDescripcion="
				+ dependenciaDescripcion + ", deptId=" + deptId + ", activos="
				+ activos + "]";
	}
	
	

}
