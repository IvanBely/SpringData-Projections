package com.example.SpringData_Projections.repositiry;

import com.example.SpringData_Projections.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
