
package acme.features.company.practicum;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.practicum.Practicum;
import acme.entities.practicumSession.PracticumSession;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyPracticumRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.company.id = :id")
	Collection<Practicum> findAllPracticumByCompanyId(int id);

	@Query("select p from Practicum p where p.id = :id")
	Practicum findPracticumById(int id);

	@Query("select c from Company c where c.userAccount.id = :id")
	Company findCompanyByUserId(int id);

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select u from UserAccount u where u.id = :id")
	UserAccount findUserById(int id);

	@Query("select c from Course c where c.id = :id")
	Course findCourseById(int id);

	@Query("select ps from PracticumSession ps where ps.practicum.id = :id")
	Collection<PracticumSession> findAllSessionByPracticumId(int id);

	@Query("select p from Practicum p where p.code = :code")
	Optional<Practicum> findPracticumByCode(String code);
}
