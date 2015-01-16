package com.ucab.servimovil.model;

import java.util.Date;

public class Transaccion {
	
	long transaccionId;
	Date fechaTransaccion;
	String tipoTransaccion;

	long idUsuario;
	String observaciones;
	String status;
	long idReporte;
	
	Reporte reporte;
	Toner toner;
	
	public long getTransaccionId() {
		return transaccionId;
	}
	public void setTransaccionId(long transaccionId) {
		this.transaccionId = transaccionId;
	}
	public Date getFechaTransaccion() {
		return fechaTransaccion;
	}
	public void setFechaTransaccion(Date fechaTransaccion) {
		this.fechaTransaccion = fechaTransaccion;
	}
	public String getTipoTransaccion() {
		return tipoTransaccion;
	}
	public void setTipoTransaccion(String tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}
	
	public long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Reporte getReporte() {
		return reporte;
	}
	public void setReporte(Reporte reporte) {
		this.reporte = reporte;
	}
	public Toner getToner() {
		return toner;
	}
	public void setToner(Toner toner) {
		this.toner = toner;
	}
	
	public long getIdReporte() {
		return idReporte;
	}
	public void setIdReporte(long idReporte) {
		this.idReporte = idReporte;
	}
	@Override
	public String toString() {
		return "Transaccion [transaccionId=" + transaccionId
				+ ", fechaTransaccion=" + fechaTransaccion
				+ ", tipoTransaccion=" + tipoTransaccion + ", idUsuario="
				+ idUsuario + ", observaciones=" + observaciones + ", status="
				+ status + ", reporte=" + reporte + ", toner=" + toner + "]";
	}
	
	
	
	

}
