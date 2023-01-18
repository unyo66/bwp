package com.bwp.app.domain;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@ToString
public class Company {
    @Id
    @Column
    private Long id;

    @Column
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name="adminId")
    private UserAccount userAccount;

    protected Company() {}

    private Company(String name, UserAccount userAccount) {
        this.name = name;
        this.userAccount = userAccount;
    }

    public static Company of(String name, UserAccount userAccount) {
        return new Company(name, userAccount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return id.equals(company.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
