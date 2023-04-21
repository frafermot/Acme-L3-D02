
package acme.features.company.practicumSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicum.Practicum;
import acme.entities.practicumSession.PracticumSession;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyPracticumSessionRepository extends AbstractRepository {

	@Query("select ps from PracticumSession ps where ps.practicum.id = :id")
	Collection<PracticumSession> findAllSessionByPracticumId(int id);

	@Query("select p from Practicum p where p.id = :id")
	Practicum findPracticumById(int id);

	@Query("select c from Company c where c.userAccount.id = :id")
	Company findCompanyByUserId(int id);

	@Query("select ps from PracticumSession ps where ps.id = :id")
	PracticumSession findSessionById(int id);
}
