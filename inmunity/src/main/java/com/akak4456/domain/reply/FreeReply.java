package com.akak4456.domain.reply;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.akak4456.domain.board.FreeBoard;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude="board")
@Entity
@Table(name="tbl_free_reply")
@EqualsAndHashCode(of="rno")
@SequenceGenerator(name = "rno_sequence",allocationSize = 1, sequenceName = "FREEREPLY_SEQ")
@DynamicUpdate
public class FreeReply extends Reply<FreeBoard> {
	/*
	 * FreeReply는 FreeBoard와 다르게 @DynamicUpdate를 필요로 함
	 * 이것이 없으면 ReplyService에서 addReply를 할 때 문제가 발생 가능
	 * addReply에서 path를 업데이트하는 부분이 있는데 DynamicUpdate를 안 해 놓으면 path를 update하면서 RegDate가 null이 되어버림
	 */
}
