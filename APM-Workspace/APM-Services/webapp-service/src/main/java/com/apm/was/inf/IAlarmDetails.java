package com.apm.was.inf;

import java.util.List;

import com.apm.was.dto.AlarmCountDTO;
import com.apm.was.dto.AlertResponseDTO;
import com.apm.was.dto.MessageDTO;


public interface IAlarmDetails {


List<AlertResponseDTO> getAlertData();
AlarmCountDTO getAssetAlertCount(String levelName);
MessageDTO updateAssetHealth();
		
		







}
