<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.student.form.label.statement" path="statement"/>
	<acme:input-textarea code="authenticated.student.form.label.strongFeatures" path="strongFeatures"/>
	<acme:input-textarea code="authenticated.student.form.label.weakFeatures" path="weakFeatures"/>
	<acme:input-url code="authenticated.student.form.label.link" path="link"/>
	
	<acme:submit test="${_command == 'create'}" code="authenticated.student.form.button.create" action="/authenticated/student/create"/>
	<acme:submit test="${_command == 'update'}" code="authenticated.student.form.button.update" action="/authenticated/student/update"/>
</acme:form>
