package com.apm.datarw.utils;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apm.datarw.dto.HistorianDataDTO;
import com.apm.datarw.exceptions.HistorianDACException;
import com.apm.datarw.exceptions.OnPremiseException;
import com.apm.historian.dac.read.DataSample;
import com.google.common.collect.Lists;

public class HistorianWrapper {

	private static Long maxRetries = 5L;
	private static Integer maxQueryTags = 15;

	private static final Logger log=LoggerFactory.getLogger(HistorianWrapper.class);

	private void loadDllFiles(){
		log.info("Loading DLL File");
		System.loadLibrary("HistorianJNIDLL");
		log.info("DLL File Load :: Complete");
	}

	private List<String> createTagList(List<String> tagList, String assetId){
		log.info("Creating tag list for historian [AssetId.TagName]");
		tagList= tagList.stream().map(tagName -> {
			String modifiedTagName = String.format("%s.%s",assetId, tagName);
			log.debug(String.format("%s ----> %s", tagName, modifiedTagName));
			return modifiedTagName;
		}).collect(Collectors.toList());
		log.debug("Modified Tags:: " + tagList);
		log.info("Total Tags = " + tagList.size());
		return tagList;
	}

	private Map<String, HistorianDataDTO> getMappedVector(Map<String, HistorianDataDTO> dataMap, DataSample[][] nativeOutput, List<String> tagList){
		log.info("Creating mapped vectors Map<String[tagName], HistorianDataDTO>");
		IntStream
		.range(0, tagList.size())
		.forEach(index -> {
			log.info("Fetching data for = " + tagList.get(index));
			//If Historian is not able to find data then it sets the value as null.
			if (! Objects.isNull(nativeOutput[index])){
				HistorianDataDTO historianData = new HistorianDataDTO();
				historianData.setTagName(tagList.get(index));
				Vector<String> dataValues = new Vector<>();
				Vector<String> qualityFlags = new Vector<>();
				Vector<Long> dateTime = new Vector<>();
				log.info(tagList.get(index) + " data length = " + nativeOutput[index].length);
				Arrays.asList(nativeOutput[index]).stream().forEach(dataObj -> {
					//Convert timestamp from seconds to milliseconds.
					dateTime.add(dataObj.timeStamp * 1000);
					//Get Value.
					dataValues.add( dataObj.toStringValue() );
					//Get Flag
					qualityFlags.add( String.valueOf(dataObj.quality) );
				});
				historianData.setDataValues(dataValues);
				historianData.setDateTime(dateTime);
				historianData.setQualityFlags(qualityFlags);
				historianData.setTagName(tagList.get(index));
				dataMap.put(tagList.get(index), historianData);
			}else{
				log.info(String.format("DataSample for %s is null. [No data in historian]", tagList.get(index)));
			}
		});
		log.info("::COMPLETED:: Creating mapped vectors Map<String[tagName], HistorianDataDTO>");
		return dataMap;
	}

	private Map<String, HistorianDataDTO> runInParallel(List<String> tagList, Long serverhandle, Long startDate, Long endDate, Long interval, HistorianUtil historianUtil){
		log.info("Running Historian Fetch in parallel");
		Object[] tagErrors = { "", "", "" };
		Map<String, HistorianDataDTO> output = new HashMap<String, HistorianDataDTO>();
		List<List<String>> requests = Lists.partition(tagList, maxQueryTags);
		requests
		.parallelStream()
		.map(requestList -> {
			Map<String, HistorianDataDTO> historianData = new HashMap<>();
			DataSample[][] ds = null;
			try {
				ds = historianUtil.getIhuReadMultiTagInterpData(serverhandle, requestList.toArray(), interval, startDate, endDate, maxRetries, tagErrors);
			} catch (Exception e) {
				log.error("Failed to fetch data for tags:: " + requestList.toString(), e);
			}
			if(! Objects.isNull(ds)){
				return getMappedVector(historianData, ds, requestList);
			}
			else
				return null;
		})
		.collect(Collectors.toList())
		.stream()
		.forEach(map -> {
			output.putAll(map);
		});
		log.info("::COMPLETED:: Running Historian Fetch in parallel");
		return output;
	}

	private Map<String, HistorianDataDTO> getHistorianData(List<String> tagList, String assetId, Long startDate, Long endDate, Long interval, String serverAddress, String serverUserName, String serverPassword) throws Exception{
		//Server Address, Hostname, Port
		HistorianUtil historianUtil = new HistorianUtil();//Application.getHistorianUtil();
		Map<String, HistorianDataDTO> historianData = new HashMap<>();
		long serverhandle = historianUtil.getHistorianServerHandle(serverAddress, serverUserName, serverPassword);
		if (serverhandle == -1){
			//Error connecting to historian.
		}else{
			//Successful connection to historian.
			List<String> esnTagList = this.createTagList(tagList, assetId);
			if(esnTagList.size() > maxQueryTags){
				historianData = runInParallel(esnTagList, serverhandle, startDate, endDate, interval, historianUtil);
			}else{
				Object[] tagErrors = { "", "", "" };
				DataSample[][] ds = historianUtil.getIhuReadMultiTagInterpData(serverhandle, esnTagList.toArray(), interval, startDate, endDate, maxRetries, tagErrors);
				historianData = getMappedVector(historianData, ds, esnTagList);
			}
		}
		final Set<String> mapKeys = historianData.keySet();
		log.info("Tags Not Present in Historian :: " + tagList.stream().filter(elem -> {
			return ! mapKeys.contains(String.format("%s.%s", assetId, elem));
		}).collect(Collectors.toList()));
		return historianData;
	}

	private Map<String, String> getDataMapStream(List<String> tagList, String assetId, Long startDate, Long endDate, Long interval, String serverAddress, String serverUserName, String serverPassword) throws Exception{
		Map<String, String> responseObject = new HashMap<>();
		try{
			Map<String, HistorianDataDTO> dataMap = this.getHistorianData(tagList, assetId, startDate, endDate, interval, serverAddress, serverUserName, serverPassword);
			if (dataMap.keySet().size() == 0 ){
				throw new HistorianDACException("No data available in historian.", this.getClass().getName());
			}else{

				//Returning CSV Content
				Vector<Long> dateTimeVectors  = new Vector<Long>() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 5364888617780300208L;

					@Override
					public synchronized boolean addAll(Collection<? extends Long> arg0) {
						@SuppressWarnings("unchecked")
						Vector<Long> v = (Vector<Long>) arg0;
						for (Long string : v) {
							if (contains(string)) {
								return false;
							}
						}
						return super.addAll(v);
					}
				};

				//Build a CSV String
				StringBuilder content = new StringBuilder();
				content.append(DataConstants.DATE_TIME_COLUMN).append(DataConstants.COMMA_SEPERATOR);
				tagList.forEach(elem -> {
					content
					.append(elem)
					.append(DataConstants.COMMA_SEPERATOR)
					.append(elem+DataConstants.FLAG_SUFFIX)
					.append(DataConstants.COMMA_SEPERATOR);
				});
				//Delete the last Comma, which is extra
				content.deleteCharAt(content.lastIndexOf(DataConstants.COMMA_SEPERATOR));
				//New Line
				content.append(System.lineSeparator());
				tagList.forEach(elem -> {
					String tagName = String.format("%s.%s", assetId, elem);
					if (dataMap.containsKey(tagName)){
						dateTimeVectors.addAll(dataMap.get(tagName).getDateTime());
					}
				});

				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DataConstants.INPUT_DATE_TIME_FMT);
				simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
				dateTimeVectors.forEach(dateElem ->{
					content.append(simpleDateFormat.format(dateElem)).append(DataConstants.COMMA_SEPERATOR);
					tagList.forEach(elem -> {
						String tagName = String.format("%s.%s", assetId, elem);
						try {
							if(dataMap.containsKey(tagName)){
								Vector<Long> temp_vector =  dataMap.get(tagName).getDateTime();
								if (null != temp_vector){
									if (temp_vector.indexOf(dateElem) != -1){
										content
										.append(dataMap.get(tagName).getDataValues().get(temp_vector.indexOf(dateElem)))
										.append(DataConstants.COMMA_SEPERATOR)
										.append(dataMap.get(tagName).getQualityFlags().get(temp_vector.indexOf(dateElem)));
									}else{
										throw new OnPremiseException(String.format("Data not present in historian for datetime[%s].", dateElem));
									}
								}else{
									throw new OnPremiseException(String.format("Data not present in historian for tag[%s].", elem));
								}
							}else{
								throw new OnPremiseException(String.format("Tag %s not present in historian.", tagName));
							}
						} catch (OnPremiseException e) {
							content.append(DataConstants.COMMA_SEPERATOR);
						}
						content.append(DataConstants.COMMA_SEPERATOR);
					});
					content.deleteCharAt(content.lastIndexOf(DataConstants.COMMA_SEPERATOR));
					content.append(System.lineSeparator());
				});
				responseObject.put(DataConstants.INPUT_DATA, content.toString().trim());
				responseObject.put(DataConstants.SUCCESS, DataConstants.LITERAL_TRUE);
			}
		}catch(Exception e){
			responseObject.put(DataConstants.FAILURE, DataConstants.LITERAL_TRUE);
			responseObject.put(DataConstants.ERROR, DataConstants.DATA_FETCH_FAILED);
			responseObject.put(DataConstants.ERROR_MESSAGE, e.getMessage());
			log.error("[Historian] Data Fetch Failed. ", e);
		}
		return responseObject;
	}

	/**
	 * Fetch data from historian and export as a comma seperated string.
	 * @param tagList List<String> of tag names. i.e ["Tag-1","Tag-2"]
	 * @param assetId ESN/Asset ID. In case of Historian, usual format is <Asset-ID>.<Tag-Name>
	 * @param startDate Start Date/Time in Epoch. [GMT] - Machine can be anywhere, we don't want to use local timezones.
	 * @param endDate End Date/Time in Epoch. [GMT] - Machine can be anywhere, we don't want to use local timezones.
	 * @param interval - Data Interval
	 * @param serverAddress - Historian Server Address
	 * @param serverUserName - Username for Historian Server
	 * @param serverPassword - Password for Historian Server
	 * @return Returns a map containing <b>success</b> in case historian is able to return data along with <b>input_data</b> else <b>failure</b>
	 * 		key is returned in the map.
	 * @throws Exception 
	 */
	public static Map<String, String> getDataMap(List<String> tagList, String assetId, Long startDate, Long endDate, Long interval, String serverAddress, String serverUserName, String serverPassword) throws Exception{
		HistorianWrapper hw = new HistorianWrapper();
		hw.loadDllFiles();
		//State date and end date is in Epoch (ms)
		//Historian needs epoch in seconds
		return hw.getDataMapStream(tagList, assetId, startDate/1000, endDate/1000, interval, serverAddress, serverUserName, serverPassword);
	}

}
