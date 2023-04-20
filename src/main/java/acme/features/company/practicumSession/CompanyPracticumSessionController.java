
package acme.features.company.practicumSession;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.practicumSession.PracticumSession;
import acme.framework.controllers.AbstractController;
import acme.roles.Company;

@Controller
public class CompanyPracticumSessionController extends AbstractController<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionListService listService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
	}
}
