package com.app.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;
import javax.annotation.processing.Generated;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@NamedQueries({
        @NamedQuery(name = "accounts.findAll",
                    query = "select a from Account a"),
        @NamedQuery(name = "accounts.findByNumber",
                    query = "select a from Account a where number=:number"),
})
public class Account extends AbstractEntity implements Serializable {
    @Column(name = "number", unique = true, nullable = false, updatable = false)
    private String number;
    @Column(name= "currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Column(name = "balance")
    private Double balance;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name ="customer_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false)
    private Customer customer;

    public Account() {}
    public Account(Currency currency, Customer customer){
        this.currency = currency;
        this.customer = customer;
        this.balance = 0.0;
        this.number = UUID.randomUUID().toString();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return this.getId().equals(account.getId()) &&
                number.equals(account.number) &&
                currency == account.currency &&
                Objects.equals(balance, account.balance) &&
                Objects.equals(customer, account.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + this.getId() +
                ", number='" + number + '\'' +
                ", currency=" + currency +
                ", balance=" + balance +
                '}';
    }
}
