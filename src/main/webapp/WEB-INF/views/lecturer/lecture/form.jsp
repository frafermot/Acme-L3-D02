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
	<acme:input-checkbox code="lecturer.lecture.form.label.published" path="published"/>
	
</acme:form>
