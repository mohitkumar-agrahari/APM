package com.apm.was.asme.inf;

import com.apm.was.asme.dto.ParamResponseDTO;
import com.apm.was.asme.dto.SaveInputDTO;
import com.apm.was.dto.MessageDTO;


public interface IASMEDetails {

	ParamResponseDTO getAssetParam(Long analyticId);

	MessageDTO saveAssetInputValue(SaveInputDTO saveInputDTO);



}
