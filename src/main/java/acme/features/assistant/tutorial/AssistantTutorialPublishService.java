/*
 * EmployerJobPublishService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialPublishService extends AbstractService<Assistant, Tutorial> {

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
		boolean status;
		int id;
		Tutorial tutorial;
		Collection<Tutorial> myTutorials;
		Principal principal;

		id = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findTutorialById(id);
		principal = super.getRequest().getPrincipal();
		myTutorials = this.repository.findTutorialsByAssistantId(principal.getActiveRoleId());
		status = tutorial != null && !tutorial.isPublished() && principal.hasRole(Assistant.class) && myTutorials.contains(tutorial);

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
	public void bind(final Tutorial object) {
		assert object != null;

		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);

		super.bind(object, AssistantTutorialDeleteService.ATTRIBUTES);
		object.setCourse(course);
	}

	@Override
	public void validate(final Tutorial object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			boolean uniqueCode;
			boolean sameCode;

			uniqueCode = this.repository.existsTutorialByCode(object.getCode());
			sameCode = this.repository.checksameTutorialByCode(object.getCode(), object.getId());
			super.state(uniqueCode || sameCode, "code", "javax.validation.constraints.AssertTrue.message");
		}
		if (!super.getBuffer().getErrors().hasErrors("course")) {
			boolean emptyCourse;
			boolean sameCourse;

			emptyCourse = this.repository.checkEmptyCourseById(object.getCourse().getId());
			sameCourse = this.repository.checkSameCourseById(object.getCourse().getId(), object.getId());
			super.state(emptyCourse || sameCourse, "course", "javax.validation.constraints.AssertTrue.message");
		}
	}

	@Override
	public void perform(final Tutorial object) {
		assert object != null;

		object.setPublished(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "title", object.getCourse());

		tuple = super.unbind(object, AssistantTutorialDeleteService.ATTRIBUTES);
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
