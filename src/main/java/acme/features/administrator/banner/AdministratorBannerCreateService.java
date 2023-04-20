
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banner.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerCreateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBannerRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Banner object;

		object = new Banner();

		object.setInstantiationUpdateMoment(MomentHelper.getCurrentMoment());

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "instantiationUpdateMoment", "periodStart", "periodEnd", "linkPicture", "slogan", "linkTarget");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("periodStart"))
			super.state(MomentHelper.isAfterOrEqual(object.getPeriodStart(), MomentHelper.getCurrentMoment()), "periodStart", "administrator.banner.form.error.periodStart");

		if (!super.getBuffer().getErrors().hasErrors("periodEnd")) {
			Date minEnd;

			minEnd = MomentHelper.deltaFromMoment(object.getPeriodStart(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfterOrEqual(object.getPeriodEnd(), minEnd), "periodEnd", "administrator.banner.form.error.periodEnd");
		}
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		object.setInstantiationUpdateMoment(MomentHelper.getCurrentMoment());

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiationUpdateMoment", "periodStart", "periodEnd", "linkPicture", "slogan", "linkTarget");
		tuple.put("readonly", false);

		super.getResponse().setData(tuple);
	}

}
