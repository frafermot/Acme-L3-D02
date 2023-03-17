
package acme.forms;

import acme.Statistics;
import acme.framework.data.AbstractForm;

public class StudentDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Integer						numberOfTheoryTheorical;
	Integer						numberOfTheoryHandsOn;
	Statistics					activityStatistics;
	Statistics					enrolmentStatistics;

}
