<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="lecturer.lecture.list.label.title" path="title" width="10%"/>
	<acme:list-column code="lecturer.lecture.list.label.lectureAbstract" path="lectureAbstract" width="30%"/>
	<acme:list-column code="lecturer.lecture.list.label.indicator" path="indicator" width="50%"/>
	<acme:list-column code="lecturer.lecture.list.label.published" path="published" width="10%"/>
</acme:list>

<acme:button code="lecturer.lecture.list.button.create" action="/lecturer/lecture/create?masterId=${masterId}"/>