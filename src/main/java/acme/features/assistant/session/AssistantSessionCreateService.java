
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
public class AssistantSessionCreateService extends AbstractService<Assistant, Session> {

	public static final String[]			ATTRIBUTES	= {
		"title", "sessionAbstract", "periodStart", "periodEnd", "link", "published"
	};

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantSessionRepository	repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("masterId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int tutorialId;
		Tutorial tutorial;
		Collection<Tutorial> myTutorials;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		tutorialId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findTutorialById(tutorialId);
		myTutorials = this.repository.findTutorialsByAssistantId(principal.getActiveRoleId());
		status = tutorial != null && tutorial.isPublished() && principal.hasRole(Assistant.class) && myTutorials.contains(tutorial);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int tutorialId;
		Session object;
		Tutorial tutorial;

		tutorialId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findTutorialById(tutorialId);
		object = new Session();
		object.setPublished(false);
		object.setTutorial(tutorial);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Session object) {
		final Indication indication;
		String indicationName;

		assert object != null;

		indicationName = super.getRequest().getData("indication", String.class);
		indication = Indication.valueOf(indicationName);
		super.bind(object, AssistantSessionCreateService.ATTRIBUTES);
		object.setIndication(indication);
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
		int tutorialId;

		Tuple tuple;
		SelectChoices choices;
		Tutorial tutorial;

		tutorialId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findTutorialById(tutorialId);
		choices = SelectChoices.from(Indication.class, object.getIndication());

		tuple = super.unbind(object, AssistantSessionCreateService.ATTRIBUTES);
		tuple.put("masterId", tutorialId);
		tuple.put("tutorial", tutorial.getTitle());
		tuple.put("indications", choices);

		super.getResponse().setData(tuple);
	}
}
