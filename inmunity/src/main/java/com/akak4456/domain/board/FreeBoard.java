package com.akak4456.domain.board;

import javax.persistence.DiscriminatorValue;
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
@Entity
@DiscriminatorValue(value = "")
public class FreeBoard extends Board<FreeFileUpload,FreeReply> {

}
