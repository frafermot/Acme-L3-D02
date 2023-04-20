
package acme.features.student.enrolment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.enrolment.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentEnrolmentRepository extends AbstractRepository {

	@Query("select e from Enrolment e where e.id = :id")
	Enrolment findOneEnrolmentById(int id);

	//@Query("select e from Enrolment e where e.code = :code")
	//Enrolment findOneEnrolmentByCode(String code);

	@Query("select e.code from Enrolment e")
	Collection<String> findAllEnrolmentsCodes();

	@Query("select e from Enrolment e")
	Collection<Enrolment> findAllEnrolments();

	@Query("select s from Student s where s.id = :id")
	Student findOneStudentById(int id);

	@Query("select e from Enrolment e where e.student.id = :studentId")
	Collection<Enrolment> findManyEnrolmentsByStudentId(int studentId);

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select c from Course c")
	Collection<Course> findAllCourses();
}
