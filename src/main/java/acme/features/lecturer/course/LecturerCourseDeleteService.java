
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.course.LectureCourse;
import acme.enums.Indication;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseDeleteService extends AbstractService<Lecturer, Course> {

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
		int masterId;
		Course course;
		Lecturer lecturer;

		masterId = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(masterId);
		lecturer = course == null ? null : course.getLecturer();
		status = super.getRequest().getPrincipal().hasRole(lecturer);

		super.getResponse().setAuthorised(status && !course.isPublished());
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
	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		final Collection<LectureCourse> lectureCourses;

		lectureCourses = this.repository.findLectureCoursesByCourseId(object.getId());
		this.repository.deleteAll(lectureCourses);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		SelectChoices indicators;
		Tuple tuple;

		indicators = SelectChoices.from(Indication.class, object.getIndicator());

		tuple = super.unbind(object, "code", "title", "courseAbstract", "indicator", "retailPrice", "link", "published");
		tuple.put("indicators", indicators);

		super.getResponse().setData(tuple);
	}

}
