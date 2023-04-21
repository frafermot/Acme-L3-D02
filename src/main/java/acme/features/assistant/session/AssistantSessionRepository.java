
package acme.features.assistant.session;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.session.Session;
import acme.entities.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantSessionRepository extends AbstractRepository {

	@Query("SELECT s FROM Session s WHERE s.id = :id")
	Session findSessionById(int id);

	@Query("SELECT t FROM Tutorial t WHERE t.id = :id")
	Tutorial findTutorialById(int id);

	@Query("SELECT s FROM Session s JOIN s.tutorial t WHERE t.id = :id AND t.published = true")
	Collection<Session> findSessionsByTutorialId(int id);

	@Query("SELECT t FROM Tutorial t WHERE t.assistant.id = :id")
	Collection<Tutorial> findTutorialsByAssistantId(int id);
}
