
package acme.entities.course;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import acme.entities.lecture.Lecture;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LectureCourse extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@NotNull
	@Valid
	@ManyToOne
	protected Course			course;

	@NotNull
	@Valid
	@ManyToOne
	protected Lecture			lecture;

}
