package com.example.SpringData_Projections.repositiry;

import com.example.SpringData_Projections.model.Employee;
import com.example.SpringData_Projections.projection.EmployeeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.id = :id")
    Optional<EmployeeProjection> findEmployeeProjectionById(Long id);

    @Query("SELECT e FROM Employee e WHERE e.department.name = :departmentName")
    List<EmployeeProjection> findEmployeeProjectionsByDepartmentName(String departmentName);
}