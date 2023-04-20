
package acme.features.administrator.bulletin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.bulletin.Bulletin;
import acme.framework.components.accounts.Administrator;
import acme.framework.controllers.AbstractController;

@Controller
public class AdministratorBulletinController extends AbstractController<Administrator, Bulletin> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBulletinListService		listService;

	@Autowired
	protected AdministratorBulletinShowService		showService;

	@Autowired
	protected AdministratorBulletinCreateService	createService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
	}

}
