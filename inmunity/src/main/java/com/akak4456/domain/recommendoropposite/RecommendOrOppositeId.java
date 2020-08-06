package com.akak4456.domain.recommendoropposite;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class RecommendOrOppositeId implements Serializable {
	private Long bno;
	
	private String useremail;
	
	public RecommendOrOppositeId () {
		
	}
	
	public RecommendOrOppositeId(Long bno, String useremail) {
		this.bno = bno;
		this.useremail = useremail;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof RecommendOrOppositeId)) return false;
		RecommendOrOppositeId that = (RecommendOrOppositeId) o;
		
		return Objects.equals(getBno(),that.getBno())&& Objects.equals(getUseremail(),that.getUseremail());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getUseremail(),getBno());
	}
}
