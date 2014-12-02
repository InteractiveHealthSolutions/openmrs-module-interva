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
package org.openmrs.module.interva.web.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.interva.IntervaUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * This class configured as controller using annotation and mapped with the URL of 'module/basicmodule/basicmoduleLink.form'.
 */
@Controller
@RequestMapping(value = "/module/interva/iocsvhandler/")
public class IntervaFormController{
	
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	
	/** Success form view name */
	public final static String INTERVA_RESPONSE_FORM_VIEW = "/module/interva/intervaResponseForm";
	public final static String INTERVA_OUTPUT_UPLOAD_FORM_VIEW = "/module/interva/outputUploadForm";

	
	@RequestMapping(method = RequestMethod.GET, value = "generateIntervaInput.form")
	public String generateIntervaInput(Map model){
		model.put("intervaResponseMessage", generateInputCsv());
		
		return INTERVA_RESPONSE_FORM_VIEW;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "intervaioactivityForm.form")
	public String uploadIntervaOutput(Map model) throws IOException{
		return "/module/interva/intervaioactivityForm";
	}
	
//	@RequestMapping(method = RequestMethod.GET, value = "uploadIntervaOutputForm.form")
//	public String homePage(Map model) throws IOException{
//		return INTERVA_OUTPUT_UPLOAD_FORM_VIEW;
//	}
	
	@RequestMapping(method = RequestMethod.POST, value = "uploadIntervaOutputForm.form")
	public String uploadIntervaOutput(HttpServletRequest request, HttpServletResponse response, Map model) throws IOException{
		String message = "File parsed successfully.";
		try{
			parseUploadedFile(request);
		}
		catch(Exception e){
			e.printStackTrace();
			message = "Error parsing file: "+e.getMessage();
		}
		model.put("message", message);
		
		return "/module/interva/intervaioactivityForm";
	}
	
	private int parseUploadedFile(HttpServletRequest request) throws IOException{
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile ivaoutputFile = multipartRequest.getFile("ivaoutputFile");
		if (ivaoutputFile != null && !ivaoutputFile.isEmpty()) {
			return IntervaUtils.parseOutputFileStream(ivaoutputFile.getInputStream());
		}
		else {
			throw new IllegalArgumentException("No file specified");
		}
	}
	
/*	@RequestMapping(value = "uploadIntervaOutput.form")
	public @ResponseBody Map<String, Object>  uploadIntervaOutput(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String message = "File parsed successfully.";
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			message += "\nRecords processed : " + (parseUploadedFile(request)-1);
			map.put("message", message);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("message", "Error parsing file: "+e.getMessage());
		}
		
		return map;
	}*/
	
	private String generateInputCsv(){
		String message = "";
		try {
			String inputfileDir = Context.getAdministrationService().getGlobalProperty("interva.input.fileDirectory");
			if(!inputfileDir.trim().endsWith("\\")){
				inputfileDir = inputfileDir.trim() + "\\";
			}
			
			String inputfilenamepattern = Context.getAdministrationService().getGlobalProperty("interva.input.filenameStartPattern").trim();

			File file = new File(inputfileDir + inputfilenamepattern + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".csv");

			
			IntervaUtils.createCSV(file);
			message = "CSV generated successfully @ " + file.getAbsoluteFile();
		} 
		catch (IOException e) {
			e.printStackTrace();
			message = "Error occurred while creating CSV : "+e.getMessage();
		}
		return message;
	}
	
	private String getInputCsvName() throws IOException{
		String inputfileDir = Context.getAdministrationService().getGlobalProperty("interva.input.fileDirectory");
		if(!inputfileDir.trim().endsWith("\\")){
			inputfileDir = inputfileDir.trim() + "\\";
		}
		
		String inputfilenamepattern = Context.getAdministrationService().getGlobalProperty("interva.input.filenameStartPattern").trim();

		return inputfilenamepattern + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".csv";
	}
	
	@RequestMapping(value = "generateIntervaInput.json")
	public @ResponseBody Map<String, Object> generateIntervaInputJson(Map model, HttpServletRequest request, HttpServletResponse response) throws IOException{
		//response.setContentType("application/json");
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			map.put("message", generateInputCsv());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("message", e.getMessage());
		}
		//response.getWriter().write(map.toString());
		return map;
	}

	@RequestMapping(value = "parseIntervaOutput.form")
	public String parseIntervaOutput(Map model){
		model.put("intervaResponseMessage", parseOutputCsv());
		
		return INTERVA_RESPONSE_FORM_VIEW;
	}
	
	@RequestMapping(value = "parseIntervaOutput.json")
	public @ResponseBody Map<String, Object> parseIntervaOutput(Map model, HttpServletRequest request, HttpServletResponse response) throws IOException{
		//response.setContentType("application/json");
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			map.put("message", parseOutputCsv());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("message", e.getMessage());
		}
		//response.getWriter().write(map.toString());
		return map;	
	}
	
	public String parseOutputCsv(){
		String message = "";
		try {
			String inputfileDir = Context.getAdministrationService().getGlobalProperty("interva.output.fileDirectory");
			if(!inputfileDir.trim().endsWith("\\")){
				inputfileDir = inputfileDir.trim() + "\\";
			}
			
			String inputfilenamepattern = Context.getAdministrationService().getGlobalProperty("interva.output.filename").trim();

			File file = new File(inputfileDir + inputfilenamepattern);
			
			String warnings = IntervaUtils.parseOutput(file);
			message = "CSV parsed successfully @ " + file.getAbsoluteFile();
			message += "\n\rWarnings generated are :\n\r<br>" + warnings;
		} 
		catch (IOException e) {
			e.printStackTrace();
			message = "Error occurred while creating CSV : "+e.getMessage();
		}
		
		return message;
	}	
	/**
	 * All the parameters are optional based on the necessity  
	 * 
	 * @param httpSession
	 * @param anyRequestObject
	 * @param errors
	 * @return
	 */
	/*@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(HttpSession httpSession,
	                               @ModelAttribute("anyRequestObject") Object anyRequestObject, BindingResult errors) {
		
		if (errors.hasErrors()) {
			// return error view
		}
		
		return SUCCESS_FORM_VIEW;
	}*/
	
	/**
	 * This class returns the form backing object. This can be a string, a boolean, or a normal java
	 * pojo. The bean name defined in the ModelAttribute annotation and the type can be just
	 * defined by the return type of this method
	 */
/*	@ModelAttribute("thePatientList")
	protected Collection<Patient> formBackingObject(HttpServletRequest request) throws Exception {
		// get all patients that have an identifier "101" (from the demo sample data)
		// see http://resources.openmrs.org/doc/index.html?org/openmrs/api/PatientService.html for
		// a list of all PatientService methods
		Collection<Patient> patients = Context.getPatientService().findPatients("101", false);
		
		// this object will be made available to the jsp page under the variable name
		// that is defined in the @ModuleAttribute tag
		return patients;
	}*/
	
}
