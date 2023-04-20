
package acme.features.auditor.audit;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.course.Course;
import acme.enums.Mark;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditCreateService extends AbstractService<Auditor, Audit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Auditor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Audit object = new Audit();
		Auditor auditor;

		auditor = this.repository.findOneAuditorById(super.getRequest().getPrincipal().getActiveRoleId());

		object.setPublished(false);
		object.setAuditor(auditor);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Audit object) {
		assert object != null;

		super.bind(object, "code", "conclusion", "strongPoints", "weakPoints", "mark", "published", "course");

		final Course course = this.repository.findCourse(super.getRequest().getData("id", int.class));
		object.setCourse(course);
	}

	@Override
	public void validate(final Audit object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Optional<Audit> existing;

			existing = this.repository.findOneAuditByCode(object.getCode());
			if (existing.isPresent())
				super.state(existing == null, "code", "auditor.audit.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("course")) {
			boolean emptyCourse;

			emptyCourse = this.repository.checkEmptyCourseById(object.getCourse().getId());
			super.state(emptyCourse, "course", "javax.validation.constraints.AssertTrue.message");
		}

	}

	@Override
	public void perform(final Audit object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		SelectChoices marks;
		final SelectChoices courses2;
		Tuple tuple;
		Collection<Course> courses;

		marks = SelectChoices.from(Mark.class, object.getMark());

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "mark", "published", "course");
		tuple.put("marks", marks);

		courses = this.repository.findAllCourses();

		final List<Course> auditsCourse = this.repository.findAllCoursesFromAudit();

		courses.removeAll(auditsCourse);

		courses2 = SelectChoices.from(courses, "title", object.getCourse());
		tuple.put("courses", courses2);

		super.getResponse().setData(tuple);
	}

	//	@Override
	//	public void onSuccess() {
	//		if (super.getRequest().getMethod().equals(HttpMethod.POST))
	//			PrincipalHelper.handleUpdate();
	//	}

}
