
package acme.entities.auditingRecords;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.audit.Audit;
import acme.enums.Mark;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditingRecords extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 75)
	protected String			subject;

	@NotBlank
	@Length(max = 100)
	protected String			assessment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				periodStart;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				periodEnd;

	protected Mark				mark;

	@URL
	protected String			link;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Audit				audit;
}
