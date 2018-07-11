package com.apm.historian.dac.read;

import com.apm.historain.dac.constants.HistorianDACConstants;

public class DataSample
{
	final public short  sValue;
	final public int    iValue;
	final public float  fValue;
	final public double dValue;
	final public String strValue;
	final public short  dataType;
	final public long   timeStamp;
	final public short  quality;

	public DataSample(short sValue, int iValue, float fValue, double dValue, String strValue, short dataType,
	        long timeStamp, short quality)
	{
		this.sValue = sValue;
		this.iValue = iValue;
		this.fValue = fValue;
		this.dValue = dValue;
		this.strValue = strValue;
		this.dataType = dataType;
		this.timeStamp = timeStamp;
		this.quality = quality;
	}

	public String toStringValue()
	{
		switch (dataType)
		{
			case HistorianDACConstants.ihuShort:
				return String.valueOf(sValue);
			case HistorianDACConstants.ihuInteger:
				return String.valueOf(iValue);
			case HistorianDACConstants.ihuFloat:
				return String.valueOf(fValue);
			case HistorianDACConstants.ihuDoubleFloat:
				return String.valueOf(dValue);
			case HistorianDACConstants.ihuString:
				return strValue;
			default:
				return "";
		}
	}

	public double toDoubleValue()
	{
		switch (dataType)
		{
			case HistorianDACConstants.ihuShort:
				return sValue;
			case HistorianDACConstants.ihuInteger:
				return iValue;
			case HistorianDACConstants.ihuFloat:
				return fValue;
			case HistorianDACConstants.ihuDoubleFloat:
				return dValue;
			case HistorianDACConstants.ihuString:
				return Double.parseDouble(strValue);
			default:
				return 0.0D;
		}
	}

	public float toFloatValue()
	{
		switch (dataType)
		{
			case HistorianDACConstants.ihuShort:
				return sValue;
			case HistorianDACConstants.ihuInteger:
				return iValue;
			case HistorianDACConstants.ihuFloat:
				return fValue;
			case HistorianDACConstants.ihuDoubleFloat:
				return (float) dValue;
			case HistorianDACConstants.ihuString:
				return Float.parseFloat(strValue);
			default:
				return 0.0F;
		}
	}

	public int toIntValue()
	{
		switch (dataType)
		{
			case HistorianDACConstants.ihuShort:
				return sValue;
			case HistorianDACConstants.ihuInteger:
				return iValue;
			case HistorianDACConstants.ihuFloat:
				return (int) fValue;
			case HistorianDACConstants.ihuDoubleFloat:
				return (int) dValue;
			case HistorianDACConstants.ihuString:
				return Integer.parseInt(strValue);
			default:
				return 0;
		}
	}

	public short toShortValue()
	{
		switch (dataType)
		{
			case HistorianDACConstants.ihuShort:
				return sValue;
			case HistorianDACConstants.ihuInteger:
				return (short) iValue;
			case HistorianDACConstants.ihuFloat:
				return (short) fValue;
			case HistorianDACConstants.ihuDoubleFloat:
				return (short) dValue;
			case HistorianDACConstants.ihuString:
				return Short.parseShort(strValue);
			default:
				return 0;
		}
	}

}
