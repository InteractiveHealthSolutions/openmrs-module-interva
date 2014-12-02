<%@ include file="/WEB-INF/template/include.jsp"%>
<style>
<!--
.ivdiv {
	padding: 20px;
	border: solid thin silver;
}
-->
</style>
<div style="color: maroon;">${message}</div>
<table style="border: 0px">
	<tr>
		<td>
			<h1>InterVA</h1>
			<br>
			InterVA is an external tool that interprets verbal autopsy data. 
			eCODIRS is using this software to calculate Cause of Death(COD) from the information filled into Verbal Autopsy forms. 
			Calculating COD is a two step process and is explained below. 
			To download InterVA and read further details about the software, go here: <a href="http://www.InterVA-4.net" >http://www.InterVA-4.net</a>
		</td>
	</tr>
	<tr>
		<td>
			<div class="ivdiv">
			<h2>Step 1: Generating Input CSV</h2>
			<br>
			eCODIRS needs to export an 'input CSV' to send to InterVA for further calculations to be done. 
			This CSV has to be downloaded and pasted into InterVA software's root folder so that it can be processed. 
			Click 'Download InterVA Input CSV' below to do this. 
			<br><br>
			<a href="${pageContext.request.contextPath}/moduleServlet/interva/csvDownload">Download InterVA Input CSV</a>
			</div>
		</td>
	</tr>
	<tr>
		<td>
			<div class="ivdiv">
			<h2>Step 2: Uploading Output CSV</h2>
			<br>
			When InterVA software has calculated COD, it will generate an output CSV file that needs to be uploaded back into eCODIRS. This CSV file will appear in reports and eCODIRS database. 
			Click 'Select Output File' button below and then 'Upload' to do this. 			
			<br><br>
			<form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/module/interva/iocsvhandler/uploadIntervaOutputForm.form">
			Select output file : <input type="file" name="ivaoutputFile" /> <input type="submit" value='Upload' />  
			</form>
			</div>
		</td>
	</tr>
</table>

