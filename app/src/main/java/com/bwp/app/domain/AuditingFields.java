package com.bwp.app.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/*  할일 : Article, Comment 의 중복필드 합치기
    1) Article 의 메타데이터들(auditing 관련 필드) 가져오기
    2) 클래스에 @MappedSuperclass 달아주기
    3) auditing 관련된 것들 다 가져오기
    4)
*
* */

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@ToString
public class AuditingFields {
    //메타데이터
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column
    private LocalDateTime modifiedAt;
}
