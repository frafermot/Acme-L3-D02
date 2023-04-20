
package acme.features.assistant.session;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.session.Session;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionListService extends AbstractService<Assistant, Session> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantSessionRepository repository;

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
		Collection<Session> sessions;

		tutorialId = super.getRequest().getData("masterId", int.class);
		sessions = this.repository.findSessionsByTutorialId(tutorialId);

		super.getBuffer().setData(sessions);
	}

	@Override
	public void unbind(final Session object) {
		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "title", "indication", "periodStart", "periodEnd", "published");

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<Session> objects) {
		assert objects != null;

		int tutorialId;
		Tutorial tutorial;

		tutorialId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findTutorialById(tutorialId);

		super.getResponse().setGlobal("masterId", tutorialId);
		super.getResponse().setGlobal("showCreate", tutorial.isPublished());
	}

}
