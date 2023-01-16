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
        @Index(columnList = "name"),
        @Index(columnList = "price"),
        @Index(columnList = "roastingPoint"),
        @Index(columnList = "origin"),
        @Index(columnList = "memo")
})
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name="companyId")
    private Company company;

    @Setter
    @Column(nullable = false)
    private Long price;

    @Setter
    @Column(nullable = false)
    private String roastingPoint;

    @Setter
    @Column(nullable = false)
    private String origin;

    @Setter
    @Column(nullable = false)
    private String memo;

    @Setter
    @Column(nullable = false)
    private String thumbnailImg;

    @Setter
    @Column(nullable = false)
    private String infoImg;

    @Setter
    @Column(nullable = false)
    private Boolean stock;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;


    protected Item(){};

    private Item(String name, Company company, Long price, String roastingPoint, String origin, String memo, String thumbnailImg, String infoImg, Boolean stock) {
        this.name = name;
        this.company = company;
        this.price = price;
        this.roastingPoint = roastingPoint;
        this.origin = origin;
        this.memo = memo;
        this.thumbnailImg = thumbnailImg;
        this.infoImg = infoImg;
        this.stock = stock;
    }

    public Item of(String name, Company company, Long price, String roastingPoint, String origin, String memo, String thumbnailImg, String infoImg, Boolean stock) {
        return new Item(name, company, price, roastingPoint, origin, memo, thumbnailImg, infoImg, stock);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id.equals(item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
