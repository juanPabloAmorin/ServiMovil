package com.ucab.servimovil.model;

public class UserService {

	    long idUsuario;
	    String nombre;
	    String login;
	    String status;
	    
		public long getIdUsuario() {
			return idUsuario;
		}
		public void setIdUsuario(long idUsuario) {
			this.idUsuario = idUsuario;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getLogin() {
			return login;
		}
		public void setLogin(String login) {
			this.login = login;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}    
	    
}
