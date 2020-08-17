package com.akak4456.domain.recommendoropposite;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.validation.constraints.NotNull;

import com.akak4456.domain.board.Board;
import com.akak4456.domain.member.MemberEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
public class RecommendOrOpposite {
	@EmbeddedId
	private RecommendOrOppositeId id;
	@MapsId("board_id")
	@ManyToOne
	@JoinColumn(name="bno")
	private Board board;
	@MapsId("member_id")
	@ManyToOne
	@JoinColumn(name="useremail")
	private MemberEntity member;
	@NotNull
	private RecommendOrOppositeEnum recommendOrOpposite;
}
