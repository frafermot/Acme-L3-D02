
package acme.entities.peep;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

public class PeepListService extends AbstractService<Any, Peep> {

	@Autowired
	protected PeepRepository repository;


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
		Collection<Peep> objects;

		objects = this.repository.findAllPeeps();

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Peep object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "instantiationMoment", "nick");

		super.getResponse().setData(tuple);
	}
}
