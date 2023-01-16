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
public class ItemOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name="itemId")
    private Item item;

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name="userId")
    private UserAccount userAccount;

    @Setter
    @Column(nullable = false)
    private Long itemCount;

    @Setter
    @Column(nullable = false)
    private int orderStep;

    @Setter
    @Column(nullable = false)
    private int optionGrinding;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected ItemOrder() {}

    private ItemOrder(Item item, UserAccount userAccount, Long itemCount, int orderStep, int optionGrinding) {
        this.item = item;
        this.userAccount = userAccount;
        this.itemCount = itemCount;
        this.orderStep = orderStep;
        this.optionGrinding = optionGrinding;
    }

    public ItemOrder of(Item item, UserAccount userAccount, Long itemCount, int orderStep, int optionGrinding) {
        return new ItemOrder(item, userAccount, itemCount, orderStep, optionGrinding);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemOrder itemOrder = (ItemOrder) o;
        return id.equals(itemOrder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
