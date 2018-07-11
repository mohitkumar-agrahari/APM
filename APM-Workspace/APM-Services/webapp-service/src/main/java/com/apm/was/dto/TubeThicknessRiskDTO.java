package com.apm.was.dto;



public class TubeThicknessRiskDTO {
	private String Date;
	private String LOC_IDENTIFIER;
	  private String Z_HEIGHT;
	
	  private String Forecast_thickness;
	  private String Risk;
	
	public String getLOC_IDENTIFIER() {
		return LOC_IDENTIFIER;
	}
	public void setLOC_IDENTIFIER(String lOC_IDENTIFIER) {
		LOC_IDENTIFIER = lOC_IDENTIFIER;
	}
	public String getZ_HEIGHT() {
		return Z_HEIGHT;
	}
	public void setZ_HEIGHT(String z_HEIGHT) {
		Z_HEIGHT = z_HEIGHT;
	}
	
	public String getRisk() {
		return Risk;
	}
	public void setRisk(String risk) {
		Risk = risk;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getForecast_thickness() {
		return Forecast_thickness;
	}
	public void setForecast_thickness(String forecast_thickness) {
		Forecast_thickness = forecast_thickness;
	}

	
	 
	  

}
