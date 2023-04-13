
package acme.features.assistant.session;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.session.Session;
import acme.framework.controllers.AbstractController;
import acme.roles.Assistant;

@Controller
public class AssistantSessionController extends AbstractController<Assistant, Session> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantSessionListService		listAllService;

	@Autowired
	protected AssistantSessionShowService		showService;

	@Autowired
	protected AssistantSessionCreateService		createService;

	@Autowired
	protected AssistantSessionUpdateService		updateService;

	@Autowired
	protected AssistantSessionDeleteService		deleteService;

	@Autowired
	protected AssistantSessionPublishService	publishService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("list", this.listAllService);

		super.addCustomCommand("publish", "update", this.publishService);
	}

}
