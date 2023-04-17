<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="lecturer.lecture.form.label.title" path="title"/>	
	<acme:input-textarea code="lecturer.lecture.form.label.lectureAbstract" path="lectureAbstract"/>
	<acme:input-textbox code="lecturer.lecture.form.label.estimatedTime" path="estimatedTime"/>
	<acme:input-textarea code="lecturer.lecture.form.label.body" path="body"/>
	<acme:input-select code="lecturer.lecture.form.label.indicator" path="indicator" choices="${indicators}"/>
	<acme:input-url code="lecturer.lecture.form.label.link" path="link"/>
	<acme:input-checkbox code="lecturer.lecture.form.label.published" path="published" readonly="true"/>

	
	<jstl:choose>
		<jstl:when test="${ _command == 'create'}">
			<acme:submit code="lecturer.lecture.form.button.create" action="/lecturer/lecture/create?masterId=${masterId}"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')}">
			<jstl:if test="${published == false}">
				<acme:submit code="lecturer.lecture.form.button.update" action="/lecturer/lecture/update"/>
				<acme:submit code="lecturer.lecture.form.button.delete" action="/lecturer/lecture/delete"/>
				<acme:submit code="lecturer.lecture.form.button.publish" action="/lecturer/lecture/publish"/>
			</jstl:if>
		</jstl:when>
	</jstl:choose>
</acme:form>
