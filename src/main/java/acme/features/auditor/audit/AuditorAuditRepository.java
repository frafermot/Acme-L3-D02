
package acme.features.auditor.audit;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audit.Audit;
import acme.entities.auditingRecords.AuditingRecords;
import acme.entities.course.Course;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuditorAuditRepository extends AbstractRepository {

	@Query("select a from Audit a where a.auditor.id = :auditorId")
	Collection<Audit> findManyAuditsByAuditorId(int auditorId);

	@Query("select a.course from Audit a")
	List<Course> findAllCoursesFromAudit();

	@Query("select a.course from Audit a Where a.id != :id")
	List<Course> findAllCoursesFromAuditWithId(int id);

	@Query("select a from Audit a where a.id = :id")
	Audit findOneAuditById(int id);

	@Query("select a from Auditor a where a.id = :auditorId")
	Auditor findOneAuditorById(int auditorId);

	@Query("select a from Audit a where a.code = :code")
	Optional<Audit> findOneAuditByCode(String code);

	@Query("select ar from AuditingRecords ar where ar.audit.id = :auditId")
	Collection<AuditingRecords> findAuditingRecordsByAuditId(int auditId);

	@Query("select c from Course c where c.id = :id")
	Course findCourse(int id);

	@Query("select c from Course c")
	List<Course> findAllCourses();

	@Query("SELECT COUNT(a) = 0 FROM Audit a JOIN a.course c WHERE c.id = :id")
	boolean checkEmptyCourseById(int id);

}
