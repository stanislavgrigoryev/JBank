package com.jbank.cardservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 19)
    private String maskedNumber;

    @Column(nullable = false, unique = true)
    private String encryptedNumber;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardType cardType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentSystem paymentSystem;

    @Enumerated(EnumType.STRING)
    private CardStatus cardStatus;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private BigDecimal dailyLimit;

    @Column(nullable = false)
    private BigDecimal monthlyLimit;


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Card card = (Card) o;
        return getId() != null && Objects.equals(getId(), card.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }


//    · Маскированный номер (реальный номер хранится в HSMsimulator/шифровании)
//    · Срок действия (месяц/год)
//    · CVV2 (только при эмиссии, хранится зашифрованно)
//    · Тип карты (VIRTUAL, PHYSICAL)
//    · Платежная система (MIR, VISA, MasterCard)
//    · Статус (ВЫПУЩЕНА, АКТИВНА, ЗАБЛОКИРОВАНА, ЗАКРЫТА)
//    · Привязка к счету
//    · Дневной/месячный лимит операций
//    ● Создайте таблицу карт
//    ● Реализуйте эмиссию карты (генерация номера через Luhn, CVV)
//    ● Эндпоинты для блокировки, перевыпуска, смены лимитов
//    ● Добавьте PIN-менеджмент (проверка/смена через отдельный сервис)
}
