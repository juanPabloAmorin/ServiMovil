package com.ucab.servimovil.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class LocalTransaction implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private long idTransaccion; 
	private Date fechaTransaccion; 
	private String tipoTransaccion;
	private long idUsuario;
	private long idReporte;
	private String observaciones;
	private long idToner;
	private String status;
	private String dependencia;
	private String solicitante;
	private long idImpresora;
	private String modeloImpresora;
	private String serialToner;
	private String ModeloToner;
	private long serverTransaction;
	private String descripcion; 
	private int contador;
	private String receptor;
	private long idActivo;
	private String isSolved;
	private String solucion;
	
	private byte[] firma;
	
	private List<Activo> activos;
	
	
	public long getIdTransaccion() {
		return idTransaccion;
	}
	public void setIdTransaccion(long idTransaccion) {
		this.idTransaccion = idTransaccion;
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
	public long getIdReporte() {
		return idReporte;
	}
	public void setIdReporte(long idReporte) {
		this.idReporte = idReporte;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public long getIdToner() {
		return idToner;
	}
	public void setIdToner(long idToner) {
		this.idToner = idToner;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public long getIdImpresora() {
		return idImpresora;
	}
	public void setIdImpresora(long idImpresora) {
		this.idImpresora = idImpresora;
	}
	public String getModeloImpresora() {
		return modeloImpresora;
	}
	public void setModeloImpresora(String modeloImpresora) {
		this.modeloImpresora = modeloImpresora;
	}
	public String getSerialToner() {
		return serialToner;
	}
	public void setSerialToner(String serialToner) {
		this.serialToner = serialToner;
	}
	public String getModeloToner() {
		return ModeloToner;
	}
	public void setModeloToner(String modeloToner) {
		ModeloToner = modeloToner;
	}
	public long getServerTransaction() {
		return serverTransaction;
	}
	public void setServerTransaction(long serverTransaction) {
		this.serverTransaction = serverTransaction;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getContador() {
		return contador;
	}
	public void setContador(int contador) {
		this.contador = contador;
	}
	public byte[] getFirma() {
		return firma;
	}
	public void setFirma(byte[] firma) {
		this.firma = firma;
	}
	public String getReceptor() {
		return receptor;
	}
	public void setReceptor(String receptor) {
		this.receptor = receptor;
	}
	public List<Activo> getActivos() {
		return activos;
	}
	public void setActivos(List<Activo> activos) {
		this.activos = activos;
	}
	public long getIdActivo() {
		return idActivo;
	}
	public void setIdActivo(long idActivo) {
		this.idActivo = idActivo;
	}
	public String getIsSolved() {
		return isSolved;
	}
	public void setIsSolved(String isSolved) {
		this.isSolved = isSolved;
	}
	public String getSolucion() {
		return solucion;
	}
	public void setSolucion(String solucion) {
		this.solucion = solucion;
	}
	@Override
	public String toString() {
		return "LocalTransaction [idTransaccion=" + idTransaccion
				+ ", fechaTransaccion=" + fechaTransaccion
				+ ", tipoTransaccion=" + tipoTransaccion + ", idUsuario="
				+ idUsuario + ", idReporte=" + idReporte + ", observaciones="
				+ observaciones + ", idToner=" + idToner + ", status=" + status
				+ ", dependencia=" + dependencia + ", solicitante="
				+ solicitante + ", idImpresora=" + idImpresora
				+ ", modeloImpresora=" + modeloImpresora + ", serialToner="
				+ serialToner + ", ModeloToner=" + ModeloToner
				+ ", serverTransaction=" + serverTransaction + ", descripcion="
				+ descripcion + ", contador=" + contador + ", receptor="
				+ receptor + ", idActivo=" + idActivo + ", isSolved="
				+ isSolved + ", solucion=" + solucion + ", firma="
				+ Arrays.toString(firma) + ", activos=" + activos + "]";
	}
 
	
    
	

}
