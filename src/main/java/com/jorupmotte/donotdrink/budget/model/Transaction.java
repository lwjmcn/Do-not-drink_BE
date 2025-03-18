package com.jorupmotte.donotdrink.budget.model;

import com.jorupmotte.donotdrink.user.model.User;
import com.jorupmotte.donotdrink.common.type.ExpenseType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction", schema = "do-not-drink")
@Getter
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "expense_type", nullable = false)
    private ExpenseType expenseType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private ExpenseCategory category;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "description", nullable = false, length = 225)
    private String description;
}