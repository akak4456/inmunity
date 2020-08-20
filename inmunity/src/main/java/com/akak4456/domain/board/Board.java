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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.akak4456.domain.fileupload.BoardFileUpload;
import com.akak4456.domain.member.MemberEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DTYPE")
@SequenceGenerator(name="board_seq", initialValue=1, allocationSize=1)
@EqualsAndHashCode(of="bno")
public abstract class Board {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="board_seq")
	private Long bno;
	
	@NotNull
	@NotEmpty
	private String title;
	@NotNull
	@NotEmpty
	@Column(columnDefinition = "CLOB")
	private String content;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@CreationTimestamp
	private LocalDateTime regdate;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@UpdateTimestamp
	private LocalDateTime updatedate;
	
	@Builder.Default
	private Long viewcnt = 0L;
	@Builder.Default
	private Long recommendcnt = 0L;
	@Builder.Default
	private Long oppositecnt = 0L;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="useremail")
	private MemberEntity member;
	
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true,fetch = FetchType.LAZY)
	@JoinColumn(name="bno")
	@JsonIgnore
	private List<BoardFileUpload> fileUpload;
	
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setViewcnt(Long viewcnt) {
		this.viewcnt = viewcnt;
	}
	public void setRecommendcnt(Long recommendcnt) {
		this.recommendcnt = recommendcnt;
	}
	public void setOppositecnt(Long oppositecnt) {
		this.oppositecnt = oppositecnt;
	}
	@JsonIgnore
	public List<BoardFileUpload> getFileUpload(){
		return this.fileUpload;
	}
	
	@JsonProperty
	public void setFileUpload(List<BoardFileUpload> fileUpload) {
		this.fileUpload = fileUpload;
	}
}