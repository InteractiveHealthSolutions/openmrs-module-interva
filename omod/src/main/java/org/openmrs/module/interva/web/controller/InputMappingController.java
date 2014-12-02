package org.openmrs.module.interva.web.controller;

import java.util.List;
import java.util.Map;

import org.openmrs.api.context.Context;
import org.openmrs.module.interva.IntervaInputMapping;
import org.openmrs.module.interva.api.IntervaMappingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/module/interva/inputmappings/")
public class InputMappingController {

	private static final String INPUT_MAPPINGS_LIST_VIEW = "/module/interva/inputMapping";

	@RequestMapping(value = "inputMapping.form")
	public String getInputMapping(Map model){
		List<IntervaInputMapping> ml = Context.getService(IntervaMappingService.class).getIntervaInputMappings(true, 0, Integer.MAX_VALUE, new String[]{"concept"});
		model.put("inputmappings", ml);
		return INPUT_MAPPINGS_LIST_VIEW ;
	}
}
