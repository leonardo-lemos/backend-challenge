package dev.leonardolemos.backendchallenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tbl_consumers")
@SequenceGenerator(initialValue = 1, name = "defaultGenerator", sequenceName = "seq_consumers", allocationSize = 1)
@AttributeOverride(name = "id", column = @Column(name = "id_consumer"))
public class Consumer extends BaseEntity {

    @NotEmpty
    @Size(max = 128)
    @Column(name = "nm_consumer", length = 128)
    private String name;

    @Size(min = 13, max = 14)
    @Column(name = "ds_phone", length = 14)
    private String phone;

    @Email
    @NotEmpty
    @Size(max = 254)
    @Column(name = "ds_email", length = 254)
    private String email;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_order", referencedColumnName = "id_order", foreignKey = @ForeignKey(name = "fk_consumer_x_order_01"))
    private Order order;

    public Consumer(String name, String phone, String email, Order order) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.order = order;
    }

    public Consumer(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Consumer() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
