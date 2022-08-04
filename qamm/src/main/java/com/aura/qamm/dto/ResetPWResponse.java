/**
 * 
 */
package com.aura.qamm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Acer
 *
 */
public class ResetPWResponse {
	
	@JsonProperty("msgExitoEng")
	private String msgExitoEng;
	
	@JsonProperty("msgExitoEsp")
	private String msgExitoEsp;
	
	@JsonProperty("msgErrorEng")
	private String msgErrorEng;
	
	@JsonProperty("msgErrorEsp")
	private String msgErrorEsp;

	/**
	 * @return the msgExitoEng
	 */
	public String getMsgExitoEng() {
		return msgExitoEng;
	}

	/**
	 * @param msgExitoEng the msgExitoEng to set
	 */
	public void setMsgExitoEng(String msgExitoEng) {
		this.msgExitoEng = msgExitoEng;
	}

	/**
	 * @return the msgExitoEsp
	 */
	public String getMsgExitoEsp() {
		return msgExitoEsp;
	}

	/**
	 * @param msgExitoEsp the msgExitoEsp to set
	 */
	public void setMsgExitoEsp(String msgExitoEsp) {
		this.msgExitoEsp = msgExitoEsp;
	}

	/**
	 * @return the msgErrorEng
	 */
	public String getMsgErrorEng() {
		return msgErrorEng;
	}

	/**
	 * @param msgErrorEng the msgErrorEng to set
	 */
	public void setMsgErrorEng(String msgErrorEng) {
		this.msgErrorEng = msgErrorEng;
	}

	/**
	 * @return the msgErrorEsp
	 */
	public String getMsgErrorEsp() {
		return msgErrorEsp;
	}

	/**
	 * @param msgErrorEsp the msgErrorEsp to set
	 */
	public void setMsgErrorEsp(String msgErrorEsp) {
		this.msgErrorEsp = msgErrorEsp;
	}
	
}
