
package acme.forms;

import java.time.Month;
import java.util.Map;

import acme.Statistics;
import acme.framework.data.AbstractForm;

public class CompanyDashboard extends AbstractForm {

	protected static final long		serialVersionUID	= 1L;

	protected Map<Month, Integer>	numberOfPracticaTheorical;
	protected Map<Month, Integer>	numberOfPracticaHandsOn;
	protected Statistics			sessionStatistics;
	protected Statistics			practicaStatistics;
}
