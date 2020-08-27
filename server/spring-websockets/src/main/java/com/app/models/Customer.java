package com.app.models;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name ="customers", uniqueConstraints = {
        @UniqueConstraint(name = "UC_customer_email", columnNames = {"email"}),
        @UniqueConstraint(name = "UC_customer_phone", columnNames = {"phone"})
})
@Data
@EqualsAndHashCode(callSuper = false, exclude = {"accounts", "employers"})
@ToString(exclude = {"accounts", "employers"})
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends AbstractEntity implements Serializable {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "age", nullable = false)
    private Integer age;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;
    @OneToMany(mappedBy = "customer",
            cascade = {CascadeType.REMOVE, CascadeType.MERGE},
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<Account> accounts = new HashSet<>();
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
}
