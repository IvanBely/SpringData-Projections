package com.example.SpringData_Projections.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "FirstName cannot be blank")
    @NotNull(message = "FirstName cannot be null")
    private String firstName;
    @NotBlank(message = "LastName cannot be blank")
    @NotNull(message = "LastName cannot be null")
    private String lastName;
    @NotBlank(message = "Position cannot be blank")
    @NotNull(message = "Position cannot be null")
    private String position;

    @NotNull(message = "Salary cannot be null")
    private Double salary;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @JsonBackReference
    private Department department;

}
