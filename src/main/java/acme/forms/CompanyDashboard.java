
package acme.forms;

import java.time.Month;
import java.util.Map;

import acme.framework.data.AbstractForm;

public class CompanyDashboard extends AbstractForm {

	protected static final long		serialVersionUID	= 1L;

	protected Map<Month, Integer>	numberOfPracticaTheorical;
	protected Map<Month, Integer>	numberOfPracticaHandsOn;

	protected Double				averageLengthSession;
	protected Double				deviationSession;
	protected Double				minSession;
	protected Double				maxSession;

	protected Double				averagePractica;
	protected Double				deviationPractica;
	protected Double				minPractica;
	protected Double				maxPractica;
}
