
package acme.entities.systemConfiguration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SystemConfiguration extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(name = "accepted_currencies", unique = false)
	public String				acceptedCurrencies;

	@NotBlank
	@Column(name = "system_currency", unique = false)
	public String				systemCurrency;

}
