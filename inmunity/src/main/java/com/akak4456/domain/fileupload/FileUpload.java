package com.akak4456.domain.fileupload;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.akak4456.domain.board.Board;
import com.akak4456.domain.member.MemberEntity;
import com.akak4456.domain.recommendoropposite.RecommendOrOpposite;
import com.akak4456.domain.reply.Reply;
import com.akak4456.domain.scrap.Scrap;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="DTYPE")
@SequenceGenerator(name="fileupload_seq", initialValue=1, allocationSize=1)
@EqualsAndHashCode(of="bno")
public abstract class FileUpload {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="fileupload_seq")
	private Long fno;
	
	private String uploadPath;
	
	private String uploadFileName;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@CreationTimestamp
	private LocalDateTime regdate;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@UpdateTimestamp
	private LocalDateTime updatedate;
}
