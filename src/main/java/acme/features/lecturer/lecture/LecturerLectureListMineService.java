
package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.lecture.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureListMineService extends AbstractService<Lecturer, Lecture> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Lecturer lecturer;

		masterId = super.getRequest().getData("masterId", int.class);
		lecturer = this.repository.findOneLecturerByCourseId(masterId);

		status = super.getRequest().getPrincipal().hasRole(lecturer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Lecture> objects;

		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findManyLecturesByCourseId(masterId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;
		int masterId;
		masterId = super.getRequest().getData("masterId", int.class);

		Tuple tuple;

		tuple = super.unbind(object, "title", "lectureAbstract", "indicator", "published");
		super.getResponse().setGlobal("masterId", masterId);

		super.getResponse().setData(tuple);
	}
}
