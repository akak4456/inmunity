package com.akak4456.domain.fileupload;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.akak4456.domain.member.MemberEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = "")
public class MemberFileUpload extends FileUpload {
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name="useremail")
	private MemberEntity member;
}
