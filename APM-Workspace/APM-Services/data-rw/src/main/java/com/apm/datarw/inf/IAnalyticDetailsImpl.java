package com.apm.datarw.inf;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.apm.datarw.dto.PythonRequestDTO;
import com.apm.datarw.entity.AnalyticInfoEntity;
import com.apm.datarw.entity.AnalyticRunEntity;

@Service
public interface IAnalyticDetailsImpl {

	PythonRequestDTO getAnalyticDetails(JSONObject jsonObject);
	List<Map<String, String>> fetchHistorianData(AnalyticInfoEntity analyticInfoEntity);
	PythonRequestDTO facadeForScheduler(AnalyticRunEntity jobObject);
	List<Map<String, String>> fetchCentralHistorianData(AnalyticInfoEntity analyticInfoEntity) throws ParseException;

}
