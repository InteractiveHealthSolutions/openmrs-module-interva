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
package org.openmrs.module.interva.api.db;

import java.util.List;

import org.openmrs.module.interva.IntervaICD10Mapping;
import org.openmrs.module.interva.IntervaInputMapping;
import org.openmrs.module.interva.IntervaOutputMapping;

/**
 *  Database methods for {@link IntervaMappingService}.
 */
public interface IntervaMappingDAO {
	
	List<IntervaICD10Mapping> getIntervaICD10Mapping(boolean readonly, int firstResult, int maxResults, String[] mappingsToJoin);

	IntervaICD10Mapping getIntervaICD10Mapping(int intervaICD10MappingId, boolean readonly);

	IntervaICD10Mapping getIntervaICD10Mapping(String intervaResult, boolean readonly);

	List<IntervaInputMapping> getIntervaInputMappings(boolean readonly, int firstResult, int maxResults, String[] mappingsToJoin);

	List<IntervaOutputMapping> getIntervaOutputMappings(boolean readonly, int firstResult, int maxResults, String[] mappingsToJoin);

}