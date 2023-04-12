<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.peep.list.label.title" path="title" width="50%"/>
	<acme:list-column code="any.peep.list.label.nick" path="nick" width="35%"/>
	<acme:list-column code="any.peep.list.label.instantiationMoment" path="instantiationMoment" width="15%"/>
</acme:list>

<acme:button code="any.peep.form.button.create" action="/any/peep/create"/>