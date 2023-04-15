<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="lecturer.course.form.label.code" path="code"/>	
	<acme:input-textbox code="lecturer.course.form.label.title" path="title"/>
	<acme:input-textarea code="lecturer.course.form.label.courseAbstract" path="courseAbstract"/>
	<acme:input-select code="lecturer.course.form.label.indicator" path="indicator" choices="${indicators}"/>
	<acme:input-money code="lecturer.course.form.label.retailPrice" path="retailPrice"/>
	<acme:input-url code="lecturer.course.form.label.link" path="link"/>
	<acme:input-checkbox code="lecturer.course.form.label.published" path="published" readonly="true"/>
	
	<acme:button code="lecturer.course.form.button.lectures" action="/lecturer/lecture/list-mine?masterId=${id}"/>
	
	<jstl:choose>
		<jstl:when test="${ _command == 'create'}">
			<acme:submit code="lecturer.course.form.button.create" action="/lecturer/course/create"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')}">
			<jstl:if test="${published == false}">
				<acme:submit code="lecturer.course.form.button.update" action="/lecturer/course/update"/>
				<acme:submit code="lecturer.course.form.button.delete" action="/lecturer/course/delete"/>
				<acme:submit code="lecturer.course.form.button.publish" action="/lecturer/course/publish"/>
			</jstl:if>
		</jstl:when>
	</jstl:choose>
</acme:form>