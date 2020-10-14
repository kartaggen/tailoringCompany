package entity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "materials")
public class Material {

    @Id
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Material name cannot be blank!")
    @Size(max = 20, message = "Material name has to be up to 20 characters!")
    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @NotBlank(message = "Color cannot be blank!")
    @Size(max = 20, message = "Color has to be up to 20 characters!")
    @Column(name = "color", nullable = false, length = 20)
    private String color;

    @Positive
    @DecimalMin(value = "0.00", message = "Price per sq.m. has to be more than or equal to 0.00")
    @DecimalMax(value = "10000.00", message = "Price per sq.m. has to be less than or equal to 10000.00")
    @Digits(integer = 5, fraction = 2, message = "Price per sq.m. has to be up to 5 digits before and 2 digits after the decimal separator!")
    @Column(name = "price_per_sqm", nullable = false, length = 10)
    private double pricePerSqM;

    public Material() {
    }

    public Material(String name, String color, double pricePerSqM) {
        this.name = name;
        this.color = color;
        this.pricePerSqM = pricePerSqM;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String colorName) {
        this.color = colorName;
    }

    public double getPricePerSqM() {
        return pricePerSqM;
    }

    public void setPricePerSqM(double pricePerSqM) {
        this.pricePerSqM = pricePerSqM;
    }

    @Override
    public String toString() {
        return name + " - " + color;
    }
}
