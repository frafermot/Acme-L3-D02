<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.offer.list.label.heading" path="heading" width="50%"/>
	<acme:list-column code="authenticated.offer.list.label.price" path="price" width="30%"/>
	<acme:list-column code="authenticated.offer.list.label.instantiationMoment" path="instantiationMoment" width="20%"/>
</acme:list>