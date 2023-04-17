
package acme.features.student.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.course.LectureCourse;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentCourseRepository extends AbstractRepository {

	@Query("select c from Course c where c.published = true")
	Collection<Course> findAllPublishedCourses();

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select lc from LectureCourse lc where lc.course.id = :id")
	Collection<LectureCourse> findLecturesByCourseId(int id);

}
