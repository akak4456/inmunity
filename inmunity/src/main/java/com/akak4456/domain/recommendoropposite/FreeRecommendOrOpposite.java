package com.akak4456.domain.recommendoropposite;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@DiscriminatorValue(value = "")
public class FreeRecommendOrOpposite extends RecommendOrOppositeEntity {

}
