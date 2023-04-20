<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.audit.list.label.code" path="code" width="30%"/>
	<acme:list-column code="auditor.audit.list.label.published" path="published" width="40%"/>
	<acme:list-column code="auditor.audit.list.label.course" path="course" width="30%"/>
</acme:list>

<acme:button code="auditor.audit.form.button.create" action="/auditor/audit/create"/>