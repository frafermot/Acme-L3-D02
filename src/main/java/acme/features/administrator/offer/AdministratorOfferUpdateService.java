
package acme.features.administrator.offer;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferUpdateService extends AbstractService<Administrator, Offer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorOfferRepository repository;

	// AbstractService<Administrator, Offer> -------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Offer object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneOfferById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Offer object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "heading", "summary", "price", "link", "periodStart", "periodEnd");
	}

	@Override
	public void validate(final Offer object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("periodStart")) {
			Date minStart;

			minStart = MomentHelper.deltaFromMoment(object.getInstantiationMoment(), 1, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfterOrEqual(object.getPeriodStart(), minStart), "periodStart", "administrator.offer.form.error.periodstart");
		}

		if (!super.getBuffer().getErrors().hasErrors("periodEnd")) {
			Date minEnd;

			minEnd = MomentHelper.deltaFromMoment(object.getPeriodStart(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfterOrEqual(object.getPeriodEnd(), minEnd), "periodEnd", "administrator.offer.form.error.periodend");
		}

		if (!super.getBuffer().getErrors().hasErrors("price"))
			super.state(object.getPrice().getAmount() > 0, "price", "administrator.offer.form.error.price");

	}

	@Override
	public void perform(final Offer object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiationMoment", "heading", "summary", "price", "link", "periodStart", "periodEnd");

		super.getResponse().setData(tuple);
	}

}
