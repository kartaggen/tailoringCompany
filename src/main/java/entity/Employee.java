package entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "First name cannot be blank!")
    @Size(max = 20, message = "First name has to be up to 20 characters!")
    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @NotBlank(message = "Last name cannot be blank!")
    @Size(max = 20, message = "Last name has to be up to 20 characters!")
    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @Column(name = "experienced", nullable = false)
    private boolean isExperienced;

    public Employee() {
    }

    public Employee(String firstName, String lastName, boolean isExperienced) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isExperienced = isExperienced;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isExperienced() {
        return isExperienced;
    }

    public void setExperienced(boolean experienced) {
        isExperienced = experienced;
    }

    @Override
    public String toString() {
        return "#" + id + " - " +
                firstName + " " + lastName +
                " - " + (isExperienced ? "Exp" : "Inexp");
    }
}
