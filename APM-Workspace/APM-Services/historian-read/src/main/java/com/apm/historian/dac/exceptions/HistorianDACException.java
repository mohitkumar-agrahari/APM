/*******************************************************************************
 * Copyright (C) 2012 General Electric Company. All rights reserved. � General
 * Electric Company, 2012. All rights reserved.
 ******************************************************************************/

package com.apm.historian.dac.exceptions;

public class HistorianDACException extends Exception
{

    private static final long serialVersionUID = -8235784945166887574L;
	private String      gstrExceptionMessage = null;
	public String       gErrLocation;
	protected Throwable gException           = null;

	public HistorianDACException(String astrExceptionMessage, String aErrLocation)
	{
		gstrExceptionMessage = astrExceptionMessage;
		gErrLocation = aErrLocation;
	}

	public HistorianDACException(Throwable aException, String aErrLocation)
	{
		gException = aException;
		gErrLocation = aErrLocation;
	}

	/**
	 * Returns the errort message string of this throwable object.
	 * 
	 * @return the error message string of this Throwable object if it was created with an
	 *         error message string; or null if it was created with no error message.
	 */
	public String getMessage()
	{
		return gstrExceptionMessage;
	}

	/**
	 * Returns the Throwble object by converting it into string
	 * 
	 * @return Throwble object
	 */
	public String gettoString()
	{
		if(gException != null)
		{
			return gException.toString();	
		}else
		{
			return null;
		}
		
	}

	/**
	 * Returns location of Exception
	 * 
	 * @return location of Exception
	 */
	public String getErrLocation()
	{
		return gErrLocation;
	}
}
