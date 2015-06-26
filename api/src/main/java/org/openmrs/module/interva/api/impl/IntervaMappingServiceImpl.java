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
package org.openmrs.module.interva.api.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.interva.IntervaICD10Mapping;
import org.openmrs.module.interva.IntervaInputMapping;
import org.openmrs.module.interva.IntervaOutputMapping;
import org.openmrs.module.interva.api.IntervaMappingService;
import org.openmrs.module.interva.api.db.IntervaMappingDAO;

/**
 * It is a default implementation of {@link IntervaMappingService}.
 */
public class IntervaMappingServiceImpl extends BaseOpenmrsService implements IntervaMappingService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private IntervaMappingDAO dao;
	
	/**
     * @param dao the dao to set
     */
    public void setDao(IntervaMappingDAO dao) {
	    this.dao = dao;
    }
    
    /**
     * @return the dao
     */
    public IntervaMappingDAO getDao() {
	    return dao;
    }

	@Override
	public List<IntervaInputMapping> getIntervaInputMappings(boolean readonly, int firstResult, int maxResults, String[] mappingsToJoin) {
		return dao.getIntervaInputMappings(readonly, firstResult, maxResults, mappingsToJoin);
	}

	@Override
	public List<IntervaOutputMapping> getIntervaOutputMappings(boolean readonly, int firstResult, int maxResults, String[] mappingsToJoin) {
		return dao.getIntervaOutputMappings(readonly, firstResult, maxResults, mappingsToJoin);
	}

	@Override
	public List<IntervaICD10Mapping> getIntervaICD10Mapping(boolean readonly,
			int firstResult, int maxResults, String[] mappingsToJoin) {
		return dao.getIntervaICD10Mapping(readonly, firstResult, maxResults, mappingsToJoin);
	}

	@Override
	public IntervaICD10Mapping getIntervaICD10Mapping(int intervaICD10MappingId, boolean readonly) {
		return dao.getIntervaICD10Mapping(intervaICD10MappingId, readonly);
	}

	@Override
	public IntervaICD10Mapping getIntervaICD10Mapping(String intervaResult, boolean readonly) {
		return dao.getIntervaICD10Mapping(intervaResult, readonly);
	}
}