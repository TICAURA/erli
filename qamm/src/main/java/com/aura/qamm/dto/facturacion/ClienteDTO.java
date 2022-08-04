package com.aura.qamm.dto.facturacion;

import java.io.Serializable;

public class ClienteDTO implements Serializable {

	private Long idCliente;
	private String rfc;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private String contacto;
	private String nombreCompleto;
	private String tipoPersona;
	private String usoCFDI;
	private String usoCFDIDesc;
	private String curp;
	private String idEmpleadoSTP;
	private String correoElectronico;
	private String nss;
	private String periodicidad;
	
	public ClienteDTO(){
		
	}
	
	public ClienteDTO(String demo){
		
		this.idCliente = 1L;
		this.rfc = "CUGV9312315H5";
		this.curp = "CUGV931231HVZVRC09";
		this.nombreCompleto = "Miguel Angele Lopez Jimenez";
		this.usoCFDI = "P01"; //G03 or P01 "Sin definir";
		this.usoCFDIDesc = "Sin definir";
		this.correoElectronico ="monteromalj@gmail.com";
		this.idEmpleadoSTP = "2";
		this.periodicidad = "Q";
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getUsoCFDI() {
		return usoCFDI;
	}

	public void setUsoCFDI(String usoCFDI) {
		this.usoCFDI = usoCFDI;
	}

	public String getUsoCFDIDesc() {
		return usoCFDIDesc;
	}

	public void setUsoCFDIDesc(String usoCFDIDesc) {
		this.usoCFDIDesc = usoCFDIDesc;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getIdEmpleadoSTP() {
		return idEmpleadoSTP;
	}

	public void setIdEmpleadoSTP(String idEmpleadoSTP) {
		this.idEmpleadoSTP = idEmpleadoSTP;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getNss() {
		return nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public String getPeriodicidad() {
		return periodicidad;
	}

	public void setPeriodicidad(String periodicidad) {
		this.periodicidad = periodicidad;
	}
}