package org.openmrs.module.interva.web.controller;

import java.util.List;
import java.util.Map;

import org.openmrs.api.context.Context;
import org.openmrs.module.interva.IntervaOutputMapping;
import org.openmrs.module.interva.api.IntervaMappingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/module/interva/outputmappings/")
public class OutputMappingController {

	private static final String OUTPUT_MAPPINGS_LIST_VIEW = "/module/interva/outputMapping";

	@RequestMapping(value = "outputMapping.form")
	public String getOutputMapping(Map model){
		List<IntervaOutputMapping> ml = Context.getService(IntervaMappingService.class).getIntervaOutputMappings(true, 0, Integer.MAX_VALUE, new String[]{"concept"});
		model.put("outputmappings", ml);
		return OUTPUT_MAPPINGS_LIST_VIEW ;
	}
}
