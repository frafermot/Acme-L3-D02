<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="company.practicumSession.form.label.title" path="title"/>	
	<acme:input-textarea code="company.practicumSession.form.label.sessionAbstract" path="sessionAbstract"/>
	<acme:input-moment code="company.practicumSession.form.label.periodStart" path="periodStart"/>
	<acme:input-moment code="company.practicumSession.form.label.periodEnd" path="periodEnd"/>
	<acme:input-url code="company.practicumSession.form.label.link" path="link" readonly="true"/>
	<acme:input-textbox code="company.practicumSession.form.label.practicum" path="practicum" readonly="true"/>
	<acme:input-checkbox code="company.practicumSession.form.label.published" path="published" readonly="true"/>
	
	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="company.practicumSession.form.button.create" action="/company/practicum-session/create?masterId=${masterId}"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')}">
			<jstl:if test="${published == false}">
				<acme:submit code="company.practicumSession.form.button.update" action="/company/practicum-session/update?id=${id}"/>
				<acme:submit code="company.practicumSession.form.button.delete" action="/company/practicum-session/delete?id=${id}"/>
				<acme:submit code="company.practicumSession.form.button.publish" action="/company/practicum-session/publish?id=${id}"/>
			</jstl:if>
		</jstl:when>
	</jstl:choose>
</acme:form>