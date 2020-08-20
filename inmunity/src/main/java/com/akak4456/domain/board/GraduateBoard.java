package com.akak4456.domain.board;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.akak4456.domain.reply.GraduateReply;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "GB")
public class GraduateBoard extends Board {

}
