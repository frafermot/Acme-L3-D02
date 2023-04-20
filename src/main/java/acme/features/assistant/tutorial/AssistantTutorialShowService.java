
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialShowService extends AbstractService<Assistant, Tutorial> {

	public static final String[]			ATTRIBUTES	= {
		"title", "code", "tutorialAbstract", "goals", "estimatedTime", "published"
	};

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialRepository	repository;

	// AbstractService interface ----------------------------------------------


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
		Tutorial tutorial;
		Collection<Tutorial> myTutorials;
		Principal principal;
		boolean restriction1;
		boolean restriction2;

		id = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findTutorialById(id);
		principal = super.getRequest().getPrincipal();
		myTutorials = this.repository.findTutorialsByAssistantId(principal.getActiveRoleId());
		restriction1 = tutorial != null && !tutorial.isPublished() && principal.hasRole(Assistant.class) && myTutorials.contains(tutorial);
		restriction2 = tutorial != null && tutorial.isPublished() && principal.hasRole(Authenticated.class);
		status = restriction1 || restriction2;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Tutorial object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTutorialById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;
		Tuple tuple;
		Collection<Course> courses;
		SelectChoices choices;
		Principal principal;
		Collection<Tutorial> myTutorials;

		courses = this.repository.findAccessibleCourses();
		choices = SelectChoices.from(courses, "title", object.getCourse());

		principal = super.getRequest().getPrincipal();
		myTutorials = this.repository.findTutorialsByAssistantId(principal.getActiveRoleId());
		tuple = super.unbind(object, AssistantTutorialCreateService.ATTRIBUTES);
		tuple.put("courses", choices);
		tuple.put("assistant", object.getAssistant().getSupervisor());
		tuple.put("showSessions", object.isPublished() && principal.hasRole(Assistant.class) && myTutorials.contains(object));
		tuple.put("showAssistant", object.isPublished());

		super.getResponse().setData(tuple);
	}
}
