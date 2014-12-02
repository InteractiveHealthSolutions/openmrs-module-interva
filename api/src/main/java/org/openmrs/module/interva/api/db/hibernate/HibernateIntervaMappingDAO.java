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
package org.openmrs.module.interva.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.openmrs.module.interva.IntervaInputMapping;
import org.openmrs.module.interva.IntervaOutputMapping;
import org.openmrs.module.interva.api.db.IntervaMappingDAO;

/**
 * It is a default implementation of  {@link IntervaMappingDAO}.
 */
public class HibernateIntervaMappingDAO implements IntervaMappingDAO {
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private SessionFactory sessionFactory;
	
	/**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
	    this.sessionFactory = sessionFactory;
    }
    
	/**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
	    return sessionFactory;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<IntervaInputMapping> getIntervaInputMappings(boolean readonly,int firstResult, int maxResults, String[] mappingsToJoin) {
		Criteria cri = sessionFactory.getCurrentSession().createCriteria(IntervaInputMapping.class)
				.setReadOnly(readonly)
				.addOrder(Order.asc("intervaIndex"));

		if (mappingsToJoin != null)
			for (String mapping : mappingsToJoin) {
				cri.setFetchMode(mapping, FetchMode.JOIN);
			}

		List<IntervaInputMapping> list = cri.setFirstResult(firstResult).setMaxResults(maxResults).list();
		return list;
	}

	@Override
	public List<IntervaOutputMapping> getIntervaOutputMappings(boolean readonly, int firstResult, int maxResults, String[] mappingsToJoin) {
		Criteria cri = sessionFactory.getCurrentSession().createCriteria(IntervaOutputMapping.class)
				.setReadOnly(readonly)
				.addOrder(Order.asc("intervaOutputIndex"));

		if (mappingsToJoin != null)
			for (String mapping : mappingsToJoin) {
				cri.setFetchMode(mapping, FetchMode.JOIN);
			}

		List<IntervaOutputMapping> list = cri.setFirstResult(firstResult).setMaxResults(maxResults).list();
		return list;
	}
}