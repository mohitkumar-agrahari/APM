package com.apm.datarw.utils;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apm.historian.dac.exceptions.HistorianDACException;
import com.apm.historian.dac.read.DataSample;

public class HistorianUtil
{
	private final Logger log = LoggerFactory.getLogger(HistorianUtil.class);
	private static final Long RETRY_ATTEMPTS = 5L;
	private static final Long MIN_RETRY_SLEEP = 1L;
	private static final Long MAX_RETRY_SLEEP = 3L;
	private static final Long UAPI_TIMEOUT = 5L;
	private static Map<String, Long> HistorianConnectionMap = Collections.synchronizedMap(new HashMap<String, Long>());

	/**
	 * Native method for connecting to Historian.
	 * 
	 * @param nodeName
	 *            the node name
	 * 
	 * @param retryAttempts
	 *            retry attempts
	 * 
	 * @param minRetrySleep
	 *            minimum retry sleep
	 * 
	 * @param maxRetrySleep
	 *            maximum retry sleep
	 * 
	 * @param uapiTimeOut
	 *            API time out
	 * 
	 * @return the long
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public native long historianConnect(String nodeName, String userName, String password, long lngRetryAttempts,
			long lngMminRetrySleep, long lngMaxRetrySleep, long lngUapiTimeOut) throws Exception;

	/**
	 * Native method for disconnecting the historian.
	 * 
	 * @param serverhandle
	 * @return
	 */
	public native long historianDisconnect(long serverhandle);

	/**
	 * Native method to retrieve current value.
	 * 
	 * @param pstrDSN
	 *            Historian Name.
	 * @param username
	 *            User Name.
	 * @param password
	 *            Password.
	 * @param tagArray
	 *            List of Tags.
	 * @param retryAttempts
	 *            Retry Attempts.
	 * @param minRetrySleep
	 *            MinRetry Sleep.
	 * @param maxRetrySleep
	 *            MAxRetrySleep.
	 * @param uapiTimeOut
	 *            UAPITimeOut.
	 * @return Object results in form string in Object[]
	 * @throws Exception
	 */
	public native DataSample[] getIhuReadCurrentValue(long serverHandle, Object[] tagArray, long lngRetryAttempts,
			Object[] tagErrors) throws Exception;

	/**
	 * Native method to retrieve raw data for multiple TSNs.
	 * 
	 * @param handle
	 *            long value
	 * @param tagnames
	 *            name of the tag
	 * @param startTime
	 *            start time in seconds
	 * @param endTime
	 *            end time in seconds
	 * @param lngretryAttempts
	 *            long value
	 * @return
	 * @throws Exception
	 */
	public native DataSample[][] getIhuReadMultiTagRawData(long handle, Object[] tagnames, long startTime,
			long endTime, long lngretryAttempts, Object[] tagErrors) throws Exception;

	/**
	 * Native method to retrieve interpolated data for multiple TSNs.
	 * 
	 * @param handle
	 *            long value
	 * @param tagnames
	 *            name of the tag
	 * @param startTime
	 *            start time in seconds
	 * @param endTime
	 *            end time in seconds
	 * @param lngretryAttempts
	 *            long value
	 * @return
	 * @throws Exception
	 */
	public native DataSample[][] getIhuReadMultiTagInterpData(long handle, Object[] tagnames, long interval,
			long startTime, long endTime, long lngretryAttempts, Object[] tagErrors) throws Exception;

	/**
	 * This method provides connection pooling mechanism for Historian connection.
	 * 
	 * @param nodeName
	 *            the node name
	 * 
	 * @param retryAttempts
	 *            retry attempts
	 * 
	 * @param minRetrySleep
	 *            minimum retry sleep
	 * 
	 * @param maxRetrySleep
	 *            maximum retry sleep
	 * 
	 * @param uapiTimeOut
	 *            API time out
	 * 
	 * @return the long
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public long getHistorianServerHandle(String pstrDSN, String userName, String password) throws HistorianDACException
	{
		long serverHandle = -1L;
		try
		{
			if (pstrDSN == null || pstrDSN.trim().equals(""))
			{
				return serverHandle;
			}
			if (!HistorianConnectionMap.containsKey(pstrDSN))
			{
				long lBegin = System.currentTimeMillis();
				serverHandle = historianConnect(pstrDSN, userName, password, RETRY_ATTEMPTS, MIN_RETRY_SLEEP,
						MAX_RETRY_SLEEP, UAPI_TIMEOUT);
				long lNow = System.currentTimeMillis();
				log.info("Time took to connect to Historian:" + ((double) (lNow - lBegin) / 1000)
						+ " sec");
				if (serverHandle != 0)
				{
					log.debug("Created Server handle in getHistorianServerHandle -> "
							+ serverHandle);
					HistorianConnectionMap.put(pstrDSN, new Long(serverHandle));
				}
			}
			else
			{
				serverHandle = HistorianConnectionMap.get(pstrDSN);
				log.error("Using existing Server handle from the Map -> " + serverHandle);
			}
		}
		catch(UnsatisfiedLinkError ex)
		{
			System.out.println(ex.getLocalizedMessage());
			log.error("UnsatisfiedLinkError "+ex.getMessage() + " At " + ex.getStackTrace()[0]);
			serverHandle = -1L;
			throw new HistorianDACException("UnsatisfiedLinkError "+ex.getMessage(), "HistorianUtil - getHistorianServerHandle");
		}
		catch (Exception ex)
		{
			log.error(ex.getMessage() + " At " + ex.getStackTrace()[0]);
			historianDisconnect(serverHandle);
			serverHandle = -1L;
			throw new HistorianDACException(ex.getMessage(), "HistorianUtil - getHistorianServerHandle");

		}

		return serverHandle;
	}


}