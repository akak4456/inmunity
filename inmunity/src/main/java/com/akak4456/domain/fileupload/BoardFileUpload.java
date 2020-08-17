package com.akak4456.domain.fileupload;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.akak4456.domain.board.Board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "")
public class BoardFileUpload extends FileUpload {
	
}
