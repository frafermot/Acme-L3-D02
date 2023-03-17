
package acme.forms;

import java.util.Map;

import acme.Statistics;
import acme.enums.Indication;
import acme.framework.data.AbstractForm;

public class LecturerDashboard extends AbstractForm {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	Map<Indication, Integer>	totalLecturesNumberByIndication;
	Statistics					lectureStatistics;
	Statistics					courseStatistics;
}
