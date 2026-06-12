package com.jbank.transactionservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String externalId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private EnumType currency;

    @Column(nullable = false)
    private String senderIdentifier;

    @Column(nullable = false)
    private String receiverIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus transactionStatus;

    @Column(nullable = false)
    private LocalDateTime dateOfEvent;

    private Integer mccCode;

    private BigDecimal commission;

    private String reasonForRefusal;


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Transaction transaction = (Transaction) o;
        return getId() != null && Objects.equals(getId(), transaction.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }



    //● Опишите сущность «Транзакция». Атрибуты:
    //· Уникальный идентификатор (внешний и внутренний)
    //· Тип (ОПЛАТА, ПЕРЕВОД, ЗАЧИСЛЕНИЕ, СПИСАНИЕ, КОМИССИЯ, ВОЗВРАТ)
    //· Сумма и валюта
    //· Карта/счет отправителя и получателя (внутренний номер или внешний IBAN/карта)
    //· Статус (ИНИЦИИРОВАНА, ВЫПОЛНЕНА, ОТМЕНЕНА, ОШИБКА)
    //· Дата проведения
    //· MNC/MCC код (для категоризации)
    //· Комиссия
    //· Причина отказа (если есть)
    //● Создайте таблицы транзакций с партиционированием по году/месяцу
    //● Реализуйте переводы:
    //· Между своими счетами
    //· На карту другого клиента банка
    //· На внешнюю карту через СБП (через мок-интеграцию)
    //● Добавьте обработку возвратов (reversal)
    //● Обеспечьте атомарность через @Transactional и проверку достаточности средств
}
