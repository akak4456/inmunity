package com.akak4456.domain.reply;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import com.akak4456.domain.board.Board;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="DTYPE")
@SequenceGenerator(name="reply_seq", initialValue=1, allocationSize=1)
public abstract class Reply<B extends Board> {
	@Transient
	private final char[] CHAR_SET = {
			'0','1','2','3','4','5','6','7','8','9',
			'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
			'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
	};
	//CHAR_SET의 길이는 62
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="reply_seq")
	private Long rno;
	
	private Long parent_rno;
	
	private String path;
	
	
	@NotNull
	@NotEmpty
	private String reply;
	
	/*
	 유저정보를 담는 것
	 replyer는 member의 username과 동일함
	 */
	@NotNull
	private String useremail;
	@NotNull
	private String replyer;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@CreationTimestamp
	private LocalDateTime regdate;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@UpdateTimestamp
	private LocalDateTime updatedate;
	
	@Column(columnDefinition = "char(1) default 'N'")
	private char isdelete = 'N';
	
	public void postPersist() {
		StringBuffer result1 = new StringBuffer();
		if(parent_rno != -1) {
			long no = this.parent_rno;
			while(no > 0) {
				int len = CHAR_SET.length;
				int remain = (int) (no%len);
				result1.append(CHAR_SET[remain]);
				no /= len;
			}
		}
		while(result1.length() < 6) {
			result1.append(CHAR_SET[0]);
		}
		result1.reverse();
		StringBuffer result2 = new StringBuffer();
		long no = this.rno;
		while(no > 0) {
			int len = CHAR_SET.length;
			int remain = (int) (no%len);
			result1.append(CHAR_SET[remain]);
			no /= len;
		}
		while(result2.length() < 6) {
			result2.append(CHAR_SET[0]);
		}
		result2.reverse();
		StringBuffer result = new StringBuffer();
		result.append(result1);
		result.append(result2);
		//최종적으로 path에는 (parent_rno변형결과가 들어가게 됨)(rno변형결과)
		this.setPath(result.toString());
	}
	
	public String generatePathFromRnoAndParentRno() {
		StringBuffer result = new StringBuffer();
		if(parent_rno != -1) {
			StringBuffer result1 = new StringBuffer();
			long no = this.parent_rno;
			while(no > 0) {
				int len = CHAR_SET.length;
				int remain = (int) (no%len);
				result1.append(CHAR_SET[remain]);
				no /= len;
			}
			while(result1.length() < 6) {
				result1.append(CHAR_SET[0]);
			}
			result1.reverse();
			result.append(result1);
		}
	
		StringBuffer result2 = new StringBuffer();
		long no = this.rno;
		while(no > 0) {
			int len = CHAR_SET.length;
			int remain = (int) (no%len);
			result2.append(CHAR_SET[remain]);
			no /= len;
		}
		while(result2.length() < 6) {
			result2.append(CHAR_SET[0]);
		}
		result2.reverse();
		result.append(result2);
		//최종적으로 path에는 (parent_rno변형결과가 들어가게 됨)(rno변형결과)
		return result.toString();
	}
	
	@Type(type="Board")
	@JsonIgnore
	@ManyToOne
	private B board;
}
