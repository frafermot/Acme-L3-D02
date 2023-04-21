<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="company.practicum.form.label.code" path="code"/>	
	<acme:input-textbox code="lecturer.course.form.label.title" path="title"/>
	<acme:input-textarea code="company.practicum.form.label.practicumAbstract" path="practicumAbstract"/>
	<acme:input-textarea code="company.practicum.form.label.goals" path="goals"/>
	<acme:input-textbox code="company.practicum.form.label.estimatedTotalTime" path="estimatedTotalTime" readonly="true"/>
	<acme:input-textbox code="company.practicum.form.label.company" path="company" readonly="true"/>
	<acme:input-select code="company.practicum.form.label.course" path="course" choices="${courses}"/>
	<acme:input-checkbox code="company.practicum.form.label.published" path="published" readonly="true"/>
	
	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="company.practicum.form.button.create" action="/company/practicum/create"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')}">
			<acme:button code="company.practicum.form.button.sessions" action="/company/practicum-session/list?masterId=${id}"/>
			<jstl:if test="${published == false}">
				<acme:submit code="company.practicum.form.button.update" action="/company/practicum/update"/>
				<acme:submit code="company.practicum.form.button.delete" action="/company/practicum/delete"/>
				<acme:submit code="company.practicum.form.button.publish" action="/company/practicum/publish"/>
			</jstl:if>
		</jstl:when>
	</jstl:choose>
</acme:form>