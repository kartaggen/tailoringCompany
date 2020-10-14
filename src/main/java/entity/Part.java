package entity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "parts")
public class Part {

    @Id
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Part name cannot be blank!")
    @Size(max = 45, message = "Part name has to be up to 45 characters!")
    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @Positive
    @DecimalMin(value = "0.00", message = "Material needed per sq.m. has to be more than 0.00", inclusive = false)
    @DecimalMax(value = "100.00", message = "Material needed per sq.m. has to be less than or equal to 100.00")
    @Digits(integer = 3, fraction = 2, message = "Material needed per sq.m. has to be up to 3 digits before and 2 digits after the decimal separator!")
    @Column(name = "material_needed", nullable = false)
    private double materialNeededSqm;

    @Positive
    @Min(value = 1, message = "Experienced employee has to make at least 1!")
    @Max(value = 1000, message = "Experienced employee has to make up to 1000!")
    @Column(name = "exp_make_per_month", nullable = false)
    private int expMakePerMonth;

    @Positive
    @Min(value = 1, message = "Inexperienced employee has to make at least 1!")
    @Max(value = 1000, message = "Inexperienced employee has to make up to 1000!")
    @Column(name = "inexp_make_per_month", nullable = false)
    private int inexpMakePerMonth;

    @Positive
    @DecimalMin(value = "0.00", message = "Price has to be more than 0.00", inclusive = false)
    @DecimalMax(value = "10000.00", message = "Price has to be less than or equal to 10000.00")
    @Digits(integer = 5, fraction = 2, message = "Price has to be up to 5 digits before and 2 digits after the decimal separator!")
    @Column(name = "sell_price", nullable = false)
    private double sellPrice;

    public Part() {
    }

    public Part(String name, Material material, double materialNeededSqm, int expMakePerMonth, int inexpMakePerMonth, double sellPrice) {
        this.name = name;
        this.material = material;
        this.materialNeededSqm = materialNeededSqm;
        this.expMakePerMonth = expMakePerMonth;
        this.inexpMakePerMonth = inexpMakePerMonth;
        this.sellPrice = sellPrice;
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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public double getMaterialNeededSqm() {
        return materialNeededSqm;
    }

    public void setMaterialNeededSqm(double materialNeededSqm) {
        this.materialNeededSqm = materialNeededSqm;
    }

    public int getExpMakePerMonth() {
        return expMakePerMonth;
    }

    public void setExpMakePerMonth(int expMakePerMonth) {
        this.expMakePerMonth = expMakePerMonth;
    }

    public int getInexpMakePerMonth() {
        return inexpMakePerMonth;
    }

    public void setInexpMakePerMonth(int inexpMakePerMonth) {
        this.inexpMakePerMonth = inexpMakePerMonth;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    @Override
    public String toString() {
        return "#" + id + " - " +
                name + " - " + material;
    }
}
