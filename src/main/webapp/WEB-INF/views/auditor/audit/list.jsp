<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.audit.list.label.code" path="code" width="10%"/>
	<acme:list-column code="auditor.audit.list.label.conclusion" path="conclusion" width="30%"/>
	<acme:list-column code="auditor.audit.list.label.strongPoints" path="strongPoints" width="30%"/>
	<acme:list-column code="auditor.audit.list.label.weakPoints" path="weakPoints" width="20%"/>
	<acme:list-column code="auditor.audit.list.label.published" path="published" width="5%"/>
	<acme:list-column code="auditor.audit.list.label.course" path="course" width="5%"/>
</acme:list>

<acme:button code="auditor.audit.form.button.create" action="/auditor/audit/create"/>