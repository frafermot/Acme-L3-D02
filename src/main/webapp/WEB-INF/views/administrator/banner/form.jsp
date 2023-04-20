<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="administrator.banner.form.label.slogan" path="slogan"/>
	<acme:input-moment code="administrator.banner.form.label.periodStart" path="periodStart"/>	
	<acme:input-moment code="administrator.banner.form.label.periodEnd" path="periodEnd"/>
	<acme:input-url code="administrator.banner.form.label.linkPicture" path="linkPicture"/>
	<acme:input-url code="administrator.banner.form.label.linkTarget" path="linkTarget"/>
	<acme:hidden-data path="id"/>

	<jstl:choose>
		<jstl:when test="${ _command == 'create'}">
			<acme:hidden-data path="instantiationUpdateMoment"/>
			<acme:submit code="administrator.banner.form.button.create" action="/administrator/banner/create"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
			<acme:input-moment code="administrator.banner.form.label.instantiationUpdateMoment" path="instantiationUpdateMoment" readonly="true"/>
			<acme:submit code="administrator.banner.form.button.update" action="/administrator/banner/update"/>
			<acme:submit code="administrator.banner.form.button.delete" action="/administrator/banner/delete"/>
		</jstl:when>
	</jstl:choose>
</acme:form>