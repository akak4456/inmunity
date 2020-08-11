package com.akak4456.domain.board;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import com.akak4456.domain.fileupload.FileUpload;
import com.akak4456.domain.member.MemberEntity;
import com.akak4456.domain.reply.Reply;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="DTYPE")
@SequenceGenerator(name="board_seq", initialValue=1, allocationSize=1)
@EqualsAndHashCode(of="bno")
public abstract class Board <F extends FileUpload,R extends Reply> {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="board_seq")
	private Long bno;
	
	private String title;
	@Column(columnDefinition = "CLOB")
	private String content;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@CreationTimestamp
	private LocalDateTime regdate;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@UpdateTimestamp
	private LocalDateTime updatedate;
	
	@Type(type = "FileUpload")
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true,fetch = FetchType.LAZY)
	@JoinColumn(name="bno")
	@JsonIgnore
	private List<F> fileUpload;
	
	@JsonIgnore
	public List<F> getFileUpload(){
		return fileUpload;
	}
	
	@JsonProperty
	public void setFileUpload(List<F> fileUpload) {
		this.fileUpload = fileUpload;
	}
	
	@Type(type = "Reply")
	@JsonIgnore
	@OneToMany(mappedBy="board",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	private List<R> replies;
	
	/*
	 useremail과 username은 board와 member 사이의 mapping을 위해서 존재함
	 useremail은 변경불가하고, 유일하므로 식별성을 가지고 있다.
	 그러니 누군가가 글을 수정 삭제하고자 할 때 이를 이용하면 충분히 식별이 될 것이다.
	 username은 작성자를 보일 때 쓰이는 것이다.
	 */
	@NotNull
	private String useremail;
	
	@NotNull
	private String username;
	
	private Long viewcnt = 0L;
	
	private Long recommendcnt = 0L;
	
	private Long oppositecnt = 0L;
	
}