<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="assistant.session.list.label.title" path="title" width="20%"/>
	<acme:list-column code="assistant.session.list.label.indication" path="indication" width="20%"/>
	<acme:list-column code="assistant.session.list.label.periodStart" path="periodStart" width="20%"/>
	<acme:list-column code="assistant.session.list.label.periodEnd" path="periodEnd" width="20%"/>
	<acme:list-column code="assistant.session.list.label.published" path="published" width="20%"/>
</acme:list>
<jstl:if test="${showCreate}">
    <acme:button code="assistant.session.list.button.create" action="/assistant/session/create?masterId=${masterId}"/>
</jstl:if>