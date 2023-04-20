
package acme.features.auditor.audit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.auditingRecords.AuditingRecords;
import acme.enums.Indication;
import acme.enums.Mark;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditPublishService extends AbstractService<Auditor, Audit> {

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
		final boolean status;
		int id;
		Audit Audit;
		Principal principal;

		id = super.getRequest().getData("id", int.class);
		principal = super.getRequest().getPrincipal();
		Audit = this.repository.findOneAuditById(id);
		status = Audit != null && !Audit.isPublished() && principal.hasRole(Audit.getAuditor());

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
	public void bind(final Audit object) {
		assert object != null;

		super.bind(object, "code", "conclusion", "strongPoints", "weakPoints", "mark", "published", "course");
	}

	@Override
	public void validate(final Audit object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("indicator")) {
			int id;
			id = super.getRequest().getData("id", int.class);

			final Collection<AuditingRecords> auditingRecords = this.repository.findAuditingRecordsByAuditId(id);
			final boolean onlyTheoretical = auditingRecords.stream().allMatch(x -> x.getAudit().getCourse().equals(Indication.THEORETICAL));
			super.state(!onlyTheoretical, "indicator", "Auditor.Audit.form.error.onlyTheoretical");
		}
	}

	@Override
	public void perform(final Audit object) {
		assert object != null;

		object.setPublished(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(Mark.class, object.getMark());

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "mark", "published", "course");
		tuple.put("marks", choices);

		super.getResponse().setData(tuple);
	}

}
