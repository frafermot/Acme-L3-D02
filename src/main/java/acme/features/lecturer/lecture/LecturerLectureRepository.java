
package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.lecture.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerLectureRepository extends AbstractRepository {

	@Query("select l from Lecture l where l.id = :id")
	Lecture findOneLectureById(int id);

	@Query("select lc.lecture from LectureCourse lc where lc.course.id = :masterId")
	Collection<Lecture> findManyLecturesByMasterId(int masterId);

	@Query("select c.lecturer from Course c where c.id = :courseId")
	Lecturer findOneLecturerByCourseId(int courseId);

	@Query("select lc.course.lecturer from LectureCourse lc where lc.lecture.id = :lectureId")
	Lecturer findOneLecturerByLectureId(int lectureId);

	@Query("select c from Course c where c.id = :courseId")
	Course findOneCourseByCourseId(int courseId);
}
