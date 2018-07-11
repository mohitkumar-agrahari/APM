package com.apm.was.inf;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.apm.was.dto.AssetModelDTO;
import com.apm.was.dto.ImageDetailsDTO;
import com.apm.was.dto.MessageDTO;

public interface IAssetModel {

	List<AssetModelDTO> getAssetData();
	MessageDTO saveAssetImage(MultipartFile imageFile, String analyticName, String assetType) throws IOException;
	ImageDetailsDTO getImageData(String levelDisplayName);
	List<AssetModelDTO> getHierarchyBySourcekey(String sourceKey);

}
