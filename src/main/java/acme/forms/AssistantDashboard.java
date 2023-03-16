
package acme.forms;

import acme.framework.data.AbstractForm;

public class AssistantDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	protected Integer			numberOfTutorialTheorical;
	protected Integer			numberOfTutorialHandsOn;

	protected Double			averageSession;
	protected Double			deviationSession;
	protected Double			minSession;
	protected Double			maxSession;

	protected Double			averageTutorial;
	protected Double			deviationTutorial;
	protected Double			minTutorial;
	protected Double			maxTutorial;
}
