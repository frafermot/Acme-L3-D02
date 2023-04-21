
package acme.features.company.practicum;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.practicum.Practicum;
import acme.enums.Indication;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumCreateService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticumRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Company.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Practicum object;
		Company company;

		company = this.repository.findCompanyByUserId(super.getRequest().getPrincipal().getAccountId());

		object = new Practicum();
		object.setPublished(false);
		object.setCompany(company);
		object.setEstimatedTotalTime(0.);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Practicum object) {
		assert object != null;
		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);

		super.bind(object, "code", "title", "practicumAbstract", "goals", "estimatedTotalTime", "published");
		object.setCourse(course);
	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Optional<Practicum> optPracticum;

			optPracticum = this.repository.findPracticumByCode(object.getCode());
			if (optPracticum.isPresent())
				super.state(optPracticum == null, "code", "company.practicum.form.error.duplicated");

		}
	}

	@Override
	public void perform(final Practicum object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		final SelectChoices choice;
		final Collection<Course> courses;
		final Tuple tuple;

		courses = this.repository.findAllCourses().stream().filter(x -> x.getIndicator().equals(Indication.HANDS_ON)).collect(Collectors.toList());
		choice = SelectChoices.from(courses, "title", object.getCourse());

		tuple = super.unbind(object, "code", "title", "practicumAbstract", "goals", "estimatedTotalTime", "published", "company");
		tuple.put("courses", choice);
		tuple.put("company", object.getCompany().getName());

		super.getResponse().setData(tuple);
	}
}
