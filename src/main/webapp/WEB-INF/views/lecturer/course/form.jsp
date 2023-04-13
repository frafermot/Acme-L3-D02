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
	
		<acme:button code="lecturer.course.form.button.lectures" action="/lecturer/lecture/list-mine?masterId=${id}"/>
</acme:form>