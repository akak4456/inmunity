package com.akak4456.domain.recommendoropposite;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DTYPE")
public abstract class RecommendOrOppositeEntity {
	
	@EmbeddedId
	private RecommendOrOppositeId id;
	@NotNull
	private RecommendOrOpposite recommendOrOpposite;
}
