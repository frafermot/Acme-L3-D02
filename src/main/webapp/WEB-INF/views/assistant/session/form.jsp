<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="assistant.session.form.label.title" path="title"/>
	<acme:input-textbox code="assistant.session.form.label.tutorial" path="tutorial" readonly="true"/>
	<acme:input-textbox code="assistant.session.form.label.sessionAbstract" path="sessionAbstract"/>	
	<acme:input-select code="assistant.session.form.label.indication" path="indication" choices="${indications}"/>
	<acme:input-moment code="assistant.session.form.label.periodStart" path="periodStart"/>
	<acme:input-moment code="assistant.session.form.label.periodEnd" path="periodEnd"/>
	<acme:input-url code="assistant.session.form.label.link" path="link"/>
	<acme:hidden-data path="id"/>
	<acme:hidden-data path="masterId"/>
	<acme:hidden-data path="published"/>

	<jstl:choose>
		<jstl:when test="${ _command == 'create'}">
			<acme:submit code="assistant.session.form.button.create" action="/assistant/session/create"/>
		</jstl:when>
		<jstl:when test="${published == false && acme:anyOf(_command, 'show|update|delete')}">
			<acme:submit code="assistant.session.form.button.update" action="/assistant/session/update"/>
			<acme:submit code="assistant.session.form.button.delete" action="/assistant/session/delete"/>
			<acme:submit code="assistant.session.form.button.publish" action="/assistant/session/publish"/>
		</jstl:when>
	</jstl:choose>
	
</acme:form>