
package acme.features.lecturer.lecture;

import java.util.Collection;

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

		final Collection<Lecture> lectures = this.repository.findManyLecturesByCourseId(masterId);
		int numTeoricos = 0;
		int numPracticos = 0;
		for (final Lecture lecture : lectures)
			if (lecture.getIndicator().equals(Indication.THEORETICAL))
				numTeoricos++;
			else if (lecture.getIndicator().equals(Indication.HANDS_ON))
				numPracticos++;
		if (numTeoricos > numPracticos)
			course.setIndicator(Indication.THEORETICAL);
		else if (numPracticos > numTeoricos)
			course.setIndicator(Indication.HANDS_ON);
		else
			course.setIndicator(Indication.BALANCED);

		this.repository.save(course);
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
