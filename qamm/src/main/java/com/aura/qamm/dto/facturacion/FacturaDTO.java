package com.aura.qamm.dto.facturacion;

import java.io.Serializable;
import java.util.List;


@SuppressWarnings("serial")
public class FacturaDTO implements Serializable {


	// datos en properties
	private String pacTimbrado;
	private String tipoFactura;
	private String version;
	private String serie;
	private Long folio;

	private String tipoCambio;
	private String formaPago;
	private String moneda;
	private String tipoComprobante;
	private String metodoPago;
	private String lugarExpedicion;
	private String regimenFiscal;

	private String formaPagoDesc;
	private String monedaDesc;
	private String tipoComprobanteDesc;
	private String metodoPagoDesc;
	private String tipoFacturaDesc;
	private String regimenFiscalDesc;

	//datos a llenar
	//3 Totales
	private TotalDTO totales; // comision

	//4 receptor
	private ClienteDTO cliente;

	//5 conceptos
	private List<ConceptoDTO> conceptos;

	//// PDF ///////
	private String inicioPeriodo;
	private String finPeriodo;


	//Datos con duda
	private String fechaDispersion;
	private String idConsar;
	//cliente /idEmpleadoSTP

	//private String nombreDeArchivo;


	private int clientId;
	private int persId;
	private String fecIngreso;



	public FacturaDTO(){
		
	}


	public String getPacTimbrado() {
		return pacTimbrado;
	}

	public void setPacTimbrado(String pacTimbrado) {
		this.pacTimbrado = pacTimbrado;
	}

	public String getTipoFactura() {
		return tipoFactura;
	}

	public void setTipoFactura(String tipoFactura) {
		this.tipoFactura = tipoFactura;
	}



	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public Long getFolio() {
		return folio;
	}

	public void setFolio(Long folio) {
		this.folio = folio;
	}

	public String getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(String tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}


	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}


	public String getTipoComprobante() {
		return tipoComprobante;
	}

	public void setTipoComprobante(String tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}



	public String getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}



	public String getLugarExpedicion() {
		return lugarExpedicion;
	}

	public void setLugarExpedicion(String lugarExpedicion) {
		this.lugarExpedicion = lugarExpedicion;
	}

	public TotalDTO getTotales() {
		return totales;
	}

	public void setTotales(TotalDTO totales) {
		this.totales = totales;
	}

	/**
	public EmpresaDTO getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaDTO empresa) {
		this.empresa = empresa;
	}

	public DomicilioDTO getDomicilioEmpresa() {
		return domicilioEmpresa;
	}

	public void setDomicilioEmpresa(DomicilioDTO domicilioEmpresa) {
		this.domicilioEmpresa = domicilioEmpresa;
	}
**/
	public ClienteDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}

	public List<ConceptoDTO> getConceptos() {
		return conceptos;
	}

	public void setConceptos(List<ConceptoDTO> conceptos) {
		this.conceptos = conceptos;
	}

	public String getIdConsar() {
		return idConsar;
	}

	public void setIdConsar(String idConsar) {
		this.idConsar = idConsar;
	}


	public String getRegimenFiscal() {
		return regimenFiscal;
	}

	public void setRegimenFiscal(String regimenFiscal) {
		this.regimenFiscal = regimenFiscal;
	}







	public String getInicioPeriodo() {
		return inicioPeriodo;
	}

	public void setInicioPeriodo(String inicioPeriodo) {
		this.inicioPeriodo = inicioPeriodo;
	}

	public String getFinPeriodo() {
		return finPeriodo;
	}

	public void setFinPeriodo(String finPeriodo) {
		this.finPeriodo = finPeriodo;
	}

	public String getFechaDispersion() {
		return fechaDispersion;
	}

	public void setFechaDispersion(String fechaDispersion) {
		this.fechaDispersion = fechaDispersion;
	}

	public String getFormaPagoDesc() {
		return formaPagoDesc;
	}

	public void setFormaPagoDesc(String formaPagoDesc) {
		this.formaPagoDesc = formaPagoDesc;
	}

	public String getMonedaDesc() {
		return monedaDesc;
	}

	public void setMonedaDesc(String monedaDesc) {
		this.monedaDesc = monedaDesc;
	}

	public String getTipoComprobanteDesc() {
		return tipoComprobanteDesc;
	}

	public void setTipoComprobanteDesc(String tipoComprobanteDesc) {
		this.tipoComprobanteDesc = tipoComprobanteDesc;
	}

	public String getMetodoPagoDesc() {
		return metodoPagoDesc;
	}

	public void setMetodoPagoDesc(String metodoPagoDesc) {
		this.metodoPagoDesc = metodoPagoDesc;
	}

	public String getTipoFacturaDesc() {
		return tipoFacturaDesc;
	}

	public void setTipoFacturaDesc(String tipoFacturaDesc) {
		this.tipoFacturaDesc = tipoFacturaDesc;
	}

	public String getRegimenFiscalDesc() {
		return regimenFiscalDesc;
	}

	public void setRegimenFiscalDesc(String regimenFiscalDesc) {
		this.regimenFiscalDesc = regimenFiscalDesc;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getPersId() {
		return persId;
	}

	public void setPersId(int persId) {
		this.persId = persId;
	}

	public String getFecIngreso() {
		return fecIngreso;
	}

	public void setFecIngreso(String fecIngreso) {
		this.fecIngreso = fecIngreso;
	}
}