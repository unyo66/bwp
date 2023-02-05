package com.bwp.app.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "type"),
        @Index(columnList = "createdAt")
})
@Entity
@Getter
@ToString(callSuper = true) // 모든 필드에 toString, 다른데에 toString 까지 출력할 수 있게 callSuper
public class Article extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name="userId")
    private UserAccount userAccount;

    //itemId 에 item 에 없는 값을 넣을 수 없음(참조무결성).
    //TODO: 바인딩을 하되 itemId 에 없는 값을 넣을 수는 없나?
//    @Setter
//    @ManyToOne(optional = false)
//    @JoinColumn(name="itemId")
//    private Item item;

    @Setter
    @Column
    private Long itemId;
    @Setter
    @Column(nullable = false)
    private int type;
    @Setter
    @Column(nullable = false, length = 1000)
    private String title;

    @Setter
    @Column(nullable = false, length = 10000)
    private String content;

    // 메타데이터
//    @CreatedDate
//    @Column(nullable = false)
//    private LocalDateTime createdAt;
//
//    @LastModifiedDate
//    @Column
//    private LocalDateTime modifiedAt;


    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @ToString.Exclude
    private final Set<Comment> comments = new LinkedHashSet<>();

    /** Entity를 만들때는 무조건 기본 생성자가 필요함.
     * public / protected만 가능한데, 아무데서도 기본생성자를 안쓰이게 하고 싶어서 protected로 변경함
     * **/

    protected Article(){};

    private Article(UserAccount userAccount, Long itemId, int type, String title, String content) {
        this.userAccount = userAccount;
        this.itemId = itemId;
        this.type = type;
        this.title = title;
        this.content = content;
    }

    public static Article of(UserAccount userAccount, Long itemId, int type, String title, String content) {
        return new Article(userAccount, itemId, type, title, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
