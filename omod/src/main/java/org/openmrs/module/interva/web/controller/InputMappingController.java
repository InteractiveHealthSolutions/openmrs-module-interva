package org.openmrs.module.interva.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openmrs.Location;
import org.openmrs.LocationAttribute;
import org.openmrs.api.context.Context;
import org.openmrs.module.interva.IntervaICD10Mapping;
import org.openmrs.module.interva.IntervaInputMapping;
import org.openmrs.module.interva.api.IntervaMappingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/module/interva/inputmappings/")
public class InputMappingController {

	private static final String INPUT_MAPPINGS_LIST_VIEW = "/module/interva/inputMapping";
	private static final String ICD10_MAPPINGS_LIST_VIEW = "/module/interva/icd10Mapping";

	@RequestMapping(value = "inputMapping.form")
	public String getInputMapping(Map model){
		List<IntervaInputMapping> ml = Context.getService(IntervaMappingService.class).getIntervaInputMappings(true, 0, Integer.MAX_VALUE, new String[]{"concept"});
		model.put("inputmappings", ml);
		return INPUT_MAPPINGS_LIST_VIEW ;
	}
	
	@RequestMapping(value = "icd10Mapping.form")
	public String getIcd10Mapping(Map model){
		List<IntervaICD10Mapping> ml = Context.getService(IntervaMappingService.class).getIntervaICD10Mapping(true, 0, Integer.MAX_VALUE, null);
		model.put("icd10mappings", ml);
		return ICD10_MAPPINGS_LIST_VIEW ;
	}
	
	@RequestMapping(value = "intervaIcd10Mapping.json", method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> getIcd10MappingById(@RequestParam(value="intervaResult", required=false) String intervaResult,
			@RequestParam(value="mappingId", required=false) Integer mappingId){
		System.out.println("FINDING : "+intervaResult+"::"+mappingId); 
		IntervaICD10Mapping r = null;
		if(mappingId != null){
			r  = Context.getService(IntervaMappingService.class).getIntervaICD10Mapping(mappingId, true) ;
		}
		else{
			r = Context.getService(IntervaMappingService.class).getIntervaICD10Mapping(intervaResult, true) ;
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("intervaICD10MappingId", r.getIntervaICD10MappingId());
		map.put("intervaResult", r.getIntervaResult());
		map.put("intervaCode", r.getIntervaCode());
		map.put("icd10Code", r.getIcd10Code());
		map.put("displayText", r.getDisplayText());
		map.put("description", r.getDescription());
		map.put("voided", r.getVoided());
		map.put("voidReason", r.getVoidReason());
		return map;
	}
}
