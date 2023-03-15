
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Integer						numberOfTheoryTheorical;
	Integer						numberOfTheoryHandsOn;
	Double						averageWorkbook;
	Double						deviationWorkbook;
	Double						minWorkbook;
	Double						maxWorkbook;
	Double						averageTimeEnrolled;
	Double						deviationTimeEnrolled;
	Double						minTimeEnrolled;
	Double						maxTimeEnrolled;

}
