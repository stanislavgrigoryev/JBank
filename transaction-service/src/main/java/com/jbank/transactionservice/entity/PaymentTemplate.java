package com.jbank.transactionservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PaymentTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namePayment;

    //● Добавьте поддержку платежей по реквизитам (расчетный счет, БИК, ИНН)

    private LocalDate nextDebitDate;

    @Column(nullable = false)
    private String cardAccount;

    //Опишите сущность «Шаблон платежа». Атрибуты:
    //
    //· Название (например, "Квартплата")
    //· Реквизиты получателя
    //· Сумма и периодичность (ЕЖЕДНЕВНО, ЕЖЕНЕДЕЛЬНО, ЕЖЕМЕСЯЧНО)
    //· Дата следующего списания
    //· Счет/карта списания
    //● Реализуйте обработчик cron-подобных задач через Scheduler + Kafka
    //● Добавьте поддержку платежей по реквизитам (расчетный счет, БИК, ИНН)
    //● Реализуйте очередь платежей с приоритетами (сначала высокая комиссия -> выше приоритет)
    //● Эндпоинт для отмены/приостановки автоплатежа
}
