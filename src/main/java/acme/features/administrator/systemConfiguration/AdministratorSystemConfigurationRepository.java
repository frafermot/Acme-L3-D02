
package acme.features.administrator.systemConfiguration;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.systemConfiguration.SystemConfiguration;
import acme.framework.components.accounts.Administrator;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorSystemConfigurationRepository extends AbstractRepository {

	@Query("select a from Administrator a where a.id = :administratorId")
	Administrator findOneAdministratorById(int administratorId);

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findActualSystemConfiguration();

}
