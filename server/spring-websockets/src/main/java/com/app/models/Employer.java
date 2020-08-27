package com.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "employers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = "customers")
public class Employer extends AbstractEntity implements Serializable {
    @Column(name = "number", nullable = false)
    private String number;
    @Column(name = "address", nullable = false)
    private String address;
    @JsonIgnore
    @ManyToMany(mappedBy = "employers", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Set<Customer> customers = new HashSet<>();

    public void addCustomer(Customer customer){
        this.customers.add(customer);
        customer.getEmployers().add(this);
    }

    public void removeCustomer(Customer customer){
        this.customers.removeIf(cust -> cust.equals(customer));
        customer.getEmployers().remove(this);
    }
}
