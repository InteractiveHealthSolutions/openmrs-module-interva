package org.openmrs.module.interva;

import java.io.File;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.openmrs.api.context.Context;
import org.openmrs.module.ModuleMustStartException;
import org.openmrs.util.DatabaseUpdateException;
import org.openmrs.util.InputRequiredException;
import org.openmrs.util.OpenmrsUtil;

public class XFORMCLASS {

	public static void main(String[] args) throws ModuleMustStartException, DatabaseUpdateException, InputRequiredException {
		Properties props = OpenmrsUtil.getRuntimeProperties("openmrs");
		
		boolean usetest = true;
		
		if (usetest) {
			props.put("connection.username", "root");
			props.put("connection.password", "codbr");
			Context.startup("jdbc:mysql://125.209.94.150:2103/openmrs?autoReconnect=true", "root", "codbr", props);
		} else {
			props.put("connection.username", "root");
			props.put("connection.password", "VA1913wm");
			Context.startup("jdbc:mysql://localhost:3306/openmrs?autoReconnect=true", "root", "VA1913wm", props);
		}
		
		try {
			Context.openSession();
			Context.authenticate("admin", "Admin123");

			File file = new File("C:\\Users\\INTEL\\Desktop\\tempxform.txt");

			Scanner snr = new Scanner(file);
			
			String xml = "";
			while(snr.hasNextLine()){
				xml += snr.nextLine();
			}
			
			snr.close();
			
			String sql = "update xforms_xform set xform_xml=concat('"+xml.replace("'", "',\"'\",'")+"') where form_id=21";
			List<List<Object>> list = Context.getAdministrationService().executeSQL(sql , false);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Context.closeSession();
		}
	}
}
