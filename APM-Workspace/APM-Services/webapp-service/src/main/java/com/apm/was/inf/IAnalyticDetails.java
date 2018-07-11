package com.apm.was.inf;

import java.io.IOException;
import java.util.List;
import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.apm.was.dto.AlarmCountDTO;
import com.apm.was.dto.AlertResponseDTO;
import com.apm.was.dto.AnalyticDetailsDTO;
import com.apm.was.dto.AssetHealthRespDTO;
import com.apm.was.dto.AssetModelDTO;
import com.apm.was.dto.ImageDetailsDTO;
import com.apm.was.dto.MessageDTO;
import com.apm.was.dto.OuttagResDTO;
import com.apm.was.entity.AnalyticInfoEntity;
import com.apm.was.entity.AnalyticOutputEntity;


public interface IAnalyticDetails {




List<AnalyticDetailsDTO> getAnalyticDetailData();








}
