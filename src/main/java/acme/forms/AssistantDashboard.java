
package acme.forms;

import acme.Statistics;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssistantDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	Integer						numberOfTutorialTheorical;
	Integer						numberOfTutorialHandsOn;
	Statistics					sessionStatistics;
	Statistics					tutorialStatistics;
}
