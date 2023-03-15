
package acme.forms;

import java.util.List;

import acme.framework.data.AbstractForm;

public class AssistantDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	protected List<Integer>		totalNumberOfTutorials;

	protected Double			averageSession;
	protected Double			deviationSession;
	protected Double			minimumSession;
	protected Double			maximumSession;

	protected Double			averageTutorial;
	protected Double			deviationTutorial;
	protected Double			minimumTutorial;
	protected Double			maximumTutorial;
}
