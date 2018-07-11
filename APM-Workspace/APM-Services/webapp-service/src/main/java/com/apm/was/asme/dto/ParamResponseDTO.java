package com.apm.was.asme.dto;

import java.util.List;

public class ParamResponseDTO {
	private List<ASMEInputDTO> inputParam;
	private List<ASMEInputDTO> outputParam;
	public List<ASMEInputDTO> getInputParam() {
		return inputParam;
	}
	public void setInputParam(List<ASMEInputDTO> inputParam) {
		this.inputParam = inputParam;
	}
	public List<ASMEInputDTO> getOutputParam() {
		return outputParam;
	}
	public void setOutputParam(List<ASMEInputDTO> outputParam) {
		this.outputParam = outputParam;
	}

}
