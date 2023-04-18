/*
 * EmployerJobUpdateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.assistant.session;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.session.Session;
import acme.entities.tutorial.Tutorial;
import acme.enums.Indication;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionUpdateService extends AbstractService<Assistant, Session> {

	public static final String[]			ATTRIBUTES	= {
		"title", "sessionAbstract", "indication", "periodStart", "periodEnd", "link", "published"
	};

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantSessionRepository	repository;

	// AbstractService<Employer, Job> -------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status;
		int id;
		Session session;
		Tutorial tutorial;
		Collection<Tutorial> myTutorials;
		Principal principal;

		id = super.getRequest().getData("id", int.class);
		principal = super.getRequest().getPrincipal();
		session = this.repository.findSessionById(id);
		tutorial = session.getTutorial();
		myTutorials = this.repository.findTutorialsByAssistantId(principal.getActiveRoleId());
		status = tutorial != null && tutorial.isPublished() && myTutorials.contains(tutorial) && session != null && !session.isPublished() && principal.hasRole(Assistant.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Session object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findSessionById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Session object) {
		assert object != null;

		super.bind(object, AssistantSessionDeleteService.ATTRIBUTES);
	}

	@Override
	public void validate(final Session object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("periodStart")) {
			Date minStart;

			minStart = MomentHelper.deltaFromMoment(MomentHelper.getCurrentMoment(), 1, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfterOrEqual(object.getPeriodStart(), minStart), "periodStart", "assistant.session.form.error.periodStart");
		}

		if (!super.getBuffer().getErrors().hasErrors("periodEnd")) {
			Date minEnd;
			Date maxEnd;

			minEnd = MomentHelper.deltaFromMoment(object.getPeriodStart(), 1, ChronoUnit.HOURS);
			maxEnd = MomentHelper.deltaFromMoment(object.getPeriodStart(), 5, ChronoUnit.HOURS);
			super.state(MomentHelper.isAfterOrEqual(object.getPeriodEnd(), minEnd) && MomentHelper.isBeforeOrEqual(object.getPeriodEnd(), maxEnd), "periodEnd", "assistant.session.form.error.periodEnd");
		}
	}

	@Override
	public void perform(final Session object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Session object) {
		assert object != null;

		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(Indication.class, object.getIndication());

		tuple = super.unbind(object, AssistantSessionDeleteService.ATTRIBUTES);
		tuple.put("tutorial", object.getTutorial().getTitle());
		tuple.put("indications", choices);

		super.getResponse().setData(tuple);
	}
}
