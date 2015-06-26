package org.openmrs.module.interva;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.interva.api.IntervaMappingService;

import com.mysql.jdbc.StringUtils;

public class IntervaUtils {
	public static void createCSV(File file) throws IOException{
		FileOutputStream fop = new FileOutputStream(file);
		
		Csvee csv = getCSV();
		fop.write(csv.getCsv(true));
		fop.flush();
		fop.close();
	}
	
	public static Csvee getCSV() throws IOException{
		String sqlhead = "SELECT interva_variable FROM interva_input_mapping ORDER BY interva_index";
		List<List<Object>> list = Context.getAdministrationService().executeSQL(sqlhead , true);
		
		CsveeRow headerrow = new CsveeRow();
		for (List<Object> list2 : list) {
			for (Object object : list2) {
				headerrow.addRowElement(object);
			}
		}
		Csvee csv = new Csvee();
		csv.addHeader(headerrow);

		String sql = "CALL interva_inputcsv_query()";
		
		List<List<Object>> listdata = Context.getAdministrationService().executeSQL(sql , true);
		
		csv.populateCsvFromList(listdata, false);

		System.out.println(csv);
		
		return csv;
	}

	public static String parseOutput(File file) throws FileNotFoundException{
		parseOutputFileStream(new FileInputStream(file));
		
		String warningsfilename = Context.getAdministrationService().getGlobalProperty("interva.output.warningsFileName");

		File archiveFile = new File(file.getParent() + "/archive/"+new SimpleDateFormat("yyyyMMddHHmm").format(new Date())+file.getName());
		File archiveDir = new File(archiveFile.getParent());
		if(!archiveDir.exists()){
			archiveDir.mkdirs();
		}
		try {
			FileUtils.moveFile(file, archiveFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		File warningfile = new File(file.getParent() + "/" +(StringUtils.isEmptyOrWhitespaceOnly(warningsfilename)?"warnings.txt":warningsfilename));
		if(warningfile.exists() && warningfile.isFile()){
			Scanner ws = new Scanner(warningfile);
			String warnings = "";
			while(ws.hasNext()){
				warnings += ws.nextLine() + "\n\r<br>";
			}
			return warnings;
		}
		
		return "";
	}
	
	public static int parseOutputFileStream(InputStream fip) {
		Scanner snr = new Scanner(fip);
		
		String sql = "SELECT interva_output_variable, interva_output_index, c.concept_id, cdt.name FROM interva_output_mapping i LEFT JOIN concept c ON c.concept_id=i.concept_id LEFT JOIN concept_datatype cdt ON c.datatype_id=cdt.concept_datatype_id WHERE is_observation = true ORDER BY interva_output_index";
		List<List<Object>> list = Context.getAdministrationService().executeSQL(sql , true);
		
		String encounterCreatorId = Context.getAdministrationService().getGlobalProperty("interva.output.encounterCreatorId");
		User creator = new User(Integer.parseInt(encounterCreatorId));
		
		String encounterTypeName = Context.getAdministrationService().getGlobalProperty("interva.output.encounterType");

		String intervaCodObsGroupId = Context.getAdministrationService().getGlobalProperty("interva.output.causeOfDeathGroupConceptId");

		String record = "";
		int line = 1;
		String[] data;
		while(snr.hasNextLine() && !StringUtils.isEmptyOrWhitespaceOnly((record = snr.nextLine()))
				&& !StringUtils.isEmptyOrWhitespaceOnly((data = record.split(",", -1))[0])){
			String ID = data[0];
			int id = -1;
			
			try{
				id = Integer.parseInt(ID.trim());
			}
			catch (Exception e){
				e.printStackTrace();
			}
			/*String MALPREV = data[1];
			String HIVPREV = data[2];
			String PREGSTAT = data[3]; 
			String PREGLIK = data[4];
			String PRMAT = data[5];
			String INDET = data[6];
			String CAUSE1 = data[7];
			String LIK1 = data[8];
			String CAUSE2 = data[9];
			String LIK2 = data[10];
			String CAUSE3 = data[11];
			String LIK3 = data[12];*/
			
			if(id != -1){
				Patient patient = Context.getPatientService().getPatient(id);
				if(patient == null){
					throw new IllegalArgumentException("Error line ("+line+") : Invalid or no patient id provided");
				}
				
				Encounter encounter = new Encounter();
				EncounterType encounterType = Context.getEncounterService().getEncounterType(encounterTypeName);
				encounter.setEncounterType(encounterType);
				encounter.setPatient(patient);
				encounter.setEncounterDatetime(new Date());
				encounter.setCreator(creator);
				encounter.setDateCreated(new Date());
				encounter.setVoided(false);
	
				Obs codObs = new Obs();
				codObs.setConcept(new Concept(Integer.parseInt(intervaCodObsGroupId)));
				codObs.setCreator(creator);
				codObs.setPerson(new Person(patient.getPatientId()));
				codObs.setEncounter(encounter);
				codObs.setObsDatetime(new Date());
	
				Set<Obs> obs = new HashSet<Obs>();
				obs.add(codObs);
	
				
//				if(data.length-2 != list.size()){
//					System.out.println(data.length + ":" + list.size());
//					throw new IllegalArgumentException("Invalid number of variables in CSV at line "+line);
//				}
				String[] causes = new String[3];

				for (List<Object> list2 : list) {
					Obs o = new Obs();
					o.setObsGroup(codObs);
					o.setConcept(new Concept((Integer) list2.get(2)));
					o.setPerson(new Person(patient.getPatientId()));
					o.setCreator(creator);
					o.setDateCreated(new Date());
					o.setEncounter(encounter);
					o.setObsDatetime(new Date());
					
					int valIndex = Integer.parseInt(list2.get(1).toString());
					String val = valIndex < data.length ? data[valIndex] : null;
					if(val != null){
						if(list2.get(0).toString().toLowerCase().matches(".*cause.*1.*")){
							causes[0] = val;
						}
						else if(list2.get(0).toString().toLowerCase().matches(".*cause.*2.*")){
							causes[1] = val;
						}
						else if(list2.get(0).toString().toLowerCase().matches(".*cause.*3.*")){
							causes[2] = val;
						}
						
					}
					if(!StringUtils.isEmptyOrWhitespaceOnly(val)){
						if(list2.get(3).toString().toLowerCase().contains("text")){
							o.setValueText(val);
						}
						else if(list2.get(3).toString().toLowerCase().contains("numeric")){
							o.setValueNumeric(Double.parseDouble(val));
						}
					}
					obs.add(o);
				}
				
				for (int i = 0; i < 3; i++) {
					try{
						Concept concept = Context.getConceptService().getConcept("DEATH CAUSE "+(i+1)+" ICD-10");
						String cod = causes[i];
						if(concept != null && !StringUtils.isEmptyOrWhitespaceOnly(cod)){
							IntervaICD10Mapping icd10 = Context.getService(IntervaMappingService.class).getIntervaICD10Mapping(cod, true);
							System.out.println("ICD 10:::::::"+icd10);
							if(icd10 != null){
								System.out.println("setting icd 10 code"+icd10.getIcd10Code());
								Obs o = new Obs();
								o.setObsGroup(codObs);
								o.setConcept(concept);
								o.setPerson(new Person(patient.getPatientId()));
								o.setCreator(creator);
								o.setDateCreated(new Date());
								o.setEncounter(encounter);
								o.setObsDatetime(new Date());
								o.setValueText(icd10.getIcd10Code());
								
								obs.add(o);
							}
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				encounter.setObs(obs );
				
				Context.getEncounterService().saveEncounter(encounter);

				line++;

			}
		}
		
		snr.close();
		return line;
	}
	
	/*public static void main(String[] args) throws ModuleMustStartException, DatabaseUpdateException, InputRequiredException {
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
			
			String inputfileDir = Context.getAdministrationService().getGlobalProperty("interva.input.fileDirectory");
			if(!inputfileDir.trim().endsWith("\\")){
				inputfileDir = inputfileDir.trim() + "\\";
			}
			
			String inputfilenamepattern = Context.getAdministrationService().getGlobalProperty("interva.input.filenameStartPattern").trim();

			File file = new File(inputfileDir + inputfilenamepattern + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".csv");

			createCSV(file);
			parseOutputFileStream(null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Context.closeSession();
		}
	}*/
}
