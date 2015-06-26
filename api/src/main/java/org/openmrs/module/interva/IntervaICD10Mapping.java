package org.openmrs.module.interva;

import org.openmrs.BaseOpenmrsData;

public class IntervaICD10Mapping extends BaseOpenmrsData {

	private int intervaICD10MappingId;
	
	private String intervaResult;
	
	private String intervaCode;
	
	private String icd10Code;
	
	private String displayText;
	
	private String description;
		
    public IntervaICD10Mapping() {
		
	}

	public int getIntervaICD10MappingId() {
		return intervaICD10MappingId;
	}

	public void setIntervaICD10MappingId(int intervaICD10MappingId) {
		this.intervaICD10MappingId = intervaICD10MappingId;
	}

	public String getIntervaResult() {
		return intervaResult;
	}

	public void setIntervaResult(String intervaResult) {
		this.intervaResult = intervaResult;
	}

	public String getIntervaCode() {
		return intervaCode;
	}

	public void setIntervaCode(String intervaCode) {
		this.intervaCode = intervaCode;
	}

	public String getIcd10Code() {
		return icd10Code;
	}

	public void setIcd10Code(String icd10Code) {
		this.icd10Code = icd10Code;
	}

	public String getDisplayText() {
		return displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Integer getId() {
		return intervaICD10MappingId;
	}

	@Override
	public void setId(Integer id) {
		intervaICD10MappingId = id;
	}
}
