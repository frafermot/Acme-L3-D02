
package acme;

import acme.framework.data.AbstractObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Statistics extends AbstractObject {

	private static final long	serialVersionUID	= 1L;

	protected Double			average;

	protected Double			deviation;

	protected Double			max;

	protected Double			min;
}
