package com.akak4456.domain.scrap;

import java.time.LocalDateTime;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.hibernate.annotations.CreationTimestamp;

import com.akak4456.domain.board.Board;
import com.akak4456.domain.member.MemberEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
public class Scrap {
	@EmbeddedId
	private ScrapId id;
	@MapsId("board_id")
	@ManyToOne
	@JoinColumn(name="bno")
	private Board board;
	@MapsId("member_id")
	@ManyToOne
	@JoinColumn(name="useremail")
	private MemberEntity member;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@CreationTimestamp
	private LocalDateTime regdate;
}
