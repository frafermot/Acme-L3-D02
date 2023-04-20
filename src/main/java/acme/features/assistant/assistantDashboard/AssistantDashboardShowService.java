/*
 * AdministratorDashboardShowService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.assistant.assistantDashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.Statistics;
import acme.forms.AssistantDashboard;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantDashboardShowService extends AbstractService<Assistant, AssistantDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Assistant.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final AssistantDashboard assistantDashboard;

		final Integer numberOfTutorialTheorical;
		final Integer numberOfTutorialHandsOn;

		final Statistics sessionStatistics;
		final Statistics tutorialStatistics;

		final Double tutorialAverage;
		final Double tutorialDeviation;
		final Double tutorialMax;
		final Double tutorialMin;

		final Double sessionAverage;
		final Double sessionDeviation;
		final Double sessionMax;
		final Double sessionMin;

		final Principal principal;
		final Integer userId;

		final List<Integer> estimatedTimes;
		final List<Integer> sessionDurations;

		principal = super.getRequest().getPrincipal();
		userId = principal.getActiveRoleId();

		numberOfTutorialTheorical = this.repository.numberOfTutorialTheorical(userId);
		numberOfTutorialHandsOn = this.repository.numberOfTutorialHandsOn(userId);

		tutorialAverage = this.repository.tutorialAverage(userId);
		estimatedTimes = this.repository.getTutorialEstimatedTimes(userId);
		tutorialDeviation = Math.sqrt(estimatedTimes.stream().mapToDouble(Integer::doubleValue).map(time -> Math.pow(time - estimatedTimes.stream().mapToDouble(Integer::doubleValue).average().orElse(0.0), 2)).average().orElse(0.0));
		tutorialMax = this.repository.tutorialMax(userId);
		tutorialMin = this.repository.tutorialMin(userId);

		sessionAverage = this.repository.sessionAverage(userId);
		sessionDurations = this.repository.getSessionDurations(userId);
		sessionDeviation = Math.sqrt(sessionDurations.stream().mapToDouble(Integer::doubleValue).map(duration -> Math.pow(duration - sessionDurations.stream().mapToDouble(Integer::doubleValue).average().orElse(0.0), 2)).average().orElse(0.0));
		sessionMax = this.repository.sessionMax(userId);
		sessionMin = this.repository.sessionMin(userId);

		tutorialStatistics = new Statistics();
		tutorialStatistics.setAverage(tutorialAverage);
		tutorialStatistics.setDeviation(tutorialDeviation);
		tutorialStatistics.setMax(tutorialMax);
		tutorialStatistics.setMin(tutorialMin);

		sessionStatistics = new Statistics();
		sessionStatistics.setAverage(sessionAverage);
		sessionStatistics.setDeviation(sessionDeviation);
		sessionStatistics.setMax(sessionMax);
		sessionStatistics.setMin(sessionMin);

		assistantDashboard = new AssistantDashboard();
		assistantDashboard.setNumberOfTutorialTheorical(numberOfTutorialTheorical);
		assistantDashboard.setNumberOfTutorialHandsOn(numberOfTutorialHandsOn);
		assistantDashboard.setSessionStatistics(sessionStatistics);
		assistantDashboard.setTutorialStatistics(tutorialStatistics);

		super.getBuffer().setData(assistantDashboard);
	}

	@Override
	public void unbind(final AssistantDashboard object) {
		Tuple tuple;
		final Principal principal;
		final Integer userId;
		final Integer numberOfTutorialBalanced;

		principal = super.getRequest().getPrincipal();
		userId = principal.getActiveRoleId();

		numberOfTutorialBalanced = this.repository.numberOfTutorialBalanced(userId);

		tuple = super.unbind(object, "numberOfTutorialTheorical", "numberOfTutorialHandsOn", "sessionStatistics", "tutorialStatistics");
		tuple.put("ratioOfCourseTheoretical", object.getNumberOfTutorialTheorical() / this.repository.numberTutorials(userId));
		tuple.put("ratioOfCourseHandsOn", object.getNumberOfTutorialHandsOn() / this.repository.numberTutorials(userId));
		tuple.put("ratioOfCourseBalanced", numberOfTutorialBalanced / this.repository.numberTutorials(userId));

		tuple.put("ratioOfSessionTheoretical", this.repository.getNumberOfSessionTheorical(userId) / this.repository.numberSessions(userId));
		tuple.put("ratioOfSessionHandsOn", this.repository.getNumberOfSessionHandsOn(userId) / this.repository.numberSessions(userId));
		tuple.put("ratioOfSessionBalanced", this.repository.numberOfSessionBalanced(userId) / this.repository.numberSessions(userId));

		super.getResponse().setData(tuple);
	}

}
