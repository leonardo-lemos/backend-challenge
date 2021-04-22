package dev.leonardolemos.backendchallenge.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tbl_manufacturers")
@SequenceGenerator(initialValue = 1, name = "defaultGenerator", sequenceName = "seq_manufacturers", allocationSize = 1)
@AttributeOverride(name = "id", column = @Column(name = "id_manufacturer"))
public class Manufacturer extends BaseEntity {

    @NotEmpty
    @Size(max = 128)
    @Column(name = "nm_manufacturer")
    private String name;

    public Manufacturer() {
    }

    public Manufacturer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
