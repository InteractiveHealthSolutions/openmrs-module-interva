<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>
<style>
<!--
table.datatableiva {
	border: 1px gray solid;
	border-spacing: 0px;
	border-collapse: collapse;
	margin: 2px;
	width: 100%;
}

table.datatableiva td {
	border: 1px #F2F2F2 solid;
	padding: 1px;
	font-size: 12px;
}

table.datatableiva th {
	border: 1px #F2F2F2 solid;
	padding: 1px;
}
.heading{
	font-size: large;
	font-weight: bold;
	color: black;
}
-->
</style>

<div class="heading">InterVA ICD10 Mappings</div>
<table class="datatableiva">
  <tr>
   <th>Mapping ID</th>
   <th>InterVA Result</th>
   <th>InterVA Result Code</th>
   <th>Display Text</th>
   <th>ICD 10 Code</th>
   <!-- <th>Creator</th> -->
   <!-- <th>Date Created</th> -->
   <!-- <th>Changed By</th>
   <th>Date Changed</th> -->
   <th>Voided</th>
   <!-- <th>Date Voided</th> -->
   <!-- <th>Voided By</th> -->
   <th>Void Reason</th>
   <th>Description</th>
  </tr>
  <c:forEach var="iml" items="${icd10mappings}">
      <tr>
        <td>${iml.intervaICD10MappingId}</td>
        <td>${iml.intervaResult}</td>
        <td>${iml.intervaCode}</td>
        <td>${iml.displayText}</td>
        <td>${iml.icd10Code}</td>
       <!--  <td></td> -->
        <%-- <td>${iml.dateCreated}</td> --%>
       <%--  <td></td>
        <td>${iml.dateChanged}</td> --%>
        <td>${iml.voided}</td>
       <%--  <td>${iml.dateVoided}</td> --%>
        <!-- <td></td> -->
        <td>${iml.voidReason}</td>
        <td>${iml.description}</td>
      </tr>		
  </c:forEach>
</table>

<%@ include file="/WEB-INF/template/footer.jsp"%>
