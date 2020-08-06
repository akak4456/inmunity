package com.akak4456.domain.board;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.akak4456.domain.fileupload.FreeFileUpload;
import com.akak4456.domain.reply.FreeReply;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude= {"fileUpload","replies"})
@Entity
@Table(name="tbl_free_board")
@EqualsAndHashCode(of="bno")
@SequenceGenerator(name = "bno_sequence",allocationSize = 1, sequenceName = "FREEBOARD_SEQ")
public class FreeBoard extends Board<FreeFileUpload,FreeReply> {

}
