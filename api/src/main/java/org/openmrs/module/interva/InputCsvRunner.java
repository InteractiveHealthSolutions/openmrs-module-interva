package org.openmrs.module.interva;

import org.openmrs.scheduler.tasks.AbstractTask;

public class InputCsvRunner extends AbstractTask{

	@Override
	public void execute() {
		try {
			IntervaUtils.createCSV(null);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
