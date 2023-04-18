/*
 * AdministratorDashboardShowService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.assistant.assistantDashboard;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import acme.Statistics;
import acme.forms.AssistantDashboard;
import acme.framework.components.datatypes.Money;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.StringHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantDashboardPerformService extends AbstractService<Assistant, AssistantDashboard> {

	// AbstractService interface ----------------------------------------------

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		AssistantDashboard object;

		object = new AssistantDashboard();

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AssistantDashboard object) {
		assert object != null;

		super.bind(object, "numberOfTutorialTheorical", "numberOfTutorialHandsOn", "sessionStatistics", "tutorialStatistics");
	}

	@Override
	public void validate(final AssistantDashboard object) {
		assert object != null;
	}

	@Override
	public void perform(final AssistantDashboard object) {
		assert object != null;

		Integer nTutorialTheorical, nTutorialHandsOn;
		final Statistics sessionStatistics, tutorialStatistics;
		final AssistantDashboard dashboard;

		nTutorialTheorical = super.getRequest().getData("numberOfTutorialTheorical", Integer.class);
		nTutorialHandsOn = super.getRequest().getData("numberOfTutorialHandsOn", Integer.class);
		//dashboard = this.computeMoneyExchange(source, targetCurrency);
		//super.state(exchange != null, "*", "authenticated.money-exchange.form.label.api-error");
		//if (exchange == null) {
		//	object.setTarget(null);
		//	object.setDate(null);
		//} else {
		//	target = exchange.getTarget();
		//	object.setTarget(target);
		//	date = exchange.getDate();
		//	object.setDate(date);
		//}
	}

	@Override
	public void unbind(final AssistantDashboard object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "source", "targetCurrency", "date", "target");

		super.getResponse().setData(tuple);
	}

	// Ancillary methods ------------------------------------------------------

	public AssistantDashboard computeMoneyExchange(final Money source, final String targetCurrency) {
		assert source != null;
		assert !StringHelper.isBlank(targetCurrency);

		//MoneyExchange result;
		RestTemplate api;
		//ExchangeRate record;
		String sourceCurrency;
		Double sourceAmount;
		final Double targetAmount;
		final Double rate;
		Money target;

		try {
			api = new RestTemplate();

			sourceCurrency = source.getCurrency();
			sourceAmount = source.getAmount();

			//record = api.getForObject( //
			//"https://api.exchangerate.host/latest?base={0}&symbols={1}", //
			//ExchangeRate.class, //
			//sourceCurrency, //
			//targetCurrency //
			//);

			//assert record != null;
			//rate = record.getRates().get(targetCurrency);
			//targetAmount = rate * sourceAmount;

			target = new Money();
			//target.setAmount(targetAmount);
			target.setCurrency(targetCurrency);

			//result = new AssistantDashboard();
			//result.setSource(source);
			//result.setTargetCurrency(targetCurrency);
			//result.setDate(record.getDate());
			//result.setTarget(target);
		} catch (final Throwable oops) {
			//result = null;
			return null;
		}

		//return result;
		return null;
	}

}
