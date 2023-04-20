
package acme.features.student.activity;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.activity.Activity;
import acme.entities.enrolment.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentActivityRepository extends AbstractRepository {

	@Query("select a from Activity a where a.id = :id")
	Activity findOneActivityById(int id);

	@Query("select e from Enrolment e where e.id = :id")
	Enrolment findOneEnrolmentById(int id);

	@Query("select a.enrolment from Activity a where a.id = :id")
	Enrolment findOneEnrolmentByActivityId(int id);

	@Query("select s from Student s where s.id = :id")
	Student findOneStudentById(int id);

	@Query("select a from Activity a where a.enrolment.student.id = :studentId")
	Collection<Activity> findManyActivitiesByStudentId(int studentId);

	@Query("select e from Enrolment e where e.student.id = :studentId and e.cardNibble is not null and e.cardHolder is not null")
	Collection<Enrolment> findFinalisedEnrolmentsByStudentId(int studentId);

	//@Query("select a from Activity a where a.enrolment.id = :enrolmentId")
	//Collection<Activity> findManyActivitiesByEnrolmentId(int enrolmentId);

	//@Query("select e.id from Enrolment e where e.student.id = :studentId")
	//Collection<Integer> findManyEnrolmentsIdByStudentId(int studentId);

	//@Query("select e from Enrolment e where e.student.id = :studentId")
	//Collection<Enrolment> findManyEnrolmentsByStudentId(int studentId);

}
