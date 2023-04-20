
package acme.features.student.activity;

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
public class StudentActivityShowService extends AbstractService<Student, Activity> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityRepository repository;

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

		masterId = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findOneEnrolmentByActivityId(masterId);
		student = enrolment == null ? null : enrolment.getStudent();
		status = super.getRequest().getPrincipal().hasRole(student);

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Activity object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneActivityById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		boolean finalised;

		SelectChoices indicators;
		Tuple tuple;

		finalised = object.getEnrolment().getCardHolder() != null && object.getEnrolment().getCardNibble() != null ? true : false;
		indicators = SelectChoices.from(Indication.class, object.getIndicator());

		tuple = super.unbind(object, "title", "activityAbstract", "indicator", "periodStart", "periodEnd", "link");
		if (!finalised)
			tuple.put("readonly", true);
		tuple.put("enrolment", object.getEnrolment().getCode());
		tuple.put("indicators", indicators);
		tuple.put("finalised", finalised);

		super.getResponse().setData(tuple);

	}
}
