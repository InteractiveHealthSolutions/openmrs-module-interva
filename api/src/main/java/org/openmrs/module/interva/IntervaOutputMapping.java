package org.openmrs.module.interva;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;

public class IntervaOutputMapping extends BaseOpenmrsData {

	private int intervaOutputMappingId;
	
	private String intervaOutputVariable;

	private int intervaOutputIndex;

	private Integer conceptId;
	
	private Concept concept;
	
	private String description;
	
	private Boolean observation;
	
    public IntervaOutputMapping() {
		
	}
    

	public int getIntervaOutputMappingId() {
		return intervaOutputMappingId;
	}


	public void setIntervaOutputMappingId(int intervaOutputMappingId) {
		this.intervaOutputMappingId = intervaOutputMappingId;
	}


	public String getIntervaOutputVariable() {
		return intervaOutputVariable;
	}


	public void setIntervaOutputVariable(String intervaOutputVariable) {
		this.intervaOutputVariable = intervaOutputVariable;
	}


	public int getIntervaOutputIndex() {
		return intervaOutputIndex;
	}


	public void setIntervaOutputIndex(int intervaOutputIndex) {
		this.intervaOutputIndex = intervaOutputIndex;
	}


	public Integer getConceptId() {
		return conceptId;
	}


	public void setConceptId(Integer conceptId) {
		this.conceptId = conceptId;
	}


	public Concept getConcept() {
		return concept;
	}


	public void setConcept(Concept concept) {
		this.concept = concept;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Boolean getObservation() {
		return observation;
	}


	public void setObservation(Boolean observation) {
		this.observation = observation;
	}


	@Override
	public Integer getId() {
		return intervaOutputMappingId;
	}

	@Override
	public void setId(Integer id) {
		intervaOutputMappingId = id;
	}
}
