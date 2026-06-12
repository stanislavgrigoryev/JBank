package com.jbank.accountservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "accounts", indexes = {
        @Index(name = "idx_account_user_id", columnList = "userId"),
        @Index(name = "idx_account_number", columnList = "accountNumber")
})
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    private AccountType typeAccount;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(nullable = false, unique = true, length = 34)
    private String accountNumber;

    @Column(nullable = false)
    private BigDecimal availableBalance;

    @Column(nullable = false)
    private BigDecimal blockedBalance;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @Column(precision = 5, scale = 2)
    private BigDecimal interestRate;

    @Column(precision = 19, scale = 2)
    private BigDecimal creditLimit;

    private LocalDate dateOpenAccount;

    private LocalDate dateCloseAccount;


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Account account = (Account) o;
        return getAccountId() != null && Objects.equals(getAccountId(), account.getAccountId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }



    //· Номер счета (генерация по алгоритму IBAN/BIC)
    //· Тип счета (ДЕБЕТОВЫЙ, КРЕДИТНЫЙ, НАКОПИТЕЛЬНЫЙ, ВАЛЮТНЫЙ)
    //· Валюта (RUB, USD, EUR, CNY)
    //· Текущий баланс (cash + заблокированные средства)
    //· Статус (АКТИВЕН, ЗАБЛОКИРОВАН, ЗАКРЫТ)
    //· Процентная ставка (для накопительных)
    //· Кредитный лимит (для кредитных счетов)
    //· Дата открытия, дата закрытия
    //● Создайте таблицу счетов с индексами по номеру и пользователю
    //● Реализуйте CRUD операций с проверкой прав (клиент видит только свои счета)
    //● Добавьте метод для блокировки/разблокировки счета оператором
    //● Реализуйте начисление процентов на остаток (ежедневно/ежемесячно)
}
