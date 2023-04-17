
package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.course.LectureCourse;
import acme.entities.lecture.Lecture;
import acme.enums.Indication;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureCreateService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository repository;

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
		int masterId;
		Course course;

		masterId = super.getRequest().getData("masterId", int.class);
		course = this.repository.findOneCourseByCourseId(masterId);
		status = course != null && !course.isPublished() && super.getRequest().getPrincipal().hasRole(course.getLecturer());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Lecture object;

		object = new Lecture();
		object.setPublished(false);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Lecture object) {
		assert object != null;

		super.bind(object, "title", "lectureAbstract", "estimatedTime", "body", "indicator", "link", "published");
	}

	@Override
	public void validate(final Lecture object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("estimatedTime")) {
			Integer time;
			time = object.getEstimatedTime();
			super.state(time > 0, "estimatedTime", "lecturer.lecture.form.error.negativeTime");
		}
	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;
		Course course;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);
		course = this.repository.findOneCourseByCourseId(masterId);

		this.repository.save(object);

		final LectureCourse lc = new LectureCourse();
		lc.setCourse(course);
		lc.setLecture(object);
		this.repository.save(lc);

	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		SelectChoices indicators;
		Tuple tuple;

		indicators = SelectChoices.from(Indication.class, object.getIndicator());

		tuple = super.unbind(object, "title", "lectureAbstract", "estimatedTime", "body", "indicator", "link", "published");
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));
		tuple.put("indicators", indicators);

		super.getResponse().setData(tuple);
	}

}
