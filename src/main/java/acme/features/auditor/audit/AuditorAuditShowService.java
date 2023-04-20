
package acme.features.auditor.audit;

import java.util.Collection;
import java.util.List;

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
public class AuditorAuditShowService extends AbstractService<Auditor, Audit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditRepository repository;

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
		Audit Audit;
		Auditor Auditor;

		masterId = super.getRequest().getData("id", int.class);
		Audit = this.repository.findOneAuditById(masterId);
		Auditor = Audit == null ? null : Audit.getAuditor();
		status = super.getRequest().getPrincipal().hasRole(Auditor);

		super.getResponse().setAuthorised(status);
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
	public void unbind(final Audit object) {
		assert object != null;

		SelectChoices marks;
		final SelectChoices courses2;
		Tuple tuple;
		Collection<Course> courses;

		marks = SelectChoices.from(Mark.class, object.getMark());

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "mark", "published");
		tuple.put("marks", marks);

		courses = this.repository.findAllCourses();

		final List<Course> auditsCourse = this.repository.findAllCoursesFromAudit();

		courses.removeAll(auditsCourse);

		courses2 = SelectChoices.from(courses, "title", object.getCourse());
		tuple.put("courses", courses2);

		super.getResponse().setData(tuple);
	}

}
