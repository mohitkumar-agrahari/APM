package com.apm.was.inf;

import java.util.List;
import com.apm.was.dto.TubeThicknessDegradationDTO;
import com.apm.was.dto.TubeThicknessRiskDTO;
import com.apm.was.dto.VelocityCsvDTO;

public interface IBtlDetails {

	List<VelocityCsvDTO> getHeatMapData();

	List<TubeThicknessRiskDTO> getLineChartCSVData();

	List<TubeThicknessDegradationDTO> getBarChartCSVData();

}
