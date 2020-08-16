package com.app.models;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name ="customers")
@NamedQueries({
        @NamedQuery(name = "customers.findAll",
                query = "select c from Customer c"),
})
public class Customer extends AbstractEntity implements Serializable {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "age", nullable = false)
    private Integer age;
    @OneToMany(mappedBy = "customer",
            cascade = {CascadeType.REMOVE, CascadeType.MERGE},
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Account> accounts;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "customers_employers",
            joinColumns = {@JoinColumn(name = "customer_id", referencedColumnName = "id")},
            inverseJoinColumns = { @JoinColumn(name="employer_id", referencedColumnName = "id")}
            )

    private Set<Employer> employers = new HashSet<>();


    public Set<Employer> getEmployers() {
        return employers;
    }

    public void setEmployers(Set<Employer> employers) {
        this.employers = employers;
    }

    public void addEmployer(Employer employer){
        this.employers.add(employer);
        employer.getCustomers().add(this);
    }

    public Customer() {}
    public Customer(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.accounts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return this.getId().equals(customer.getId()) &&
                email.equals(customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), email);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", accounts=" + accounts +
                ", employers=" + employers +
                '}';
    }
}
