
package acme.entities.lecture;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.course.Course;
import acme.enums.Indication;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lecture extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			lectureAbstract;

	@Positive
	protected int				estimatedTime;

	@NotBlank
	@Length(max = 100)
	protected String			body;

	protected Indication		indicator;

	@URL
	protected String			link;

	protected boolean			published;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Course			course;
}
