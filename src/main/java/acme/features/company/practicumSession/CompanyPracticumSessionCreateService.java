
package acme.features.company.practicumSession;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicumSession.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionCreateService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository repository;


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
		Practicum practicum;
		Company company;

		company = this.repository.findCompanyByUserId(super.getRequest().getPrincipal().getAccountId());
		masterId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findPracticumById(masterId);
		status = practicum != null && super.getRequest().getPrincipal().hasRole(Company.class) && practicum.getCompany().getId() == company.getId();

		super.getResponse().setAuthorised(status && !practicum.isPublished());
	}

	@Override
	public void load() {
		PracticumSession object;
		int masterId;
		Practicum practicum;

		masterId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findPracticumById(masterId);

		object = new PracticumSession();
		object.setPublished(false);
		object.setPracticum(practicum);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final PracticumSession object) {
		assert object != null;

		super.bind(object, "title", "sessionAbstract", "periodStart", "periodEnd", "link", "published");
	}

	@Override
	public void validate(final PracticumSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("periodStart")) {
			Date minStart;

			minStart = MomentHelper.deltaFromMoment(MomentHelper.getCurrentMoment(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfterOrEqual(object.getPeriodStart(), minStart), "periodStart", "company.practicumSession.form.error.periodStart");
		}

		if (!super.getBuffer().getErrors().hasErrors("periodEnd")) {
			Date minEnd;

			minEnd = MomentHelper.deltaFromMoment(object.getPeriodStart(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfterOrEqual(object.getPeriodEnd(), minEnd), "periodEnd", "company.practicumSession.form.error.periodEnd");
		}
	}

	@Override
	public void perform(final PracticumSession object) {
		assert object != null;
		Practicum practicum;
		int masterId;
		Collection<PracticumSession> sessions;
		double totalTime;

		this.repository.save(object);

		masterId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findPracticumById(masterId);

		sessions = this.repository.findAllSessionByPracticumId(masterId);
		totalTime = sessions.stream().mapToDouble(x -> x.getPeriodEnd().getTime() - x.getPeriodStart().getTime()).sum();

		practicum.setEstimatedTotalTime(totalTime / (1000 * 60 * 60));
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "sessionAbstract", "periodStart", "periodEnd", "link", "published");
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));

		super.getResponse().setData(tuple);
	}
}
