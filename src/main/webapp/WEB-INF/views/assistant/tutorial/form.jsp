<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="assistant.tutorial.form.label.code" path="code"/>
	<acme:input-textbox code="assistant.tutorial.form.label.title" path="title"/>	
	<acme:input-select code="assistant.tutorial.form.label.course" path="course" choices="${courses}"/>
	<acme:input-textbox code="assistant.tutorial.form.label.assistant" path="assistant" readonly="true"/>
	<acme:input-textarea code="assistant.tutorial.form.label.tutorialAbstract" path="tutorialAbstract"/>
	<acme:input-textarea code="assistant.tutorial.form.label.goals" path="goals"/>
	<acme:input-moment code="assistant.tutorial.form.label.estimatedTime" path="estimatedTime"/>
	<acme:hidden-data path="id"/>
	<acme:hidden-data path="published"/>

	<jstl:choose>
		<jstl:when test="${ _command == 'create'}">
			<acme:submit code="assistant.tutorial.form.button.create" action="/assistant/tutorial/create"/>
		</jstl:when>
		<jstl:when test="${published == false && acme:anyOf(_command, 'show|update|delete|publish')}">
			<acme:submit code="assistant.tutorial.form.button.update" action="/assistant/tutorial/update"/>
			<acme:submit code="assistant.tutorial.form.button.delete" action="/assistant/tutorial/delete"/>
			<acme:submit code="assistant.tutorial.form.button.publish" action="/assistant/tutorial/publish"/>
		</jstl:when>
	</jstl:choose>
	
</acme:form>