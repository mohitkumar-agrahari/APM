package com.apm.datarw.exceptions;

public class OnPremiseException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2753222334718314562L;
	public OnPremiseException(){
		super("Unwanted Exception :: OnPremise");
	}
	public OnPremiseException(String message){
		super(message);
	}
}
