package com.jorupmotte.donotdrink.budget.model;

import com.jorupmotte.donotdrink.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "expense_category")
@Getter
@NoArgsConstructor
public class ExpenseCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "is_deleted", nullable = false)
    private Byte isDeleted;

}