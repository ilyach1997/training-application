package com.example.training.model.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "account_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Account account;

    @OneToMany(mappedBy = "shoppingCart")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<ShoppingCartItem> items;

    public ShoppingCart(Account account) {
        this.account = account;
    }

    public BigDecimal getTotalCost() {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (ShoppingCartItem item : this.items) {
            totalCost = totalCost.add(item.getProduct().getCost().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return totalCost;
    }
}
