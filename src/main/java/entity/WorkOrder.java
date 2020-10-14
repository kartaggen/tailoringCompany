package entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "work_orders")
public class WorkOrder {

    @Id
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "part_id", nullable = false)
    private Part part;

    @Positive
    @Min(value = 1, message = "Quantity has to be at least 1!")
    @Max(value = 100, message = "Quantity has to be up to 100!")
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @NotNull
    @PastOrPresent(message = "Completion date cannot be in the future!")
    @Column(name = "completion_date")
    private LocalDate completionDate;

    public WorkOrder() {
    }

    public WorkOrder(Employee employee, Part part, int quantity, LocalDate completionDate) {
        this.employee = employee;
        this.part = part;
        this.quantity = quantity;
        this.completionDate = completionDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }
}
