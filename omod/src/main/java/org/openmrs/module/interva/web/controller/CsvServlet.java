package org.openmrs.module.interva.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.api.context.Context;
import org.openmrs.module.interva.IntervaUtils;

public class CsvServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		resp.setContentType("application/zip"); 
		resp.setHeader("Content-Disposition", "attachment; filename=InterVAInput"+new SimpleDateFormat("yyyyMMddHHmm").format(new Date())+".zip"); 

		ZipOutputStream zip = new ZipOutputStream(resp.getOutputStream());
		zip.putNextEntry(new ZipEntry(getInputCsvName()));
		zip.write(IntervaUtils.getCSV().getCsv(true));
		zip.closeEntry();
		zip.close();
		System.out.println("Preparing Download FINISHED");
	}
	
	private String getInputCsvName() throws IOException{
		String inputfileDir = Context.getAdministrationService().getGlobalProperty("interva.input.fileDirectory");
		if(!inputfileDir.trim().endsWith("\\")){
			inputfileDir = inputfileDir.trim() + "\\";
		}
		
		String inputfilenamepattern = Context.getAdministrationService().getGlobalProperty("interva.input.filenameStartPattern").trim();

		return inputfilenamepattern + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".csv";
	}
}
