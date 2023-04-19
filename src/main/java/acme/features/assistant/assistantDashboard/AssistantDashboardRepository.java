/*
 * AdministratorDashboardRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.assistant.assistantDashboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantDashboardRepository extends AbstractRepository {

	@Query("SELECT COUNT(t) FROM Tutorial t JOIN t.course c WHERE t.assistant.id = :userId AND c.indicator = acme.enums.Indication.THEORETICAL")
	Integer numberOfTutorialTheorical(Integer userId);

	@Query("SELECT COUNT(t) FROM Tutorial t JOIN t.course c WHERE t.assistant.id = :userId AND c.indicator = acme.enums.Indication.HANDS_ON")
	Integer numberOfTutorialHandsOn(Integer userId);

	@Query("SELECT COUNT(t) FROM Tutorial t JOIN t.course c WHERE t.assistant.id = :userId AND c.indicator = acme.enums.Indication.BALANCED")
	Integer numberOfTutorialBalanced(Integer userId);

	@Query("SELECT COUNT(t) FROM Tutorial t WHERE t.assistant.id = :userId")
	Double numberTutorials(Integer userId);

	@Query("SELECT AVG(t.estimatedTime) FROM Tutorial t WHERE t.assistant.id = :userId")
	Double tutorialAverage(Integer userId);

	@Query("SELECT t.estimatedTime FROM Tutorial t WHERE t.assistant.id = :userId")
	List<Integer> getTutorialEstimatedTimes(Integer userId);

	@Query("SELECT MAX(t.estimatedTime) FROM Tutorial t WHERE t.assistant.id = :userId")
	Double tutorialMax(Integer userId);

	@Query("SELECT MIN(t.estimatedTime) FROM Tutorial t WHERE t.assistant.id = :userId")
	Double tutorialMin(Integer userId);

	@Query("SELECT AVG((UNIX_TIMESTAMP(s.periodEnd) - UNIX_TIMESTAMP(s.periodStart))/60) FROM Session s WHERE s.tutorial.assistant.id = :userId")
	Double sessionAverage(Integer userId);

	@Query("SELECT (UNIX_TIMESTAMP(s.periodEnd) - UNIX_TIMESTAMP(s.periodStart))/60 FROM Session s WHERE s.tutorial.assistant.id = :userId")
	List<Integer> getSessionDurations(Integer userId);

	@Query("SELECT MAX((UNIX_TIMESTAMP(s.periodEnd) - UNIX_TIMESTAMP(s.periodStart))/60) FROM Session s WHERE s.tutorial.assistant.id = :userId")
	Double sessionMax(Integer userId);

	@Query("SELECT MIN((UNIX_TIMESTAMP(s.periodEnd) - UNIX_TIMESTAMP(s.periodStart))/60) FROM Session s WHERE s.tutorial.assistant.id = :userId")
	Double sessionMin(Integer userId);

	@Query("SELECT COUNT(s) FROM Session s WHERE s.tutorial.assistant.id = :userId")
	Double numberSessions(Integer userId);

	@Query("SELECT COUNT(s) FROM Session s WHERE s.tutorial.assistant.id = :userId AND s.indication = acme.enums.Indication.THEORETICAL")
	Integer getNumberOfSessionTheorical(Integer userId);

	@Query("SELECT COUNT(s) FROM Session s WHERE s.tutorial.assistant.id = :userId AND s.indication = acme.enums.Indication.HANDS_ON")
	Integer getNumberOfSessionHandsOn(Integer userId);

	@Query("SELECT COUNT(s) FROM Session s WHERE s.tutorial.assistant.id = :userId AND s.indication = acme.enums.Indication.BALANCED")
	Integer numberOfSessionBalanced(Integer userId);
}
