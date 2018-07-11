package com.apm.datarw.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AssetDTO {

    private String sourceKey;
    private String assetName;
    private String assetType;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updatedDate;

    private String assetLevel;
    private String parent;
    private List<AssetDTO> children;


    public List<AssetDTO> getChildren() {
        return children;
    }

    public void setChildren(List<AssetDTO> children) {
        this.children = children;
    }

    private DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");

    public AssetDTO() {
    }

    public AssetDTO(String sourceKey, String assetName, String assetType, String description, Date createdDate, Date updatedDate, String assetLevel) {
        this.sourceKey = sourceKey;
        this.assetName = assetName;
        this.assetType = assetType;
        this.description = description;
        this.createdDate = dateformat.format(createdDate);
        this.updatedDate = dateformat.format(updatedDate);
        this.assetLevel = assetLevel;
    }

    public AssetDTO(String sourceKey, String assetName, String assetType, String description, String parent, Date createdDate, Date updatedDate, String assetLevel) {
        this(sourceKey, assetName, assetType, description, createdDate, updatedDate, assetLevel);
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssetDTO assetDTO = (AssetDTO) o;

        if (sourceKey != null ? !sourceKey.equals(assetDTO.sourceKey) : assetDTO.sourceKey != null) return false;
        if (assetName != null ? !assetName.equals(assetDTO.assetName) : assetDTO.assetName != null) return false;
        if (assetType != null ? !assetType.equals(assetDTO.assetType) : assetDTO.assetType != null) return false;
        if (description != null ? !description.equals(assetDTO.description) : assetDTO.description != null)
            return false;
        if (parent != null ? !parent.equals(assetDTO.parent) : assetDTO.parent != null) return false;
        if (createdDate != null ? !createdDate.equals(assetDTO.createdDate) : assetDTO.createdDate != null)
            return false;
        if (updatedDate != null ? !updatedDate.equals(assetDTO.updatedDate) : assetDTO.updatedDate != null)
            return false;
        return assetLevel != null ? assetLevel.equals(assetDTO.assetLevel) : assetDTO.assetLevel == null;
    }

    @Override
    public int hashCode() {
        int result = sourceKey != null ? sourceKey.hashCode() : 0;
        result = 31 * result + (assetName != null ? assetName.hashCode() : 0);
        result = 31 * result + (assetType != null ? assetType.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        result = 31 * result + (assetLevel != null ? assetLevel.hashCode() : 0);
        return result;
    }

    public String getSourceKey() {
        return sourceKey;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getAssetLevel() {
        return assetLevel;
    }

    public void setAssetLevel(String assetLevel) {
        this.assetLevel = assetLevel;
    }
}
