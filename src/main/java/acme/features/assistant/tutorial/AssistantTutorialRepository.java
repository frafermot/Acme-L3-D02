
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.session.Session;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AssistantTutorialRepository extends AbstractRepository {

	@Query("SELECT t FROM Tutorial t WHERE t.id = :id")
	Tutorial findTutorialById(int id);

	@Query("SELECT t FROM Tutorial t JOIN t.course c WHERE c.published = true")
	Collection<Tutorial> findAccessibleTutorials();

	@Query("SELECT u FROM UserAccount u WHERE u.id = :id")
	UserAccount findUserAccountById(int id);

	@Query("SELECT c FROM Course c")
	Collection<Course> findAllCourses();

	@Query("SELECT c FROM Course c WHERE c.id = :id")
	Course findCourseById(int id);

	@Query("SELECT COUNT(t) = 0 FROM Tutorial t WHERE t.code = :code")
	boolean existsTutorialByCode(String code);

	@Query("SELECT COUNT(t) = 1 FROM Tutorial t WHERE t.code = :code AND t.id = :id")
	boolean checksameTutorialByCode(String code, int id);

	@Query("SELECT a FROM Assistant a WHERE a.id = :id")
	Assistant findAssistantById(int id);

	@Query("SELECT s FROM Session s WHERE s.tutorial.id = :id")
	Collection<Session> findSessionsByTutorialId(int id);

	@Query("SELECT t FROM Tutorial t WHERE t.assistant.id = :id")
	Collection<Tutorial> findTutorialsByAssistantId(int id);

	@Query("SELECT COUNT(t) = 0 FROM Tutorial t JOIN t.course c WHERE c.id = :id")
	boolean checkEmptyCourseById(int id);

	@Query("SELECT COUNT(t) = 1 FROM Tutorial t JOIN t.course c WHERE c.id = :courseId AND t.id = :tutorialId")
	boolean checkSameCourseById(int courseId, int tutorialId);
}
