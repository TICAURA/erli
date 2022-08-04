/**
 * 
 */
package com.aura.qamm.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Acer
 *
 */
public class EventoLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1617454400886116035L;
	
	private Integer id;
	
	private Date fecha;
	
	private String tipo;
	
	private String idColaborador;
	
	private String georeferencia;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the georeferencia
	 */
	public String getGeoreferencia() {
		return georeferencia;
	}

	/**
	 * @param georeferencia the georeferencia to set
	 */
	public void setGeoreferencia(String georeferencia) {
		this.georeferencia = georeferencia;
	}

	/**
	 * @return the idColaborador
	 */
	public String getIdColaborador() {
		return idColaborador;
	}

	/**
	 * @param idColaborador the idColaborador to set
	 */
	public void setIdColaborador(String idColaborador) {
		this.idColaborador = idColaborador;
	}

}
