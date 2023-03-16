
package acme.forms;

import java.util.Map;

import acme.enums.Indication;
import acme.framework.data.AbstractForm;

public class AuditorDashboard extends AbstractForm {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	Map<Indication, Integer>	numberAuditsByIndication;
	Double						averageRecordAudits;
	Double						deviationRecordAudits;
	Integer						minRecordAudits;
	Integer						maxRecordAudits;

	Double						averagePeriodAuditingRecord;
	Double						deviationPeriodAuditingRecord;
	Integer						minPeriodAuditingRecord;
	Integer						maxPeriodAuditingRecord;

}
