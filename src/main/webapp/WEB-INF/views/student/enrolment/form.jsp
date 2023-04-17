<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="student.enrolment.form.label.code" path="code"/>	
	<acme:input-textbox code="student.enrolment.form.label.motivation" path="motivation"/>
	<acme:input-textarea code="student.enrolment.form.label.goals" path="goals"/>
	<acme:input-double code="student.enrolment.form.label.workTime" path="workTime"/>
	<acme:input-textbox code="student.enrolment.form.label.cardHolder" path="cardHolder"/>
	<acme:input-integer code="student.enrolment.form.label.cardNibble" path="cardNibble"/>
	<acme:input-checkbox code="student.enrolment.form.label.finalised" path="finalised"/>
	
</acme:form>
