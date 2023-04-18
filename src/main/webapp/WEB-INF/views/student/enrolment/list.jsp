<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="student.enrolment.list.label.code" path="code" width="70%"/>
	<acme:list-column code="student.enrolment.list.label.finalised" path="finalised" width="30%"/>
</acme:list>

<acme:button code="student.enrolment.form.button.create" action="/student/enrolment/create"/>