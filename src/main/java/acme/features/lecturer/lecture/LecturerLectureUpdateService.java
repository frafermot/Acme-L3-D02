
package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.lecture.Lecture;
import acme.enums.Indication;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureUpdateService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository repository;

	// AbstractService interface ----------------------------------------------ç


	@Override
	public void authorise() {
		boolean status;
		int id;
		Lecture lecture;
		Lecturer lecturer;
		Course course;

		id = super.getRequest().getData("id", int.class);
		lecture = this.repository.findOneLectureById(id);
		course = this.repository.findOneCourseByLectureId(id);
		lecturer = lecture == null ? null : course.getLecturer();
		status = super.getRequest().getPrincipal().hasRole(lecturer);

		super.getResponse().setAuthorised(status && !course.isPublished() && !lecture.isPublished());
	}

	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void load() {
		Lecture object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneLectureById(id);

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

		this.repository.save(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		SelectChoices indicators;
		Tuple tuple;

		indicators = SelectChoices.from(Indication.class, object.getIndicator());

		tuple = super.unbind(object, "title", "lectureAbstract", "estimatedTime", "body", "indicator", "link", "published");
		tuple.put("indicators", indicators);

		super.getResponse().setData(tuple);
	}
}
