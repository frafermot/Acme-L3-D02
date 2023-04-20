<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.banner.list.label.slogan" path="slogan" width="40%"/>
	<acme:list-column code="administrator.banner.list.label.periodStart" path="periodStart" width="20%"/>
	<acme:list-column code="administrator.banner.list.label.periodEnd" path="periodEnd" width="20%"/>
	<acme:list-column code="administrator.banner.list.label.instantiationUpdateMoment" path="instantiationUpdateMoment" width="20%"/>
</acme:list>
<acme:button code="administrator.banner.list.button.create" action="/administrator/banner/create"/>
