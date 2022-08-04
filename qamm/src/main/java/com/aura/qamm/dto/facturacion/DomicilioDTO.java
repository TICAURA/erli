package com.aura.qamm.dto.facturacion;

import java.io.Serializable;

public class DomicilioDTO implements Serializable {

	private static final long serialVersionUID = -1913469152840919400L;

	private Integer idDomicilio;
	private String codigoPostal;
	private String localidad;
	private String calle;
	private String numExterior;
	private String numInterior;
	private String referencia;
	private String colonia;
	private String municipio;
	private String claveMunicipio;
	private String estado;
	private String pais;

	private String completo;

	public DomicilioDTO() {
		
	}

	public DomicilioDTO(String demo) {
		this.idDomicilio = 1;
		this.codigoPostal = "01020";
		this.localidad = "Localidad";
		this.calle = "Insurgentes Sur";
		this.numExterior = "1677";
		this.numInterior = "1006";
		this.referencia = "Referencia";
		this.colonia ="loma bonita";
		this.municipio = "Azcapotzalco";
		this.claveMunicipio = "002";
		this.estado = "CDMX";
		this.pais = "MX";

		this.completo = "Completo, a lo mejor es la dirección";
	}

	public Integer getIdDomicilio() {
		return idDomicilio;
	}

	public void setIdDomicilio(Integer idDomicilio) {
		this.idDomicilio = idDomicilio;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumExterior() {
		return numExterior;
	}

	public void setNumExterior(String numExterior) {
		this.numExterior = numExterior;
	}

	public String getNumInterior() {
		return numInterior;
	}

	public void setNumInterior(String numInterior) {
		this.numInterior = numInterior;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	

	public String getColonia() {
		return colonia;
	}
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getCompleto() {
		return completo;
	}

	public void setCompleto(String completo) {
		this.completo = completo;
	}

	public String getClaveMunicipio() {
		return claveMunicipio;
	}

	public void setClaveMunicipio(String claveMunicipio) {
		this.claveMunicipio = claveMunicipio;
	}
}
