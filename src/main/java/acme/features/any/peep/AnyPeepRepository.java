
package acme.features.any.peep;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.peep.Peep;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyPeepRepository extends AbstractRepository {

	@Query("select p from Peep p where p.id = :id")
	Peep findPeepById(int id);

	@Query("select p from Peep p")
	Collection<Peep> findAllPeeps();

	@Query("select u from UserAccount u where u.id = :id")
	UserAccount findUserById(int id);
}
