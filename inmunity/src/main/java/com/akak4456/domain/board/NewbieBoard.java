package com.akak4456.domain.board;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "NBB")
public class NewbieBoard extends Board {

}
