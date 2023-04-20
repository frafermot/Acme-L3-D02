<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<h2>
	<acme:message code="assistant.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.number-of-tutorial-theorical"/>
		</th>
		<td>
			<acme:print value="${numberOfTutorialTheorical}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.number-of-tutorial-hands-on"/>
		</th>
		<td>
			<acme:print value="${numberOfTutorialHandsOn}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.average-tutorial"/>
		</th>
		<td>
			<acme:print value="${tutorialStatistics.average}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.deviation-tutorial"/>
		</th>
		<td>
			<acme:print value="${tutorialStatistics.deviation}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.max-tutorial"/>
		</th>
		<td>
			<acme:print value="${tutorialStatistics.max}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.min-tutorial"/>
		</th>
		<td>
			<acme:print value="${tutorialStatistics.min}"/>
		</td>
	</tr>
		<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.average-session"/>
		</th>
		<td>
			<acme:print value="${sessionStatistics.average}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.deviation-session"/>
		</th>
		<td>
			<acme:print value="${sessionStatistics.deviation}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.max-session"/>
		</th>
		<td>
			<acme:print value="${sessionStatistics.max}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.min-session"/>
		</th>
		<td>
			<acme:print value="${sessionStatistics.min}"/>
		</td>
	</tr>
</table>

<div style="display: flex; flex-direction: row;">
  <div style="flex: 1;">
    <h2><acme:message code="assistant.dashboard.form.title.application-indication-tutorial"/></h2>
    <canvas id="CourseIndications"></canvas>
  </div>
  <div style="flex: 1;">
    <h2><acme:message code="assistant.dashboard.form.title.application-indication-session"/></h2>
    <canvas id="SessionIndications"></canvas>
  </div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var courseData = {
			labels : [
				"THEORETICAL", "HANDS_ON", "BALANCED"
			],
			datasets : [
				{
					data : [
						<jstl:out value="${ratioOfCourseTheoretical}"/>, 
						<jstl:out value="${ratioOfCourseHandsOn}"/>, 
						<jstl:out value="${ratioOfCourseBalanced}"/>
					],
					backgroundColor: [
						'rgba(54, 162, 235, 0.2)', // Blue
						'rgba(255, 99, 132, 0.2)', // Red
						'rgba(255, 206, 86, 0.2)', // Yellow
					]
				}
			]
		};
		var courseOptions = {
			legend : {
				display : true
			}
		};
	
		var courseCanvas, courseContext;
	
		courseCanvas = document.getElementById("CourseIndications");
		courseContext = courseCanvas.getContext("2d");
		new Chart(courseContext, {
			type : "pie",
			data : courseData,
			options : courseOptions
		});
		
		var sessionData = {
			labels : [
				"THEORETICAL", "HANDS_ON", "BALANCED"
			],
			datasets : [
				{
					data : [
						<jstl:out value="${ratioOfSessionTheoretical}"/>, 
						<jstl:out value="${ratioOfSessionHandsOn}"/>, 
						<jstl:out value="${ratioOfSessionBalanced}"/>
					],
					backgroundColor: [
						'rgba(54, 162, 235, 0.2)', // Blue
						'rgba(255, 99, 132, 0.2)', // Red
						'rgba(255, 206, 86, 0.2)', // Yellow
					]
				}
			]
		};
		var sessionOptions = {
			legend : {
				display : true
			}
		};
	
		var sessionCanvas, sessionContext;
	
		sessionCanvas = document.getElementById("SessionIndications");
		sessionContext = sessionCanvas.getContext("2d");
		new Chart(sessionContext, {
			type : "pie",
			data : sessionData,
			options : sessionOptions
		});
	});
</script>

<acme:return/>

