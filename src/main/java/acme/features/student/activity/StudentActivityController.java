
package acme.features.student.activity;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.activity.Activity;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Controller
public class StudentActivityController extends AbstractController<Student, Activity> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityListMineService	listMineService;

	@Autowired
	protected StudentActivityShowService		showService;

	@Autowired
	protected StudentActivityCreateService		createService;

	@Autowired
	protected StudentActivityUpdateService		updateService;

	@Autowired
	protected StudentActivityDeleteService		deleteService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);

		super.addCustomCommand("list-mine", "list", this.listMineService);

	}
}
