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

package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.lecture.Lecture;
import acme.enums.Indication;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCoursePublishService extends AbstractService<Lecturer, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseRepository repository;

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
		Course course;
		Principal principal;

		id = super.getRequest().getData("id", int.class);
		principal = super.getRequest().getPrincipal();
		course = this.repository.findOneCourseById(id);

		status = course != null && !course.isPublished() && principal.hasRole(course.getLecturer());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Course object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCourseById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;

		super.bind(object, "code", "title", "courseAbstract", "indicator", "retailPrice", "link", "published");
	}

	@Override
	public void validate(final Course object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("indicator")) {
			int id;
			id = super.getRequest().getData("id", int.class);

			final Collection<Lecture> lectures = this.repository.findManyLecturesByCourseId(id);
			final boolean onlyTheoretical = lectures.stream().allMatch(x -> x.getIndicator().equals(Indication.THEORETICAL));
			super.state(!onlyTheoretical, "indicator", "lecturer.course.form.error.onlyTheoretical");
		}
		final Collection<Lecture> lectures = this.repository.findManyLecturesByCourseId(object.getId());
		final boolean allPublished = lectures.stream().allMatch(x -> x.isPublished() == true);
		super.state(allPublished, "*", "lecturer.course.form.error.allPublished");

	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		object.setPublished(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(Indication.class, object.getIndicator());

		tuple = super.unbind(object, "code", "title", "courseAbstract", "indicator", "retailPrice", "link", "published");
		tuple.put("indications", choices);

		super.getResponse().setData(tuple);
	}
}
