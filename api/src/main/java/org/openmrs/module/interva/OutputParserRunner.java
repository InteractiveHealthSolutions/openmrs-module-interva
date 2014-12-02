package org.openmrs.module.interva;

import org.openmrs.scheduler.tasks.AbstractTask;

public class OutputParserRunner extends AbstractTask{

	@Override
	public void execute() {
		try {
			IntervaUtils.parseOutput(null);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
