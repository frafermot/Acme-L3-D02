
package acme.entities.bulletin;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.components.accounts.Administrator;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Bulletin extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Past
	@Column(name = "instantiation_moment", unique = false, nullable = false)
	private LocalDateTime		updateTime;

	@NotBlank
	@Length(max = 75)
	@Column(name = "title", unique = false)
	private String				title;

	@NotBlank
	@Length(max = 100)
	@Column(name = "message", unique = false)
	private String				message;

	@Column(name = "critical", unique = false)
	private boolean				critical;

	@URL
	@Column(name = "link", unique = false, nullable = true)
	private String				link;

	@ManyToOne
	@NotNull
	private Administrator		admin;

}
