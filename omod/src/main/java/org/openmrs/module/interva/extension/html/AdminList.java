/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.interva.extension.html;

import java.util.HashMap;
import java.util.Map;

import org.openmrs.module.Extension;
import org.openmrs.module.web.extension.AdministrationSectionExt;

/**
 * This class defines the links that will appear on the administration page under the
 * "basicmodule.title" heading. This extension is enabled by defining (uncommenting) it in the
 * /metadata/config.xml file.
 */
public class AdminList extends AdministrationSectionExt {
	
	/**
	 * @see org.openmrs.module.web.extension.AdministrationSectionExt#getMediaType()
	 */
	public Extension.MEDIA_TYPE getMediaType() {
		return Extension.MEDIA_TYPE.html;
	}
	
	/**
	 * @see org.openmrs.module.web.extension.AdministrationSectionExt#getTitle()
	 */
	public String getTitle() {
		return "InterVA Connector";
	}
	
	/**
	 * @see org.openmrs.module.web.extension.AdministrationSectionExt#getLinks()
	 */
	public Map<String, String> getLinks() {
		
		Map<String, String> map = new HashMap<String, String>();
		
		//map.put("module/interva/iocsvhandler/generateIntervaInput.form", "Generate InterVA Input");
		map.put("module/interva/iocsvhandler/downloadIntervaInput.form", "Download InterVA Input CSV");
		map.put("module/interva/iocsvhandler/uploadIntervaOutputForm.form", "Upload InterVA Output");
		map.put("module/interva/iocsvhandler/intervaioactivityForm.form", "InterVA IO");

		//map.put("module/interva/iocsvhandler/parseIntervaOutput.form", "Parse InterVA Output");
		map.put("module/interva/inputmappings/inputMapping.form", "Show INPUT mapping");
		map.put("module/interva/inputmappings/icd10Mapping.form", "Show InterVA ICD 10 mapping");
		map.put("module/interva/outputmappings/outputMapping.form", "Show OUTPUT mapping");

		//map.put("module/basicmodule/basicmoduleLink.form", "basicmodule.replace.this.link.name");

		return map;
	}
	
}
