
package acme.features.student.activity;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.activity.Activity;
import acme.entities.enrolment.Enrolment;
import acme.enums.Indication;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityCreateService extends AbstractService<Student, Activity> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Activity object;

		object = new Activity();

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Activity object) {
		assert object != null;

		int enrolmentId;
		Enrolment enrolment;

		enrolmentId = super.getRequest().getData("enrolment", int.class);
		enrolment = this.repository.findOneEnrolmentById(enrolmentId);

		super.bind(object, "title", "activityAbstract", "indicator", "periodStart", "periodEnd", "link");
		object.setEnrolment(enrolment);
	}

	@Override
	public void validate(final Activity object) {
		assert object != null;
	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		int studentId;
		Collection<Enrolment> enrolments;
		SelectChoices indicators;
		SelectChoices choices;
		Tuple tuple;

		studentId = super.getRequest().getPrincipal().getActiveRoleId();

		enrolments = this.repository.findFinalisedEnrolmentsByStudentId(studentId);
		choices = SelectChoices.from(enrolments, "code", object.getEnrolment());
		indicators = SelectChoices.from(Indication.class, object.getIndicator());

		tuple = super.unbind(object, "title", "activityAbstract", "indicator", "periodStart", "periodEnd", "link");
		tuple.put("enrolment", choices.getSelected().getKey());
		tuple.put("enrolments", choices);
		tuple.put("indicators", indicators);
		tuple.put("finalised", true);

		super.getResponse().setData(tuple);
	}

}
