package com.bwp.app.domain;

import com.bwp.app.domain.Article;
import com.bwp.app.domain.UserAccount;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt")
})
@Entity
@Getter
@ToString(callSuper = true)
public class Comment extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name="articleId")
    private Article article;

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name="userId")
    private UserAccount userAccount;


    @Setter
    @Column
    private Long parentId;

    @Setter
    @Column
    private int commentOrder;

    @Setter
    @Column
    private int cDepth;

    @Setter
    @Column(nullable = false)
    private String content;


//    @CreatedDate
//    @Column(nullable = false)
//    private LocalDateTime createdAt;
//    @LastModifiedDate
//    @Column
//    private LocalDateTime modifiedAt;

    protected Comment() {}

    private Comment(UserAccount userAccount, Article article, Long parentId, int commentOrder, int cDepth, String content) {
        this.userAccount = userAccount;
        this.article = article;
        this.parentId = parentId;
        this.content = content;
        this.commentOrder = commentOrder;
        this.cDepth = cDepth;
    }

    public static Comment of(UserAccount userAccount, Article article, Long parentId, int commentOrder, int cDepth, String content) {
        return new Comment(userAccount, article, parentId, commentOrder, cDepth, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id.equals(comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}