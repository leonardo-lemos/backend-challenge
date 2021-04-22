package dev.leonardolemos.backendchallenge.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tbl_products")
@SequenceGenerator(initialValue = 1, name = "defaultGenerator", sequenceName = "seq_products", allocationSize = 1)
@AttributeOverride(name = "id", column = @Column(name = "id_product"))
public class Product extends BaseEntity {

    @NotEmpty
    @Size(max = 128)
    @Column(name = "nm_product", nullable = false)
    private String name;

    @NotEmpty
    @Size(max = 256)
    @Column(name = "ds_product", nullable = false)
    private String description;

    @NotEmpty
    @Size(min = 12, max = 128)
    @Column(name = "ds_barcode", length = 128, nullable = false)
    private String barcode;

    @OneToOne
    @JoinColumn(name = "id_manufacturer", referencedColumnName = "id_manufacturer", foreignKey = @ForeignKey(name = "fk_product_x_manufacturer_01"), nullable = false)
    private Manufacturer manufacturer;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "ds_unit_price", nullable = false)
    private BigDecimal unitPrice;

    public Product() {
    }

    public Product(String name, String description, String barcode, BigDecimal unitPrice, Manufacturer manufacturer) {
        this.name = name;
        this.description = description;
        this.barcode = barcode;
        this.unitPrice = unitPrice;
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

}
