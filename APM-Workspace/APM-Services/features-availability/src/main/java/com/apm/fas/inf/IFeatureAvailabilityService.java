package com.apm.fas.inf;

import java.util.List;

import com.apm.fas.dto.NavigationDTO;

public interface IFeatureAvailabilityService {

	List<NavigationDTO> getEnabledFeatures(long userGroupId);

}
