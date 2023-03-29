package com.sistema.logindos.dto;

import java.util.List;

public class ComentarioRespuesta {

	private List<ComentarioDTO> contenido;
	private int numeroPagina;
	private int medidaPagina;
	private long totalRegistros;
	private int totalPaginas;
	private boolean ultimaPagina;
	public ComentarioRespuesta() {
		super();
	}
	public List<ComentarioDTO> getContenido() {
		return contenido;
	}
	public void setContenido(List<ComentarioDTO> contenido) {
		this.contenido = contenido;
	}
	public int getNumeroPagina() {
		return numeroPagina;
	}
	public void setNumeroPagina(int numeroPagina) {
		this.numeroPagina = numeroPagina;
	}
	public int getMedidaPagina() {
		return medidaPagina;
	}
	public void setMedidaPagina(int medidaPagina) {
		this.medidaPagina = medidaPagina;
	}
	public long getTotalRegistros() {
		return totalRegistros;
	}
	public void setTotalRegistros(long totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
	public int getTotalPaginas() {
		return totalPaginas;
	}
	public void setTotalPaginas(int totalPaginas) {
		this.totalPaginas = totalPaginas;
	}
	public boolean isUltimaPagina() {
		return ultimaPagina;
	}
	public void setUltimaPagina(boolean ultimaPagina) {
		this.ultimaPagina = ultimaPagina;
	}
	
	
}
