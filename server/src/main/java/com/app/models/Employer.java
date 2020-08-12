package com.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "employers")
@NamedQueries({
        @NamedQuery(name = "employers.findAll",
                query = "select e from Employer e"),
})
public class Employer extends AbstractEntity implements Serializable {
    @Column(name = "number", nullable = false)
    private String number;
    @Column(name = "address", nullable = false)
    private String address;
    @JsonIgnore
    @ManyToMany(mappedBy = "employers", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Set<Customer> customers = new HashSet<>();

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public void addCustomer(Customer customer){
        this.customers.add(customer);
        customer.getEmployers().add(this);
    }

    public void removeCustomer(Customer customer){
        this.customers.removeIf(cust -> cust.equals(customer));
        customer.getEmployers().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employer employer = (Employer) o;
        return number.equals(employer.number) &&
                address.equals(employer.address)
                && this.getId() == ((Employer) o).getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), number, address);
    }
}
