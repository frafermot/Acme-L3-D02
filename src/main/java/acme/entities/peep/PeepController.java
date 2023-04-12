
package acme.entities.peep;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.framework.components.accounts.Any;
import acme.framework.controllers.AbstractController;

@Controller
public class PeepController extends AbstractController<Any, Peep> {

	@Autowired
	protected PeepListService	listService;

	@Autowired
	protected PeepShowService	showService;

	@Autowired
	protected PeepCreateService	createService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
	}
}
