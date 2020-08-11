package com.akak4456.domain.scrap;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.akak4456.domain.board.Board;
import com.akak4456.domain.member.MemberEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="tbl_scrap",uniqueConstraints = @UniqueConstraint(columnNames = { "bno", "member" }))
@SequenceGenerator(name="scrap_seq", initialValue=1, allocationSize=1)
@EqualsAndHashCode(of="sno")
public class Scrap {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="scrap_seq")
	private Long sno;
	
	
	@OneToOne
	@JoinColumn(name="bno")
	private Board board;
	
	@OneToOne
	@JoinColumn(name="member")
	private MemberEntity member;
}
