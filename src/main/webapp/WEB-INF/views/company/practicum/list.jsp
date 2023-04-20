<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="company.practicum.list.label.code" path="code" width="30%"/>
	<acme:list-column code="company.practicum.list.label.title" path="title" width="30%"/>
	<acme:list-column code="company.practicum.list.label.estimatedTotalTime" path="estimatedTotalTime" width="20%"/>
	<acme:list-column code="company.practicum.list.label.published" path="published" width="20%"/>
</acme:list>

<acme:button code="company.practicum.form.button.create" action="/company/practicum/create"/>