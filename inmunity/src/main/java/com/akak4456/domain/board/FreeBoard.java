package com.akak4456.domain.board;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.akak4456.domain.fileupload.BoardFileUpload;
import com.akak4456.domain.member.MemberEntity;
import com.akak4456.domain.recommendoropposite.RecommendOrOpposite;
import com.akak4456.domain.reply.FreeReply;
import com.akak4456.domain.reply.Reply;
import com.akak4456.domain.scrap.Scrap;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "FB")
public class FreeBoard extends Board {
}
