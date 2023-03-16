
package acme.forms;

import java.util.Map;

import acme.enums.currency;
import acme.framework.data.AbstractForm;

public class AdministratorDashboard extends AbstractForm {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	Map<String, Integer>		totalPrincipalNumberPerRol;

	Double						peepsWithAddresAndLink;
	Double						ratioCriticalBulletin;
	Double						ratioNonCriticalBulletin;

	Map<currency, Double>		averageBudgetByCurrency;
	Map<currency, Integer>		minimumBudgetByCurrency;
	Map<currency, Integer>		maximumBudgetByCurrency;
	Map<currency, Double>		standartDeviationBudgetByCurrency;

	Double						averageNotesPostedLast10Weeks;
	Integer						minimumNotesPostedLast10Weeks;
	Integer						maximumNotesPostedLast10Weeks;
	Double						standartDeviationNotesPostedLast10Weeks;

}
