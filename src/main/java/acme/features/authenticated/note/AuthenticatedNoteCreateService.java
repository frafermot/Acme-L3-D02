
package acme.features.authenticated.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.note.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.UserAccount;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedNoteCreateService extends AbstractService<Authenticated, Note> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedNoteRepository repository;

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
		Note object;
		UserAccount user;

		user = this.repository.findOneUserAccountById(super.getRequest().getPrincipal().getAccountId());

		object = new Note();
		object.setInstantiationMoment(MomentHelper.getCurrentMoment());
		final String autor = user.getUsername() + " - " + user.getIdentity().getSurname() + ", " + user.getIdentity().getName();
		object.setAuthor(autor);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Note object) {
		assert object != null;

		super.bind(object, "title", "message", "link", "emailAddress");
	}

	@Override
	public void validate(final Note object) {
		assert object != null;

		boolean confirmation;

		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");
	}

	@Override
	public void perform(final Note object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Note object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "instantiationMoment", "message", "link", "emailAddress", "author");
		tuple.put("confirmation", false);
		tuple.put("readOnly", false);

		super.getResponse().setData(tuple);
	}
}
