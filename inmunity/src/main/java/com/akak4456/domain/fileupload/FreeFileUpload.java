package com.akak4456.domain.fileupload;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
//@ToString(exclude= "replies")
@Entity
@Table(name="tbl_free_fileupload")
@EqualsAndHashCode(of="fno")
@SequenceGenerator(name = "fno_sequence",allocationSize = 1, sequenceName = "FREEFILEUPLOAD_SEQ")
public class FreeFileUpload extends FileUpload {

}
