package com.app.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "accounts",
        uniqueConstraints = {
            @UniqueConstraint(name = "UC_account_name", columnNames = "number")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = "customer")
public class Account extends AbstractEntity {
    @Column(name = "number", unique = true, nullable = false, updatable = false)
    private String number;
    @Column(name= "currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Column(name = "balance")
    private Double balance;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name ="customer_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false)
    private Customer customer;

    public Account(Currency currency, Customer customer){
        this.currency = currency;
        this.customer = customer;
        this.balance = 0.0;
        this.number = UUID.randomUUID().toString();
    }
}
