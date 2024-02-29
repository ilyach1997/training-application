package com.example.training.model.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "\"order\"")
@Getter@Setter
@EqualsAndHashCode
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Account account;

    @OneToMany(mappedBy = "order")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<OrderItem> items;
    private LocalDate date;
    private BigDecimal totalCost;
}
