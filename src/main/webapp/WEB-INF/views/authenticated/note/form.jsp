<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.note.list.label.title" path="title"/>	
	<acme:input-textbox code="authenticated.note.list.label.instantiationMoment" path="instantiationMoment"/>
	<acme:input-textarea code="authenticated.note.list.label.message" path="message"/>
	<acme:input-url code="authenticated.note.list.label.link" path="link"/>
	<acme:input-url code="authenticated.note.list.label.author" path="author"/>
</acme:form>