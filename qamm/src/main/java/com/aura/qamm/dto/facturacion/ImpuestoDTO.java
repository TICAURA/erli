package com.aura.qamm.dto.facturacion;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class ImpuestoDTO implements Serializable {

	private Integer idImpuesto;
	private String impuestoClave;
	private String impuestoDescripcion;
	private BigDecimal porcentajeImpuesto;
	private BigDecimal totalImpuesto;
	
	
	public ImpuestoDTO(){
		
	}
	
	public ImpuestoDTO(String demo){
		this.idImpuesto = new Integer(1);
		this.impuestoClave = "002";
		this.impuestoDescripcion = "IVA Trasladado 16%";
		this.porcentajeImpuesto = new BigDecimal("0.160000");
		this.totalImpuesto = new BigDecimal(1600);
	}

	public Integer getIdImpuesto() {
		return idImpuesto;
	}

	public void setIdImpuesto(Integer idImpuesto) {
		this.idImpuesto = idImpuesto;
	}

	public String getImpuestoClave() {
		return impuestoClave;
	}

	public void setImpuestoClave(String impuestoClave) {
		this.impuestoClave = impuestoClave;
	}

	public String getImpuestoDescripcion() {
		return impuestoDescripcion;
	}

	public void setImpuestoDescripcion(String impuestoDescripcion) {
		this.impuestoDescripcion = impuestoDescripcion;
	}

	public BigDecimal getPorcentajeImpuesto() {
		return porcentajeImpuesto;
	}

	public void setPorcentajeImpuesto(BigDecimal porcentajeImpuesto) {
		this.porcentajeImpuesto = porcentajeImpuesto;
	}

	public BigDecimal getTotalImpuesto() {
		return totalImpuesto;
	}

	public void setTotalImpuesto(BigDecimal totalImpuesto) {
		this.totalImpuesto = totalImpuesto;
	}
}