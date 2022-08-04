package com.aura.qamm.dto.facturacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class ConceptoDTO implements Serializable {


	private BigDecimal importeTotal;
	private BigDecimal importe;
	private String concepto;
	private String unidad;
	private String unidadDesc;
	private String claveProdServ;
	private BigDecimal cantidad;
	private BigDecimal precioUnitario;
	private String descripcion;

	private List<ImpuestoDTO> impuestos;
	//private List<RetencionDTO> retenciones;



	private String ivaTrasladado16;
	private BigDecimal ivaTrasladado16Monto;

	private String ivaRetenido6;
	private BigDecimal ivaRetenido6Monto;

	private String codigoSat;
	private String descripcionSat;
	private String descripcionConcepto;
	private String descripcionConceptoAdicional;

	private String impuestosDescripcion;

	public ConceptoDTO(){
		
	}
	
	public ConceptoDTO(String demo){

		
		this.concepto = "80111701 ";
		this.unidad = "E48"; //"E48";
		this.unidadDesc = "Unidad de servicio";
		this.claveProdServ="84111701"; //50111515

		this.cantidad = new BigDecimal(1);
		this.precioUnitario = new BigDecimal("10000");
		this.descripcion = "Comisión de venta al Publico en General de los Folios";//"Servicios de contratación de personal SUMINISTRO DE PERSONAL";

		this.importeTotal = new BigDecimal("10000");
		this.importe = new BigDecimal("10000");

		List<ImpuestoDTO> impuesto = new ArrayList<>();
		impuesto.add(new ImpuestoDTO("demo"));
		this.impuestos = impuesto ;

		this.impuestosDescripcion = "IVA Trasladado 16%";

		this.codigoSat = "84131801";
		this.descripcionSat = "Fondos de pensiones autodirigidos o patrocinados por el empleador";


	}

	public BigDecimal getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(BigDecimal importeTotal) {
		this.importeTotal = importeTotal;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public String getClaveProdServ() {
		return claveProdServ;
	}

	public void setClaveProdServ(String claveProdServ) {
		this.claveProdServ = claveProdServ;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUnidadDesc() {
		return unidadDesc;
	}

	public void setUnidadDesc(String unidadDesc) {
		this.unidadDesc = unidadDesc;
	}

	public List<ImpuestoDTO> getImpuestos() {
		return impuestos;
	}

	public void setImpuestos(List<ImpuestoDTO> impuestos) {
		this.impuestos = impuestos;
	}

	public String getIvaTrasladado16() {
		return ivaTrasladado16;
	}

	public void setIvaTrasladado16(String ivaTrasladado16) {
		this.ivaTrasladado16 = ivaTrasladado16;
	}

	public BigDecimal getIvaTrasladado16Monto() {
		return ivaTrasladado16Monto;
	}

	public void setIvaTrasladado16Monto(BigDecimal ivaTrasladado16Monto) {
		this.ivaTrasladado16Monto = ivaTrasladado16Monto;
	}

	public String getIvaRetenido6() {
		return ivaRetenido6;
	}

	public void setIvaRetenido6(String ivaRetenido6) {
		this.ivaRetenido6 = ivaRetenido6;
	}

	public BigDecimal getIvaRetenido6Monto() {
		return ivaRetenido6Monto;
	}

	public void setIvaRetenido6Monto(BigDecimal ivaRetenido6Monto) {
		this.ivaRetenido6Monto = ivaRetenido6Monto;
	}

	public String getCodigoSat() {
		return codigoSat;
	}

	public void setCodigoSat(String codigoSat) {
		this.codigoSat = codigoSat;
	}

	public String getDescripcionSat() {
		return descripcionSat;
	}

	public void setDescripcionSat(String descripcionSat) {
		this.descripcionSat = descripcionSat;
	}

	public String getDescripcionConcepto() {
		return descripcionConcepto;
	}

	public void setDescripcionConcepto(String descripcionConcepto) {
		this.descripcionConcepto = descripcionConcepto;
	}

	public String getDescripcionConceptoAdicional() {
		return descripcionConceptoAdicional;
	}

	public void setDescripcionConceptoAdicional(String descripcionConceptoAdicional) {
		this.descripcionConceptoAdicional = descripcionConceptoAdicional;
	}

	public String getImpuestosDescripcion() {
		return impuestosDescripcion;
	}

	public void setImpuestosDescripcion(String impuestosDescripcion) {
		this.impuestosDescripcion = impuestosDescripcion;
	}



	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}
}
