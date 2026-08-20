package com.jbank.creditservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "credit_contracts")
public class CreditContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amountCredit;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal percentBet;

    @Column(nullable = false)
    private Integer termMonths;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatusType paymentStatusType;

    @Column(nullable = false)
    private BigDecimal remainsDebt;


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CreditContract creditContract = (CreditContract) o;
        return getId() != null && Objects.equals(getId(), creditContract.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
    //● Опишите сущность «Кредитный договор». Атрибуты:
    //
    //· Сумма кредита
    //· Процентная ставка
    //· Срок в месяцах
    //· Тип платежа (АННУИТЕТНЫЙ, ДИФФЕРЕНЦИРОВАННЫЙ)
    //· Дата выдачи
    //· Статус (ОДОБРЕН, ВЫДАН, НА РЕСТРУКТУРИЗАЦИИ, ПРОСРОЧЕН, ЗАКРЫТ)
    //· Остаток основного долга
    //● Опишите сущность «График платежей» с датами и суммами (основной долг + проценты)
    //● Реализуйте автоматическое списание ежемесячных платежей через Kafka
    //● Добавьте расчет штрафов и пеней при просрочке
    //● Эндпоинт для досрочного погашения с пересчетом графика
}
