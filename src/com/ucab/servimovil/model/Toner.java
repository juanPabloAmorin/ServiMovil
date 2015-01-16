package com.ucab.servimovil.model;

public class Toner {
	
	private long idToner;
	private String serial;
	private long idModelo;
	private String status;
	private long modeloImpresora;
	private long numeroFactura;
	
	private ModeloToner modeloToner;

	public long getIdToner() {
		return idToner;
	}
	public void setIdToner(long idToner) {
		this.idToner = idToner;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}

	public long getIdModelo() {
		return idModelo;
	}
	public void setIdModelo(long idModelo) {
		this.idModelo = idModelo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getModeloImpresora() {
		return modeloImpresora;
	}
	public void setModeloImpresora(long modeloImpresora) {
		this.modeloImpresora = modeloImpresora;
	}
	public long getNumeroFactura() {
		return numeroFactura;
	}
	public void setNumeroFactura(long numeroFactura) {
		this.numeroFactura = numeroFactura;
	}
	
	public ModeloToner getModeloToner() {
		return modeloToner;
	}
	public void setModeloToner(ModeloToner modeloToner) {
		this.modeloToner = modeloToner;
	}
	
	@Override
	public String toString() {
		return "Toner [idToner=" + idToner + ", serial=" + serial
				+ ", idModelo=" + idModelo + ", status=" + status
				+ ", modeloImpresora=" + modeloImpresora + ", numeroFactura="
				+ numeroFactura + "]";
	}
	

}
