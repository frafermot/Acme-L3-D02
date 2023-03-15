
package acme.entities.course;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.enums.Indication;
import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import acme.roles.Lecturer;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Course extends AbstractEntity {

	@Pattern(regexp = "“[A-Z]{1,3} [0-9]{3}")
	@NotBlank
	@Column(unique = true)
	protected String		code;

	@NotBlank
	@Length(max = 75)
	protected String		title;

	@NotBlank
	@Length(max = 100)
	protected String		courseAbstract;

	@NotNull
	protected Indication	indicator;

	@PositiveOrZero
	@NotNull
	protected Money			retailPrice;

	@URL
	protected String		link;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Lecturer		lecturer;
}
