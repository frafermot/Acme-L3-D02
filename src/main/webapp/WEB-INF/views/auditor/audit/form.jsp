<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>

	<acme:input-textbox code="auditor.audit.form.label.code" path="code"/>	
	<acme:input-textarea code="auditor.audit.form.label.conclusion" path="conclusion"/>
	<acme:input-textarea code="auditor.audit.form.label.strongPoints" path="strongPoints"/>
	<acme:input-textarea code="auditor.audit.form.label.weakPoints" path="weakPoints"/>
	<acme:input-select code="auditor.audit.form.label.mark" path="mark" choices="${marks}"/>
	<acme:input-select code="auditor.audit.form.label.course" path="course" choices="${courses}"/>
	<acme:input-checkbox code="auditor.audit.form.label.published" path="published"/>

	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.audit.form.button.create" action="/auditor/audit/create"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
			<jstl:if test="${published == false}">
				<acme:submit code="auditor.audit.form.button.update" action="/auditor/audit/update"/>
				<acme:submit code="auditor.audit.form.button.delete" action="/auditor/audit/delete"/>
			</jstl:if>		
		</jstl:when>	
	</jstl:choose>

</acme:form>