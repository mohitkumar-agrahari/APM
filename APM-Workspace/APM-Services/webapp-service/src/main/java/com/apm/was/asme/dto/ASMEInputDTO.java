package com.apm.was.asme.dto;

import java.util.List;

public class ASMEInputDTO {
	
	private String header;
	private List<ParamDTO> parameter;
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public List<ParamDTO> getParameter() {
		return parameter;
	}
	public void setParameter(List<ParamDTO> parameter) {
		this.parameter = parameter;
	}

}
