package com.matheus.osworks.api.exceptionhandler;
import java.time.LocalDateTime;

public class Message {

	private Integer status;
	private LocalDateTime dataHora;
	private String titulo;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public LocalDateTime getDataHora() {
		return dataHora;
	}
	public void setDataHora(LocalDateTime localDateTime) {
		this.dataHora = localDateTime;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
}
