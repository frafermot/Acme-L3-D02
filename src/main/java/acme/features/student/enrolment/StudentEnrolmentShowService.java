
package acme.features.student.enrolment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentShowService extends AbstractService<Student, Enrolment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentEnrolmentRepository repository;

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
		Enrolment enrolment;
		Student student;
		//final Principal principal;

		masterId = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findOneEnrolmentById(masterId);
		student = enrolment == null ? null : enrolment.getStudent();
		status = super.getRequest().getPrincipal().hasRole(student);

		//principal = super.getRequest().getPrincipal();

		//status = student == null ? false : principal.getActiveRoleId() == student.getId();
		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Enrolment object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneEnrolmentById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		boolean finalised;
		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "title", object.getCourse());

		finalised = object.getCardHolder() != null && object.getCardNibble() != null ? true : false;

		tuple = super.unbind(object, "code", "motivation", "goals", "workTime", "cardHolder", "cardNibble");
		if (finalised)
			tuple.put("readonly", true);
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		tuple.put("finalised", finalised);
		super.getResponse().setData(tuple);

	}
}
