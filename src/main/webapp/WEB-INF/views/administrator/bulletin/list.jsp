<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.bulletin.list.label.title" path="title" width="50%"/>
	<acme:list-column code="authenticated.bulletin.list.label.message" path="message" width="30%"/>
	<acme:list-column code="authenticated.bulletin.list.label.instantiationMoment" path="instantiationMoment" width="20%"/>
</acme:list>

<acme:button code="administrator.bulletin.form.button.create" action="/administrator/bulletin/create"/>