package com.aura.qamm.dto.facturacion;

import java.io.Serializable;

public class FacturaMsDTO implements Serializable {

	private DocumentoDTO xml;
	private DocumentoDTO pdf;
	
	public FacturaMsDTO(){}

	public DocumentoDTO getXml() {
		return xml;
	}

	public void setXml(DocumentoDTO xml) {
		this.xml = xml;
	}

	public DocumentoDTO getPdf() {
		return pdf;
	}

	public void setPdf(DocumentoDTO pdf) {
		this.pdf = pdf;
	}
}