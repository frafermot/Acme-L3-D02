
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
public class AuditorAuditUpdateService extends AbstractService<Auditor, Audit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditRepository repository;

	// AbstractService interface ----------------------------------------------ç


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Audit Audit;
		Auditor Auditor;

		masterId = super.getRequest().getData("id", int.class);
		Audit = this.repository.findOneAuditById(masterId);
		Auditor = Audit == null ? null : Audit.getAuditor();
		status = super.getRequest().getPrincipal().hasRole(Auditor);

		super.getResponse().setAuthorised(status && !Audit.isPublished());
	}

	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void load() {
		Audit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Audit object) {
		assert object != null;

		super.bind(object, "code", "conclusion", "strongPoints", "weakPoints", "mark", "published");
	}

	@Override
	public void validate(final Audit object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			boolean valid;
			Optional<Audit> existing;

			existing = this.repository.findOneAuditByCode(object.getCode());
			if (!existing.isPresent())
				valid = true;
			else if (existing.get().getId() == object.getId())
				valid = true;
			else
				valid = false;
			super.state(valid, "code", "Auditor.Audit.form.error.duplicated");
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

		courses = this.repository.findAllCourses();

		final List<Course> auditsCourse = this.repository.findAllCoursesFromAudit();

		courses.removeAll(auditsCourse);

		courses.add(object.getCourse());

		courses2 = SelectChoices.from(courses, "title", object.getCourse());

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "mark", "published", "course");
		tuple.put("marks", marks);
		tuple.put("courses", courses2);

		super.getResponse().setData(tuple);
	}

}
