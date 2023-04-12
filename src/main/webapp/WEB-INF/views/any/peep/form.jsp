<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="peep.form.label.title" path="title"/>	
	<acme:input-moment code="peep.form.label.instantiationMoment" path="instantiationMoment" readonly="true"/>
	<acme:input-textarea code="peep.form.label.message" path="message"/>
	<acme:input-url code="peep.form.label.link" path="link"/>
	<acme:input-email code="peep.form.label.email" path="emailAddress"/>
	<acme:input-textbox code="peep.form.label.nick" path="author"/>
	
	<jstl:if test="${ _command == 'create'}">
		<acme:input-checkbox code="peep.form.label.confirmation" path="confirmation"/>
		<acme:submit code="peep.form.button.create" action="/peep/create"/>
	</jstl:if>
	
</acme:form>