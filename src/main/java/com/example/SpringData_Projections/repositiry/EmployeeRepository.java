package com.example.SpringData_Projections.repositiry;

import com.example.SpringData_Projections.model.Employee;
import com.example.SpringData_Projections.projection.EmployeeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e.id AS id, CONCAT(e.firstName, ' ', e.lastName) AS fullName, e.position AS position, e.department.name AS departmentName " +
            "FROM Employee e WHERE e.id = :id")
    EmployeeProjection findEmployeeProjectionById(Long id);

    @Query("SELECT e.id AS id, CONCAT(e.firstName, ' ', e.lastName) AS fullName, e.position AS position, e.department.name AS departmentName " +
            "FROM Employee e WHERE e.department.name = :departmentName")
    List<EmployeeProjection> findEmployeeProjectionsByDepartmentName(String departmentName);
}
