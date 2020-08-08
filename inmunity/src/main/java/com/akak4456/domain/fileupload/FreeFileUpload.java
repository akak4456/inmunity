package com.akak4456.domain.fileupload;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = "")
public class FreeFileUpload extends FileUpload {

}
