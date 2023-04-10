<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.note.form.label.title" path="title"/>	
	<acme:input-moment code="authenticated.note.form.label.instantiationMoment" path="instantiationMoment" readonly="true"/>
	<acme:input-textarea code="authenticated.note.form.label.message" path="message"/>
	<acme:input-url code="authenticated.note.form.label.link" path="link"/>
	<acme:input-email code="authenticated.note.form.label.emailAddress" path="emailAddress"/>
	<acme:input-textbox code="authenticated.note.form.label.author" path="author" readonly="true"/>
	
	<jstl:if test="${ _command == 'create'}">
		<acme:input-checkbox code="authenticated.note.form.label.confirmation" path="confirmation"/>
		<acme:submit code="authenticated.note.form.button.create" action="/authenticated/note/create"/>
	</jstl:if>
	
</acme:form>