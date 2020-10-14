package entity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "company")
public class Company {

    @Id
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Name of the company cannot be blank!")
    @Size(max = 45, message = "Name of the company has to be up to 45 characters!")
    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Positive
    @DecimalMin(value = "0.00", message = "Profit tax has to be more than 0%")
    @DecimalMax(value = "100.00", message = "Profit tax has to be less than 100%", inclusive = false)
    @Digits(integer = 3, fraction = 2, message = "Profit tax has to be up to 3 digits before and 2 digits after the decimal separator!")
    @Column(name = "profit_tax", nullable = false)
    private double profitTax;

    @Positive
    @DecimalMin(value = "0", message = "Salary has to be more than 0", inclusive = false)
    @DecimalMax(value = "100000", message = "Salary has to be less than 100000", inclusive = false)
    @Digits(integer = 5, fraction = 2, message = "Salary has to be with 5 or less digits before and 2 digits after the decimal separator!")
    @Column(name = "salary_exp", nullable = false)
    private double salaryExp;

    @Positive
    @DecimalMin(value = "0", message = "Salary has to be more than 0", inclusive = false)
    @DecimalMax(value = "100000", message = "Salary has to be less than 100000", inclusive = false)
    @Digits(integer = 5, fraction = 2, message = "Salary has to be with 5 or less digits before and 2 digits after the decimal separator!")
    @Column(name = "salary_inexp", nullable = false)
    private double salaryInexp;

    public Company() {
    }

    public Company(String name, double profitTax, double salaryExp, double salaryInexp) {
        this.name = name;
        this.profitTax = profitTax;
        this.salaryExp = salaryExp;
        this.salaryInexp = salaryInexp;
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

    public double getProfitTax() {
        return profitTax;
    }

    public void setProfitTax(double profitTax) {
        this.profitTax = profitTax;
    }

    public double getSalaryExp() {
        return salaryExp;
    }

    public void setSalaryExp(double salaryExp) {
        this.salaryExp = salaryExp;
    }

    public double getSalaryInexp() {
        return salaryInexp;
    }

    public void setSalaryInexp(double salaryInexp) {
        this.salaryInexp = salaryInexp;
    }
}
