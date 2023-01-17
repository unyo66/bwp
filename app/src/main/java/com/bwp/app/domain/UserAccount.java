package com.bwp.app.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "email", unique = true),
        @Index(columnList = "nickname", unique = true),
        @Index(columnList = "createdAt")
})
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Setter
    @Column(nullable = false)
    private String pw;

    @Setter
    @Column(nullable = false)
    private String nickname;

    @Setter
    @Column(nullable = false)
    private String address;

    @Setter
    @Column(nullable = false)
    private String phone;

    @Setter
    @Column
    private String notice;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @CreatedDate
    @Column
    private LocalDateTime modifiedAt;


    protected UserAccount(){}

    private UserAccount(String email, String pw, String nickname, String address, String phone, String notice){
        this.email = email;
        this.pw = pw;
        this.nickname = nickname;
        this.address = address;
        this.phone = phone;
        this.notice = notice;
    }

    public static UserAccount of(String email, String pw, String nickname, String address, String phone, String notice) {
        return new UserAccount(email, pw, nickname, address, phone, notice);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
