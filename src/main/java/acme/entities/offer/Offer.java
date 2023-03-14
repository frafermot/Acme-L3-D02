
package acme.entities.offer;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Offer extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	@PastOrPresent
	protected Date				instantiation;

	@NotBlank
	@Length(max = 75)
	protected String			header;

	@NotBlank
	@Length(max = 100)
	protected String			summary;

	@NotNull
	protected Date				startDate;

	@NotNull
	protected Date				endDate;

	@NotNull
	protected Money				price;

	@URL
	protected String			link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
