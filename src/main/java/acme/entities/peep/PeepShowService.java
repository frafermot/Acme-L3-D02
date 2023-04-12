
package acme.entities.peep;

import org.springframework.beans.factory.annotation.Autowired;

import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

public class PeepShowService extends AbstractService<Any, Peep> {

	@Autowired
	protected PeepRepository repository;


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
		Peep object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findPeepById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Peep object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "instantiationMoment", "message", "link", "nick", "email");
		tuple.put("readOnly", false);

		super.getResponse().setData(tuple);
	}
}
