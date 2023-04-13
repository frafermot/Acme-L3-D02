
package acme.features.lecturer.lectures;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.lecture.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerLectureRepository extends AbstractRepository {

	@Query("select l from Lecture l where l.id = :id")
	Lecture findOneLectureById(int id);

	@Query("select lc.lecture from LectureCourse lc where lc.course.id = :masterId")
	Collection<Lecture> findManyLecturesByMasterId(int masterId);
}
