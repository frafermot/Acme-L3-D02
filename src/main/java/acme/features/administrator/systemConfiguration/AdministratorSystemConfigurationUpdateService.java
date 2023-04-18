
package acme.features.administrator.systemConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.systemConfiguration.SystemConfiguration;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorSystemConfigurationUpdateService extends AbstractService<Administrator, SystemConfiguration> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorSystemConfigurationRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {

		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		status = principal.hasRole(Administrator.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SystemConfiguration object;

		object = this.repository.findSystemConfiguration();

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final SystemConfiguration object) {
		assert object != null;

		super.bind(object, "acceptedCurrencies", "systemCurrency");

	}

	@Override
	public void validate(final SystemConfiguration object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("systemCurrency")) {
			final SystemConfiguration systConf = this.repository.findSystemConfiguration();
			final String acceptedCurrencies = systConf.getAcceptedCurrencies();
			final String[] currencies = object.getAcceptedCurrencies().split(",");
			boolean currencyExists = false;
			boolean currencyExists2 = false;
			for (final String currency : currencies)
				if (object.getSystemCurrency().equals(currency)) {
					currencyExists = true;
					break;
				} else if (acceptedCurrencies.contains(currency)) {
					currencyExists2 = true;
					break;
				}
			super.state(currencyExists, "systemCurrency", "administrator.config.form.error.unavailable");
			super.state(currencyExists2, "acceptedCurrencies", "administrator.config.form.error.unavailable2");
		}
	}

	@Override
	public void perform(final SystemConfiguration object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final SystemConfiguration object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "acceptedCurrencies", "systemCurrency");

		super.getResponse().setData(tuple);
	}
}
