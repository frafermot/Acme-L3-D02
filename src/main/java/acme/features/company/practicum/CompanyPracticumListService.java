
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicumSession.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumListService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticumRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Company.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Company company;
		Collection<Practicum> objects;

		company = this.repository.findCompanyByUserId(super.getRequest().getPrincipal().getAccountId());
		objects = this.repository.findAllPracticumByCompanyId(company.getId());

		for (final Practicum p : objects) {
			final double totalTime;
			Collection<PracticumSession> sessions;

			sessions = this.repository.findAllSessionByPracticumId(p.getId());
			totalTime = sessions.stream().mapToDouble(x -> x.getPeriodEnd().getTime() - x.getPeriodStart().getTime()).sum();

			p.setEstimatedTotalTime(totalTime / (1000 * 60 * 60));
		}

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "estimatedTotalTime", "published");

		super.getResponse().setData(tuple);
	}
}
