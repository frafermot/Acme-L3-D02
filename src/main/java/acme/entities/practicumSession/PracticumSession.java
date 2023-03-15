
package acme.entities.practicumSession;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.practicum.Practicum;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PracticumSession extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			sessionAbstract;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				periodStart;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				periodEnd;

	@URL
	protected String			link;

	@ManyToOne(optional = false)
	protected Practicum			practicum;
}
