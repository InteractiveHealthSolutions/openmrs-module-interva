package org.openmrs.module.interva;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;

public class IntervaInputMapping extends BaseOpenmrsData {

	private int intervaMappingId;
	
	private String intervaVariable;
	
	private Integer intervaIndex;
	
	private Integer conceptId;
	
	private Concept concept;
	
	private String description;
	
	private Boolean observation;
	
	private String acceptCondition;

    public IntervaInputMapping() {
		
	}
    
	public int getIntervaMappingId() {
		return intervaMappingId;
	}

	public void setIntervaMappingId(int intervaMappingId) {
		this.intervaMappingId = intervaMappingId;
	}

	public String getIntervaVariable() {
		return intervaVariable;
	}

	public void setIntervaVariable(String intervaVariable) {
		this.intervaVariable = intervaVariable;
	}

	public Integer getIntervaIndex() {
		return intervaIndex;
	}

	public void setIntervaIndex(Integer intervaIndex) {
		this.intervaIndex = intervaIndex;
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

	public Boolean isObservation() {
		return observation;
	}
	
	public Boolean getObservation() {
		return observation;
	}

	public void setObservation(Boolean observation) {
		this.observation = observation;
	}

	public String getAcceptCondition() {
		return acceptCondition;
	}

	public void setAcceptCondition(String acceptCondition) {
		this.acceptCondition = acceptCondition;
	}

	@Override
	public Integer getId() {
		return intervaMappingId;
	}

	@Override
	public void setId(Integer id) {
		intervaMappingId = id;
	}
}
