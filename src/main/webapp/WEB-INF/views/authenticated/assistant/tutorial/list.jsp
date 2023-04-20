<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<jstl:if test="${_command != 'list-mine'}">
	<acme:list>
		<acme:list-column code="assistant.tutorial.list.label.code" path="code" width="25%"/>
		<acme:list-column code="assistant.tutorial.list.label.title" path="title" width="50%"/>
		<acme:list-column code="assistant.tutorial.list.label.estimatedTime" path="estimatedTime" width="25%"/>
	</acme:list>
</jstl:if>

<jstl:if test="${_command == 'list-mine'}">
	<acme:list>
		<acme:list-column code="assistant.tutorial.list.label.code" path="code" width="25%"/>
		<acme:list-column code="assistant.tutorial.list.label.title" path="title" width="50%"/>
		<acme:list-column code="assistant.tutorial.list.label.estimatedTime" path="estimatedTime" width="15%"/>
		<acme:list-column code="assistant.tutorial.list.label.published" path="published" width="10%"/>
	</acme:list>
	<acme:button code="assistant.tutorial.list.button.create" action="/assistant/tutorial/create"/>
</jstl:if>
