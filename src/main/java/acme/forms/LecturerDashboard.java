
package acme.forms;

import java.util.Map;

import acme.enums.Indication;
import acme.framework.data.AbstractForm;

public class LecturerDashboard extends AbstractForm {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	Map<Indication, Integer>	totalLecturesNumberByIndication;
	Double						averageLectureLearningTime;
	Double						deviationLectureLearningTime;
	Integer						minimumLectureLearningTime;
	Integer						maximumLectureLearningTime;

	Double						averageCourseLearningTime;
	Double						deviationCourseLearningTime;
	Integer						minimumCourseLearningTime;
	Integer						maximumCourseLearningTime;

}
