
package acme.forms;

import java.util.Map;

import acme.Statistics;
import acme.enums.currency;
import acme.framework.data.AbstractForm;

public class AdministratorDashboard extends AbstractForm {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	Map<String, Integer>		totalPrincipalNumberPerRole;
	Double						peepsWithAddresAndLink;
	Double						ratioCriticalBulletin;
	Double						ratioNonCriticalBulletin;
	Map<currency, Double>		averageBudgetByCurrency;
	Map<currency, Integer>		minimumBudgetByCurrency;
	Map<currency, Integer>		maximumBudgetByCurrency;
	Map<currency, Double>		standartDeviationBudgetByCurrency;
	Statistics					statistics;
}
